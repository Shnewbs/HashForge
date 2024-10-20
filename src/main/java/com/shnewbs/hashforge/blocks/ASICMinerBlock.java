package com.shnewbs.hashforge.blocks;

import com.shnewbs.hashforge.blockchain.Blockchain;
import com.shnewbs.hashforge.blockchain.ChainBlock;  // Import ChainBlock
import com.shnewbs.hashforge.wallet.WalletManager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState; // Correct import for BlockState
import net.minecraft.world.level.Level; // Import for Level
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel; // Import for ServerLevel
import net.neoforged.neoforge.energy.IEnergyStorage; // Import Forge's IEnergyStorage

public class ASICMinerBlock extends Block {
    private int energyConsumptionRate = 100; // Example energy consumption rate

    public ASICMinerBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, world, pos, oldState, isMoving);
        if (world instanceof ServerLevel) {
            ServerLevel serverWorld = (ServerLevel) world;
            // Assume player placing block is the nearest one for now
            Player player = world.getNearestPlayer(pos.getX(), pos.getY(), pos.getZ(), 10, false);
            if (player != null) {
                // Link mining operation to the player's wallet
                Blockchain blockchain = new Blockchain(); // Assume blockchain instance exists
                blockchain.addBlock(new ChainBlock(pos, blockchain.getLastBlock().getHash()), serverWorld, player);
                WalletManager.getPlayerWallet(player).addCoins(1.0); // Reward player with 1 coin
            }
        }
    }

    // Method to consume energy and mine blocks
    public void mineBlock(IEnergyStorage energyStorage, Blockchain blockchain, Player player, ServerLevel world, BlockPos pos) {
        if (energyStorage.extractEnergy(energyConsumptionRate, false) >= energyConsumptionRate) {
            ChainBlock block = new ChainBlock(pos, blockchain.getLastBlock().getHash());
            blockchain.addBlock(block, world, player); // Add block to blockchain
            WalletManager.getPlayerWallet(player).addCoins(1.0); // Reward player for mining
        }
    }
}
