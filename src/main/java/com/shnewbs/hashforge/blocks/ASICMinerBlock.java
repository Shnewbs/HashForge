package com.shnewbs.hashforge.blocks;

import com.shnewbs.hashforge.blockentity.ASICMinerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ASICMinerBlock extends Block implements EntityBlock {

    public ASICMinerBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ASICMinerBlockEntity(pos, state);
    }

    // Add any other block-specific methods here
}