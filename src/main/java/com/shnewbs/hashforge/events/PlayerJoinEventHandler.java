package com.shnewbs.hashforge.events;

import java.util.UUID;
import com.shnewbs.hashforge.wallet.Wallet;
import com.shnewbs.hashforge.wallet.WalletManager;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import com.shnewbs.hashforge.HashForgeMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerJoinEventHandler {

    private static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();

        if (player instanceof ServerPlayer serverPlayer) {
            UUID playerUUID = serverPlayer.getUUID();

            try {
                // Check if the player already has a wallet
                if (HashForgeMod.walletManager.getWallet(playerUUID) == null) {
                    // Create a new wallet for the player
                    Wallet newWallet = HashForgeMod.walletManager.createWallet(playerUUID);
                    if (newWallet != null) {
                        serverPlayer.sendSystemMessage(Component.literal("Your wallet has been created!"));
                    } else {
                        serverPlayer.sendSystemMessage(Component.literal("Failed to create your wallet. Please contact an administrator."));
                    }
                } else {
                    serverPlayer.sendSystemMessage(Component.literal("Welcome back! Your existing wallet has been loaded."));
                }
            } catch (Exception e) {
                // Log the error
                LOGGER.error("Error creating wallet for player " + playerUUID, e);

                // Notify the player
                serverPlayer.sendSystemMessage(Component.literal("An error occurred while managing your wallet. Please contact an administrator."));
            }
        }
    }
}
