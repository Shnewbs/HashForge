package com.shnewbs.hashforge.mining;

import com.shnewbs.hashforge.blocks.ASICMinerBlockEntity;
import com.shnewbs.hashforge.HashForgeMod;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class MiningManager {
    private static final Map<UUID, List<BlockPos>> playerMiners = new ConcurrentHashMap<>();
    private static ServerLevel serverLevel;

    public static void setServerLevel(ServerLevel level) {
        serverLevel = level;
    }

    public static void registerMiner(UUID playerUUID, BlockPos minerPos) {
        playerMiners.computeIfAbsent(playerUUID, k -> new ArrayList<>()).add(minerPos);
    }

    public static void unregisterMiner(UUID playerUUID, BlockPos minerPos) {
        List<BlockPos> miners = playerMiners.get(playerUUID);
        if (miners != null) {
            miners.remove(minerPos);
            if (miners.isEmpty()) {
                playerMiners.remove(playerUUID);
            }
        }
    }

    public static List<ASICMinerBlockEntity> getMinersForPlayer(UUID playerUUID) {
        List<BlockPos> minerPositions = playerMiners.getOrDefault(playerUUID, new ArrayList<>());
        if (serverLevel == null) {
            return new ArrayList<>(); // Return empty list if server level is not set
        }

        return minerPositions.stream()
                .map(serverLevel::getBlockEntity)
                .filter(blockEntity -> blockEntity instanceof ASICMinerBlockEntity)
                .map(blockEntity -> (ASICMinerBlockEntity) blockEntity)
                .collect(Collectors.toList());
    }

    public static void mineCoin(UUID playerUUID, String coinType, double hashRate) {
        // This is a placeholder implementation. You'll need to implement your actual mining logic here.
        // This might involve calculating the probability of finding a coin based on hashRate,
        // checking against the total supply, and adding the mined coin to the player's wallet.

        double miningProbability = hashRate / 1000000.0; // Example: 1 MH/s gives 0.001 probability
        if (Math.random() < miningProbability) {
            // Coin mined!
            double minedAmount = 0.00000001; // Example: 1 satoshi equivalent
            HashForgeMod.walletManager.addToBalance(playerUUID, coinType, minedAmount);
            // You might want to log this event or notify the player
        }
    }

    // Additional methods as needed for your mining system
}