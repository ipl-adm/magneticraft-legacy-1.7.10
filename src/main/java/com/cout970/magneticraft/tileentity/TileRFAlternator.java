package com.cout970.magneticraft.tileentity;

import cofh.api.energy.IEnergyHandler;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.prefab.ElectricConductor;
import com.cout970.magneticraft.api.util.EnergyConverter;
import com.cout970.magneticraft.api.util.IConnectable;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.util.tile.TileConductorLow;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileRFAlternator extends TileConductorLow implements IEnergyHandler {

    public int storage = 0;
    public int maxStorage = 32000;
    public double min = ElectricConstants.ALTERNATOR_DISCHARGE;

    @Override
    public IElectricConductor initConductor() {
        return new ElectricConductor(this) {
            @Override
            public void iterate() {
                super.iterate();
                if (!isControlled()) return;
                if (getVoltage() < min && storage > 0) {
                    int change = (int) Math.min((min - getVoltage()) * 80, 400);
                    change = Math.min(change, storage);
                    applyPower(EnergyConverter.RFtoW(change));
                    storage -= change;
                }
            }

            @Override
            public int getStorage() {
                return storage;
            }

            @Override
            public int getMaxStorage() {
                return maxStorage;
            }

            @Override
            public void setStorage(int charge) {
                storage = charge;
            }

            @Override
            public void applyCharge(int charge) {
                storage += charge;
                if (storage > maxStorage)
                    storage = maxStorage;
            }

            @Override
            public void drainCharge(int charge) {
                storage -= charge;
                if (storage < 0) storage = 0;
            }

            @Override
            public void save(NBTTagCompound nbt) {
                super.save(nbt);
                nbt.setInteger("Storage", storage);
            }

            @Override
            public void load(NBTTagCompound nbt) {
                super.load(nbt);
                storage = nbt.getInteger("Storage");
            }

            @Override
            public VecInt[] getValidConnections() {
                return new VecInt[]{getDirection().opposite().toVecInt()};
            }

            @Override
            public boolean isAbleToConnect(IConnectable c, VecInt d) {
                return getDirection().opposite().toVecInt().equals(d);
            }
        };
    }

    public MgDirection getDirection() {
        return MgDirection.getDirection(getBlockMetadata());
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from) {
        return getDirection().toForgeDir() == from;
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
        int accepted = Math.max(Math.min(maxReceive, maxStorage - storage), 0);
        if (!simulate)
            storage += accepted;
        return accepted;
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
        return 0;
    }

    @Override
    public int getEnergyStored(ForgeDirection from) {
        return storage;
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from) {
        return maxStorage;
    }

}
