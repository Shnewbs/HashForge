package com.shnewbs.hashforge.energy;

import net.neoforged.neoforge.energy.IEnergyStorage;

public class CustomEnergyStorage implements IEnergyStorage {
    private int energyStored;
    private final int maxEnergyStored;

    public CustomEnergyStorage(int maxEnergyStored) {
        this.maxEnergyStored = maxEnergyStored;
        this.energyStored = 0; // Initially empty
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int energyReceived = Math.min(maxEnergyStored - energyStored, maxReceive);
        if (!simulate) {
            energyStored += energyReceived;
        }
        return energyReceived;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int energyExtracted = Math.min(energyStored, maxExtract);
        if (!simulate) {
            energyStored -= energyExtracted;
        }
        return energyExtracted;
    }

    @Override
    public int getEnergyStored() {
        return energyStored;
    }

    @Override
    public int getMaxEnergyStored() {
        return maxEnergyStored;
    }

    @Override
    public boolean canExtract() {
        return true;
    }

    @Override
    public boolean canReceive() {
        return true;
    }

    public void setEnergy(int energy) {
        this.energyStored = Math.min(energy, maxEnergyStored);
    }
}
