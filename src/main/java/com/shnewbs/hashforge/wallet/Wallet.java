package com.shnewbs.hashforge.wallet;

import java.util.concurrent.ConcurrentHashMap;
import java.util.UUID;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.ArrayList;
import java.util.List;
import com.shnewbs.hashforge.currency.CurrencyType;

public class Wallet {
    private final UUID playerUUID;
    private final ConcurrentHashMap<CurrencyType, Double> balances = new ConcurrentHashMap<>();
    private final List<PendingReward> pendingRewards = new ArrayList<>();
    private final ReadWriteLock rewardsLock = new ReentrantReadWriteLock();

    public Wallet(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public double getBalance(CurrencyType currencyType) {
        return balances.getOrDefault(currencyType, 0.0);
    }

    public void addBalance(CurrencyType currencyType, double amount) {
        balances.compute(currencyType, (k, v) -> (v == null ? 0 : v) + amount);
    }

    public void addPendingReward(CurrencyType currencyType, double amount, String transactionId) {
        rewardsLock.writeLock().lock();
        try {
            pendingRewards.add(new PendingReward(currencyType, amount, transactionId));
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
    private final CurrencyType currencyType;
    private final double amount;
    private final String transactionId;

    public PendingReward(CurrencyType currencyType, double amount, String transactionId) {
        this.currencyType = currencyType;
        this.amount = amount;
        this.transactionId = transactionId;
    }

    public CurrencyType getCurrencyType() { return currencyType; }
    public double getAmount() { return amount; }
    public String getTransactionId() { return transactionId; }
}
