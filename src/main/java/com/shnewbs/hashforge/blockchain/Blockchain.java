package com.shnewbs.hashforge.blockchain;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel; // Import ServerLevel
import net.minecraft.world.entity.player.Player; // Import Player
import java.util.ArrayList;
import java.util.List;

public class Blockchain {

    private List<ChainBlock> blocks = new ArrayList<>();

    // Method to add a block to the blockchain
    public void addBlock(ChainBlock block, ServerLevel world, Player player) {
        blocks.add(block);
        // Additional logic for updating the blockchain
    }

    // Method to get the last block in the blockchain
    public ChainBlock getLastBlock() {
        if (blocks.isEmpty()) {
            // Return a default genesis block if the blockchain is empty
            return new ChainBlock(new BlockPos(0, 0, 0), "0"); // Genesis block
        }
        return blocks.get(blocks.size() - 1); // Return the last block in the blockchain
    }

    // Other blockchain methods
}
