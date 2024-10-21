package com.shnewbs.hashforge.blocks;

import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import com.mojang.serialization.MapCodec;
import com.shnewbs.hashforge.HashForgeMod;

public class ASICMinerBlock extends BaseEntityBlock {

    public static final MapCodec<ASICMinerBlock> CODEC = simpleCodec(ASICMinerBlock::new);

    public ASICMinerBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ASICMinerBlockEntity(pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    // You may need to override other methods depending on your block's behavior
    // For example:

    /*
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide && hand == InteractionHand.MAIN_HAND) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof ASICMinerBlockEntity) {
                // Handle interaction with the ASIC Miner
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
    */
}