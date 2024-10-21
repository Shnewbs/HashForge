package com.shnewbs.hashforge.wallet;

import com.shnewbs.hashforge.CurrencyType; // Ensure correct import
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class Wallet {
    private HashMap<CurrencyType, Double> balances = new HashMap<>();
    private List<PendingReward> pendingRewards = new ArrayList<>(); // Store pending rewards

    // Default constructor
    public Wallet() {
        // Initialize balances if necessary
    }

    // Method to add balance to the wallet
    public void addBalance(CurrencyType coinType, double amount) {
        balances.put(coinType, balances.getOrDefault(coinType, 0.0) + amount);
    }

    // Method to subtract balance from the wallet
    public boolean subtractBalance(CurrencyType coinType, double amount) {
        double currentBalance = balances.getOrDefault(coinType, 0.0);
        if (currentBalance >= amount) {
            balances.put(coinType, currentBalance - amount);
            return true; // Successful subtraction
        }
        return false; // Insufficient funds
    }

    // Method to get the balance for a specific currency type
    public double getBalance(CurrencyType coinType) {
        return balances.getOrDefault(coinType, 0.0);
    }

    // Method to handle pending rewards
    public void addPendingReward(CurrencyType coinType, double amount, String transactionId) {
        // Create a new PendingReward object and add it to the list
        pendingRewards.add(new PendingReward(coinType, amount, transactionId));
    }

    // Method to retrieve pending rewards
    public List<PendingReward> getPendingRewards() {
        return pendingRewards;
    }

    // Method to clear pending rewards
    public void clearPendingRewards() {
        pendingRewards.clear();
    }
}
