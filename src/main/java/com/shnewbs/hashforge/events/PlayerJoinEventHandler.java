package com.shnewbs.hashforge.event;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.lifecycle.player.PlayerLoginEvent;

public class PlayerJoinEventHandler {

    @SubscribeEvent
    public void onPlayerJoin(PlayerLoginEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            player.sendSystemMessage(Component.literal("Welcome to the server!"));
        }
    }
}
