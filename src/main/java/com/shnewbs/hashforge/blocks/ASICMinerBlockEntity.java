package com.shnewbs.hashforge.blocks;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class ASICMinerBlockEntity extends BlockEntity {

    public ASICMinerBlockEntity(BlockPos pos, BlockState state) {
        super(null, pos, state);  // Pass the appropriate BlockEntityType when registering
    }

    // Add your logic for the ASIC miner block entity here
}
