package com.shnewbs.hashforge.currency;

public enum CurrencyType {
    HASHFORGE("HF"),
    FORGECOIN("FC");

    private final String symbol;

    CurrencyType(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}