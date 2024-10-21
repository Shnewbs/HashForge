package com.shnewbs.hashforge.mining;

import com.shnewbs.hashforge.blocks.ASICMinerBlockEntity;
import com.shnewbs.hashforge.HashForgeMod;
import com.shnewbs.hashforge.CurrencyType; // Ensure this is imported
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class MiningManager {
    private static final Map<UUID, List<BlockPos>> playerMiners = new ConcurrentHashMap<>();
    private static ServerLevel serverLevel;

    // Ensure serverLevel is set for proper access to the level instance
    public static void setServerLevel(ServerLevel level) {
        serverLevel = level;
    }

    // Register a miner for a specific player
    public static void registerMiner(UUID playerUUID, BlockPos minerPos) {
        playerMiners.computeIfAbsent(playerUUID, k -> new ArrayList<>()).add(minerPos);
    }

    // Unregister a miner when removed or broken
    public static void unregisterMiner(UUID playerUUID, BlockPos minerPos) {
        List<BlockPos> miners = playerMiners.get(playerUUID);
        if (miners != null) {
            miners.remove(minerPos);
            if (miners.isEmpty()) {
                playerMiners.remove(playerUUID);
            }
        }
    }

    // Get all ASICMinerBlockEntity instances for a player
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

    // Perform mining for a player, adding the mined coin to their wallet
    public static void mineCoin(UUID playerUUID, CurrencyType coinType, double hashRate) {
        // Placeholder mining logic. You will need to replace it with actual mining mechanics.
        double miningProbability = hashRate / 1000000.0; // Example: 1 MH/s gives 0.001 probability
        if (Math.random() < miningProbability) {
            // Coin mined!
            double minedAmount = 0.00000001; // Example: 1 satoshi equivalent
            if (HashForgeMod.walletManager != null) {
                HashForgeMod.walletManager.addToBalance(playerUUID, coinType, minedAmount); // Ensure walletManager is initialized
            }
        }
    }

    // Additional methods for mining and management
}
