package com.shnewbs.hashforge.currency;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public enum CurrencyType {
    HASHCOIN("HashCoin", "HC", 21_000_000L),  // Predefined default coin
    TESTCOIN("TestCoin", "TC", 1_000_000L);   // Another example coin

    private final String displayName;
    private final String symbol;
    private final long maxSupply;
    private final AtomicLong currentSupply;
    private double difficulty;

    // Dynamic coin storage
    private static final Map<String, DynamicCoin> DYNAMIC_COINS = new HashMap<>();

    // Enum constructor for predefined coins
    CurrencyType(String displayName, String symbol, long maxSupply) {
        this.displayName = displayName;
        this.symbol = symbol;
        this.maxSupply = maxSupply;
        this.currentSupply = new AtomicLong(0);
        this.difficulty = 1.0; // Start difficulty at 1.0
    }

    // Getters for coin properties
    public String getDisplayName() {
        return displayName;
    }

    public String getSymbol() {
        return symbol;
    }

    public long getMaxSupply() {
        return maxSupply;
    }

    public long getCurrentSupply() {
        return currentSupply.get();
    }

    public synchronized boolean addToSupply(long amount) {
        if (currentSupply.get() + amount <= maxSupply) {
            currentSupply.addAndGet(amount);
            updateDifficulty();
            return true;
        }
        return false;
    }

    public double getCirculationPercentage() {
        return (double) currentSupply.get() / maxSupply * 100;
    }

    public double getDifficulty() {
        return difficulty;
    }

    // Updates the difficulty based on supply
    private void updateDifficulty() {
        double supplyPercentage = (double) currentSupply.get() / maxSupply;
        this.difficulty = 1.0 + (supplyPercentage * 4.0);  // Difficulty increases with supply
    }

    // Registers a new dynamic coin by creating a DynamicCoin instance
    public static DynamicCoin registerDynamicCoin(String displayName, String symbol, long maxSupply) {
        String upperName = displayName.toUpperCase();
        if (DYNAMIC_COINS.containsKey(upperName)) {
            throw new IllegalArgumentException("Coin with name '" + displayName + "' already exists.");
        }

        // Create a new dynamic coin instance
        DynamicCoin newCoin = new DynamicCoin(displayName, symbol, maxSupply);
        DYNAMIC_COINS.put(upperName, newCoin);
        return newCoin;
    }

    // Retrieves a dynamic coin by name
    public static DynamicCoin getDynamicCoinByName(String name) {
        return DYNAMIC_COINS.get(name.toUpperCase());
    }

    // Returns all registered dynamic coins
    public static Map<String, DynamicCoin> getAllDynamicCoins() {
        return DYNAMIC_COINS;
    }

    // Separate class for dynamic coins
    public static class DynamicCoin {
        private final String displayName;
        private final String symbol;
        private final long maxSupply;
        private final AtomicLong currentSupply;

        public DynamicCoin(String displayName, String symbol, long maxSupply) {
            this.displayName = displayName;
            this.symbol = symbol;
            this.maxSupply = maxSupply;
            this.currentSupply = new AtomicLong(0);
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getSymbol() {
            return symbol;
        }

        public long getMaxSupply() {
            return maxSupply;
        }

        public long getCurrentSupply() {
            return currentSupply.get();
        }

        public synchronized boolean addToSupply(long amount) {
            if (currentSupply.get() + amount <= maxSupply) {
                currentSupply.addAndGet(amount);
                return true;
            }
            return false;
        }

        public double getCirculationPercentage() {
            return (double) currentSupply.get() / maxSupply * 100;
        }
    }
}
