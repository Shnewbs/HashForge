package com.shnewbs.hashforge.blockentity;

import com.shnewbs.hashforge.energy.CustomEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ASICMinerBlockEntity extends BlockEntity {
    private final CustomEnergyStorage energyStorage = new CustomEnergyStorage(10000);

    public ASICMinerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ASIC_MINER.get(), pos, state);
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

    @Override
    @Nullable
    public <T> T getCapability(@NotNull Capabilities cap, @Nullable Direction side) {
        if (cap == Capabilities.ENERGY) {
            return (T) energyStorage;
        }
        return super.getCapability(cap, side);
    }
}
