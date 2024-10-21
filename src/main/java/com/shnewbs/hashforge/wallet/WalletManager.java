package com.shnewbs.hashforge.wallet;

import com.shnewbs.hashforge.CurrencyType; // Ensure correct import
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
    private final ConcurrentHashMap<CurrencyType, Double> centralPool = new ConcurrentHashMap<>();
    private final ReadWriteLock poolLock = new ReentrantReadWriteLock();
    private final TransactionLogger transactionLogger;

    public WalletManager() {
        centralPool.put(CurrencyType.HASHCOIN, 21_000_000_000.0); // Adjusted for 21 billion
        this.transactionLogger = new TransactionLogger();
    }

    public Wallet createWallet(UUID playerUUID) {
        return wallets.computeIfAbsent(playerUUID, k -> new Wallet()); // Use new Wallet() constructor
    }

    public Wallet getWallet(UUID playerUUID) {
        return wallets.get(playerUUID);
    }

    public void recordMiningReward(UUID playerUUID, CurrencyType coinType, double amount) {
        Wallet wallet = getWallet(playerUUID);
        if (wallet != null) {
            String transactionId = generateTransactionId(playerUUID, coinType, amount);
            wallet.addPendingReward(coinType, amount, transactionId);
            transactionLogger.logMiningReward(playerUUID, coinType.name(), amount, transactionId); // Convert to String for logging
        } else {
            LOGGER.warning("Attempted to record mining reward for non-existent wallet: " + playerUUID);
        }
    }

    // Method to subtract balance for voucher creation
    public void createVoucher(UUID playerUUID, CurrencyType coinType, double amount) {
        Wallet wallet = getWallet(playerUUID);
        if (wallet != null && wallet.subtractBalance(coinType, amount)) {
            // Logic to create the voucher
            LOGGER.info("Voucher created for player: " + playerUUID + " for amount: " + amount);
        } else {
            LOGGER.warning("Insufficient balance for creating voucher for: " + playerUUID);
        }
    }

    // Method to send funds
    public boolean transferFunds(UUID fromPlayerUUID, UUID toPlayerUUID, CurrencyType coinType, double amount) {
        Wallet fromWallet = getWallet(fromPlayerUUID);
        Wallet toWallet = getWallet(toPlayerUUID);

        if (fromWallet != null && toWallet != null && fromWallet.subtractBalance(coinType, amount)) {
            toWallet.addBalance(coinType, amount); // Add the amount to recipient's wallet
            return true; // Transfer successful
        }
        return false; // Transfer failed due to insufficient funds or wallet issues
    }

    public boolean claimMiningRewards(UUID playerUUID) {
        Wallet wallet = getWallet(playerUUID);
        if (wallet == null) return false;

        poolLock.writeLock().lock();
        try {
            for (PendingReward reward : wallet.getPendingRewards()) {
                CurrencyType coinType = reward.getCoinType();
                double rewardAmount = reward.getAmount();
                String transactionId = reward.getTransactionId();

                if (!transactionLogger.verifyTransaction(transactionId)) {
                    LOGGER.warning("Invalid transaction detected: " + transactionId);
                    return false;
                }

                double poolBalance = centralPool.getOrDefault(coinType, 0.0);
                if (poolBalance >= rewardAmount) {
                    centralPool.put(coinType, poolBalance - rewardAmount);
                    addToBalance(playerUUID, coinType, rewardAmount); // Ensure this method exists
                    transactionLogger.logRewardClaim(playerUUID, coinType.name(), rewardAmount, transactionId); // Convert to String for logging
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

    public void addToBalance(UUID playerUUID, CurrencyType coinType, double amount) {
        Wallet wallet = getWallet(playerUUID);
        if (wallet != null) {
            wallet.addBalance(coinType, amount); // Update the wallet balance
        } else {
            LOGGER.warning("Attempted to add balance for non-existent wallet: " + playerUUID);
        }
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
