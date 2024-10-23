package com.shnewbs.hashforge.currency;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class CoinRegistry {
    private final Map<String, CurrencyType.DynamicCoin> dynamicCoins = new ConcurrentHashMap<>();

    public CurrencyType.DynamicCoin registerCoin(String name, String symbol, long maxSupply) {
        String upperName = name.toUpperCase();

        if (dynamicCoins.containsKey(upperName)) {
            throw new IllegalArgumentException("Coin with name '" + name + "' already exists.");
        }

        CurrencyType.DynamicCoin newCoin = CurrencyType.registerDynamicCoin(name, symbol, maxSupply);
        dynamicCoins.put(upperName, newCoin);
        return newCoin;
    }

    public Optional<CurrencyType.DynamicCoin> getDynamicCoin(String name) {
        return Optional.ofNullable(dynamicCoins.get(name.toUpperCase()));
    }

    public void loadCoins() {
        // Implement loading logic
    }

    public Map<String, CurrencyType.DynamicCoin> getAllCoins() {
        return dynamicCoins;
    }
}
