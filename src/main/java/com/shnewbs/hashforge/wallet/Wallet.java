package com.shnewbs.hashforge.wallet;

import java.util.concurrent.ConcurrentHashMap;
import java.util.UUID;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.ArrayList;
import java.util.List;

public class Wallet {
    private final UUID playerUUID;
    private final ConcurrentHashMap<String, Double> balances = new ConcurrentHashMap<>();
    private final List<PendingReward> pendingRewards = new ArrayList<>();
    private final ReadWriteLock rewardsLock = new ReentrantReadWriteLock();

    public Wallet(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public double getBalance(String coinType) {
        return balances.getOrDefault(coinType, 0.0);
    }

    public void addBalance(String coinType, double amount) {
        balances.compute(coinType, (k, v) -> (v == null ? 0 : v) + amount);
    }

    public boolean subtractBalance(String coinType, double amount, String transactionId) {
        return balances.computeIfPresent(coinType, (k, v) -> {
            if (v >= amount) {
                return v - amount;
            }
            return v;
        }) != null;
    }

    public void addPendingReward(String coinType, double amount, String transactionId) {
        rewardsLock.writeLock().lock();
        try {
            pendingRewards.add(new PendingReward(coinType, amount, transactionId));
        } finally {
            rewardsLock.writeLock().unlock();
        }
    }

    public List<PendingReward> getPendingRewards() {
        rewardsLock.readLock().lock();
        try {
            return new ArrayList<>(pendingRewards);
        } finally {
            rewardsLock.readLock().unlock();
        }
    }

    public void clearPendingRewards() {
        rewardsLock.writeLock().lock();
        try {
            pendingRewards.clear();
        } finally {
            rewardsLock.writeLock().unlock();
        }
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }
}

class PendingReward {
    private final String coinType;
    private final double amount;
    private final String transactionId;

    public PendingReward(String coinType, double amount, String transactionId) {
        this.coinType = coinType;
        this.amount = amount;
        this.transactionId = transactionId;
    }

    public String getCoinType() { return coinType; }
    public double getAmount() { return amount; }
    public String getTransactionId() { return transactionId; }
}