package com.shnewbs.hashforge.wallet;

import net.minecraft.world.entity.player.Player;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WalletManager {
    private static final Map<UUID, Wallet> playerWallets = new HashMap<>();

    // Get or create wallet for a player
    public static Wallet getPlayerWallet(Player player) {
        return playerWallets.computeIfAbsent(player.getUUID(), k -> new Wallet());
    }

    // Retrieve wallet by UUID
    public static Wallet getWallet(UUID walletId) {
        return playerWallets.get(walletId);
    }
}
