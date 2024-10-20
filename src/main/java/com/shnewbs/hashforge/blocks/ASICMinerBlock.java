package com.shnewbs.hashforge.blocks;

import com.shnewbs.hashforge.blockentity.ASICMinerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.Nullable;

public class ASICMinerBlock extends Block {
    public ASICMinerBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ASICMinerBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(BlockState state, BlockEntityType<T> type) {
        return (blockPos, blockState, blockEntityType) -> {
            if (blockEntityType instanceof ASICMinerBlockEntity asicMinerBlockEntity) {
                // Add ticker functionality here if needed
            }
        };
    }
}
