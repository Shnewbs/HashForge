package com.shnewbs.hashforge.wallet;

import java.util.concurrent.ConcurrentHashMap;
import java.util.UUID;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Logger;
import java.util.Base64;

public class WalletManager {
    private static final Logger LOGGER = Logger.getLogger(WalletManager.class.getName());
    private final ConcurrentHashMap<UUID, Wallet> wallets = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Double> centralPool = new ConcurrentHashMap<>();
    private final ReadWriteLock poolLock = new ReentrantReadWriteLock();
    private final TransactionLogger transactionLogger;

    public WalletManager() {
        centralPool.put("HashCoin", 21_000_000.0);
        this.transactionLogger = new TransactionLogger();
    }

    public Wallet createWallet(UUID playerUUID) {
        return wallets.computeIfAbsent(playerUUID, Wallet::new);
    }

    public Wallet getWallet(UUID playerUUID) {
        return wallets.get(playerUUID);
    }

    public void recordMiningReward(UUID playerUUID, String coinType, double amount) {
        Wallet wallet = getWallet(playerUUID);
        if (wallet != null) {
            String transactionId = generateTransactionId(playerUUID, coinType, amount);
            wallet.addPendingReward(coinType, amount, transactionId);
            transactionLogger.logMiningReward(playerUUID, coinType, amount, transactionId);
        } else {
            LOGGER.warning("Attempted to record mining reward for non-existent wallet: " + playerUUID);
        }
    }

    public boolean claimMiningRewards(UUID playerUUID) {
        Wallet wallet = getWallet(playerUUID);
        if (wallet == null) return false;

        poolLock.writeLock().lock();
        try {
            for (PendingReward reward : wallet.getPendingRewards()) {
                String coinType = reward.getCoinType();
                double rewardAmount = reward.getAmount();
                String transactionId = reward.getTransactionId();

                if (!transactionLogger.verifyTransaction(transactionId)) {
                    LOGGER.warning("Invalid transaction detected: " + transactionId);
                    return false;
                }

                double poolBalance = centralPool.getOrDefault(coinType, 0.0);
                if (poolBalance >= rewardAmount) {
                    centralPool.put(coinType, poolBalance - rewardAmount);
                    wallet.addBalance(coinType, rewardAmount);
                    transactionLogger.logRewardClaim(playerUUID, coinType, rewardAmount, transactionId);
                } else {
                    LOGGER.warning("Not enough coins in central pool for " + coinType);
                    return false;
                }
            }
            wallet.clearPendingRewards();
            return true;
        } finally {
            poolLock.writeLock().unlock();
        }
    }

    public boolean transferFunds(UUID fromPlayerUUID, UUID toPlayerUUID, String coinType, double amount) {
        Wallet fromWallet = getWallet(fromPlayerUUID);
        Wallet toWallet = getWallet(toPlayerUUID);

        if (fromWallet == null || toWallet == null) {
            return false;
        }

        String transactionId = generateTransactionId(fromPlayerUUID, toPlayerUUID, coinType, amount);

        if (fromWallet.subtractBalance(coinType, amount, transactionId)) {
            toWallet.addBalance(coinType, amount);
            transactionLogger.logTransfer(fromPlayerUUID, toPlayerUUID, coinType, amount, transactionId);
            return true;
        }
        return false;
    }

    private String generateTransactionId(Object... params) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            for (Object param : params) {
                digest.update(param.toString().getBytes());
            }
            return Base64.getEncoder().encodeToString(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            LOGGER.severe("Failed to generate transaction ID: " + e.getMessage());
            return null;
        }
    }
}