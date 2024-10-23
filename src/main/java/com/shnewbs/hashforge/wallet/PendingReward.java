package com.shnewbs.hashforge.wallet;

import com.shnewbs.hashforge.currency.CurrencyType;

public class PendingReward {
    private final CurrencyType currencyType;
    private final double amount;
    private final String transactionId;
    private final long timestamp;

    public PendingReward(CurrencyType currencyType, double amount, String transactionId) {
        this.currencyType = currencyType;
        this.amount = amount;
        this.transactionId = transactionId;
        this.timestamp = System.currentTimeMillis();
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public double getAmount() {
        return amount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public long getTimestamp() {
        return timestamp;
    }
}