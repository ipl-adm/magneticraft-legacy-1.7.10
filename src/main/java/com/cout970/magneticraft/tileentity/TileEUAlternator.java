package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.prefab.ElectricConductor;
import com.cout970.magneticraft.api.util.EnergyConverter;
import com.cout970.magneticraft.api.util.IConnectable;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.util.tile.TileConductorLow;
import cpw.mods.fml.common.FMLCommonHandler;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.info.Info;
import ic2.api.tile.IEnergyStorage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEUAlternator extends TileConductorLow implements IEnergySink, IEnergyStorage {

    public int storage = 0;
    public int maxStorage = 32000;
    public double min = ElectricConstants.ALTERNATOR_DISCHARGE;
    private boolean addedToEnet;

    public void updateEntity() {
        super.updateEntity();
        if (!addedToEnet) onLoaded();
    }

    @Override
    public IElectricConductor initConductor() {
        return new ElectricConductor(this) {
            @Override
            public void iterate() {
                super.iterate();
                if (!isControlled()) return;
                if (getVoltage() < min && storage > 0) {
                    int change = (int) Math.min((min - getVoltage()) * 80, 512);
                    change = Math.min(change, storage);
                    applyPower(EnergyConverter.EUtoW(change));
                    storage -= change;//storage in EU
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

    public void onLoaded() {
        if (!addedToEnet && !FMLCommonHandler.instance().getEffectiveSide().isClient() && Info.isIc2Available()) {
            MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
            addedToEnet = true;
        }
    }

    @Override
    public void invalidate() {
        super.invalidate();
        onChunkUnload();
    }

    @Override
    public void onChunkUnload() {
        if (addedToEnet && Info.isIc2Available()) {
            MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
            addedToEnet = false;
        }
    }

    @Override
    public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) {
        return direction == getDirection().toForgeDir();
    }

    @Override
    public double getDemandedEnergy() {
        return maxStorage - storage;
    }

    @Override
    public int getSinkTier() {
        return 2;
    }

    @Override
    public double injectEnergy(ForgeDirection dir, double amount, double voltage) {
        if (dir == getDirection().toForgeDir()) {
            double move = Math.min(amount, maxStorage - storage);
            storage += move;
            return amount - move;
        }
        return amount;
    }

    @Override
    public int getStored() {
        return storage;
    }

    @Override
    public void setStored(int energy) {
        storage = energy;
    }

    @Override
    public int addEnergy(int amount) {
        storage += amount;
        return storage;
    }

    @Override
    public int getCapacity() {
        return maxStorage;
    }

    @Override
    public int getOutput() {
        return 0;
    }

    @Override
    public double getOutputEnergyUnitsPerTick() {
        return 0;
    }

    @Override
    public boolean isTeleporterCompatible(ForgeDirection side) {
        return false;
    }

    public MgDirection getDirection() {
        return MgDirection.getDirection(getBlockMetadata());
    }
}
