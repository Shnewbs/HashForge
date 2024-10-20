package com.shnewbs.hashforge.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;

public class ASICMinerBlockEntity extends BlockEntity {

    private final EnergyStorage energyStorage = new EnergyStorage(10000); // 10000 FE capacity

    public ASICMinerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ASIC_MINER_ENTITY.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("Energy", energyStorage.getEnergyStored());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("Energy")) {
            energyStorage.setEnergy(tag.getInt("Energy"));
        }
    }

    public IEnergyStorage getEnergyStorage() {
        return energyStorage;
    }
}