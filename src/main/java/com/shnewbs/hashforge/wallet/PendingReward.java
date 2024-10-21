package com.shnewbs.hashforge.wallet;

import com.shnewbs.hashforge.CurrencyType;

public class PendingReward {
    private CurrencyType coinType;
    private double amount;
    private String transactionId;

    // Constructor
    public PendingReward(CurrencyType coinType, double amount, String transactionId) {
        this.coinType = coinType;
        this.amount = amount;
        this.transactionId = transactionId;
    }

    // Getters
    public CurrencyType getCoinType() {
        return coinType;
    }

    public double getAmount() {
        return amount;
    }

    public String getTransactionId() {
        return transactionId;
    }
}
