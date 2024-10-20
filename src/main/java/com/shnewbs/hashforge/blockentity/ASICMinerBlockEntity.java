package com.shnewbs.hashforge.blockentity;

import com.shnewbs.hashforge.HashForgeMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ASICMinerBlockEntity extends BlockEntity {

    private final EnergyStorage energyStorage = new EnergyStorage(10000);
    private final ItemStackHandler itemHandler = new ItemStackHandler(1);
    private int miningProgress = 0;
    private int miningTime = 200;

    public ASICMinerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ASIC_MINER_ENTITY.get(), pos, state);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put("Inventory", itemHandler.serializeNBT());
        tag.putInt("Energy", energyStorage.getEnergyStored());
        tag.putInt("MiningProgress", miningProgress);
        super.saveAdditional(tag);
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        itemHandler.deserializeNBT(tag.getCompound("Inventory"));
        energyStorage.receiveEnergy(tag.getInt("Energy"), false);
        miningProgress = tag.getInt("MiningProgress");
    }

    public void tick() {
        if (level != null && !level.isClientSide()) {
            if (energyStorage.getEnergyStored() >= 10) {
                energyStorage.extractEnergy(10, false);
                miningProgress++;
                if (miningProgress >= miningTime) {
                    mineCryptocurrency();
                    miningProgress = 0;
                }
            }
        }
    }

    private void mineCryptocurrency() {
        // TODO: Implement cryptocurrency mining logic
        HashForgeMod.LOGGER.info("Mined cryptocurrency at " + getBlockPos());
    }

    public EnergyStorage getEnergyStorage() {
        return energyStorage;
    }

    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }
}