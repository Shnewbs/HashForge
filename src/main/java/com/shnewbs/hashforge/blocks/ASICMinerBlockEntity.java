package com.shnewbs.hashforge.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.EnergyStorage;

public class ASICMinerBlockEntity extends BlockEntity {
    private final EnergyStorage energyStorage;
    private String currentCoin;
    private double hashRate;
    private boolean isPowered;

    // Updated constructor with BlockEntityType parameter
    public ASICMinerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.energyStorage = new EnergyStorage(100000);
        this.currentCoin = "";
        this.hashRate = 0;
        this.isPowered = false;
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putString("currentCoin", this.currentCoin);
        tag.putDouble("hashRate", this.hashRate);
        tag.putBoolean("isPowered", this.isPowered);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.currentCoin = tag.getString("currentCoin");
        this.hashRate = tag.getDouble("hashRate");
        this.isPowered = tag.getBoolean("isPowered");
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag);
        return ClientboundBlockEntityDataPacket.create(this, (blockEntity) -> tag);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }

    public void tick() {
        if (level.isClientSide()) return;
        isPowered = energyStorage.getEnergyStored() > 0;
        if (isPowered) {
            performMining();
        }
    }

    private void performMining() {
        // Mining logic goes here
    }
}
