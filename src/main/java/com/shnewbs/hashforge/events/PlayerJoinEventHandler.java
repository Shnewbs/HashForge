package com.shnewbs.hashforge.events;

import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.bus.api.SubscribeEvent;

public class PlayerJoinEventHandler {

    @SubscribeEvent
    public void onPlayerJoin(PlayerLoggedInEvent event) {
        // Your logic for handling player join events
    }
}
