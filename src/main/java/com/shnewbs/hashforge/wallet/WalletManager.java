package com.shnewbs.hashforge.wallet;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WalletManager {

    private final Map<String, Wallet> wallets = new ConcurrentHashMap<>();

    public WalletManager() {
        // Initialization logic here
    }

    public void loadWallets() {
        // Load wallets from storage
    }

    public Wallet getWallet(String playerUUID) {
        return wallets.get(playerUUID);
    }

    public void addWallet(String playerUUID, Wallet wallet) {
        wallets.put(playerUUID, wallet);
    }
}
