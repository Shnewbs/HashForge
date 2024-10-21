package com.shnewbs.hashforge.wallet;

import java.util.concurrent.ConcurrentHashMap;
import java.util.UUID;
import java.util.logging.Logger;

public class TransactionLogger {
    private static final Logger LOGGER = Logger.getLogger(TransactionLogger.class.getName());
    private final ConcurrentHashMap<String, TransactionRecord> transactionLog = new ConcurrentHashMap<>();

    public void logMiningReward(UUID playerUUID, String coinType, double amount, String transactionId) {
        TransactionRecord record = new TransactionRecord(TransactionType.MINING_REWARD, playerUUID, null, coinType, amount);
        transactionLog.put(transactionId, record);
        LOGGER.info("Mining reward logged: " + record);
    }

    public void logRewardClaim(UUID playerUUID, String coinType, double amount, String transactionId) {
        TransactionRecord record = new TransactionRecord(TransactionType.REWARD_CLAIM, playerUUID, null, coinType, amount);
        transactionLog.put(transactionId, record);
        LOGGER.info("Reward claim logged: " + record);
    }

    public void logTransfer(UUID fromPlayerUUID, UUID toPlayerUUID, String coinType, double amount, String transactionId) {
        TransactionRecord record = new TransactionRecord(TransactionType.TRANSFER, fromPlayerUUID, toPlayerUUID, coinType, amount);
        transactionLog.put(transactionId, record);
        LOGGER.info("Transfer logged: " + record);
    }

    public boolean verifyTransaction(String transactionId) {
        return transactionLog.containsKey(transactionId);
    }

    private enum TransactionType {
        MINING_REWARD, REWARD_CLAIM, TRANSFER
    }

    private static class TransactionRecord {
        private final TransactionType type;
        private final UUID fromPlayer;
        private final UUID toPlayer;
        private final String coinType;
        private final double amount;

        public TransactionRecord(TransactionType type, UUID fromPlayer, UUID toPlayer, String coinType, double amount) {
            this.type = type;
            this.fromPlayer = fromPlayer;
            this.toPlayer = toPlayer;
            this.coinType = coinType;
            this.amount = amount;
        }

        @Override
        public String toString() {
            return String.format("Transaction{type=%s, from=%s, to=%s, coin=%s, amount=%f}",
                    type, fromPlayer, toPlayer, coinType, amount);
        }
    }
}