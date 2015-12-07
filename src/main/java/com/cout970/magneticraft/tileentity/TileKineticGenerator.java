package com.cout970.magneticraft.tileentity;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyReceiver;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.prefab.ElectricConductor;
import com.cout970.magneticraft.api.util.EnergyConverter;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.client.gui.component.IBarProvider;
import com.cout970.magneticraft.client.gui.component.IGuiSync;
import com.cout970.magneticraft.util.tile.AverageBar;
import com.cout970.magneticraft.util.tile.TileConductorLow;
import com.cout970.magneticraft.util.tile.TileConductorMedium;
import cpw.mods.fml.common.Optional;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.Interface(iface = "cofh.api.energy.IEnergyHandler", modid = "CoFHAPI|energy")
public class TileKineticGenerator extends TileConductorMedium implements IEnergyHandler, IGuiSync {

    protected EnergyStorage storage = new EnergyStorage(32000);
    public float rotation = 0;
    private long time;
    private AverageBar energyBar = new AverageBar(20);

    public MgDirection getDirection() {
        return MgDirection.getDirection(getBlockMetadata() % 6);
    }

    @Override
    public IElectricConductor initConductor() {
        return new ElectricConductor(this, 1, ElectricConstants.RESISTANCE_COPPER_MED) {
            @Override
            public double getVoltageCapacity() {
                return ElectricConstants.MACHINE_CAPACITY*10;
            }
        };
    }

    @Override
    public IElectricConductor[] getConds(VecInt dir, int tier) {
        if (tier != 1) return null;
        if (VecInt.NULL_VECTOR.equals(dir)) {
            return new IElectricConductor[]{cond};
        }
        if (dir.toMgDirection() == getDirection().opposite()) return new IElectricConductor[]{cond};
        return null;
    }

    public void updateEntity() {
        super.updateEntity();
        if (worldObj.isRemote) return;

        energyBar.tick();
        if (cond.getVoltage() > ElectricConstants.MACHINE_WORK * cond.getVoltageMultiplier() && isControlled()) {

            float f = (storage.getMaxEnergyStored() - storage.getEnergyStored()) * 10f / storage.getMaxEnergyStored();
            double eff = TileConductorLow.getEfficiency(cond.getVoltage(), ElectricConstants.MACHINE_WORK * cond.getVoltageMultiplier(), ElectricConstants.MAX_VOLTAGE * cond.getVoltageMultiplier());
            int min = (int) Math.min(eff*400, 40 * Math.ceil(f));

            min = Math.min(storage.getMaxEnergyStored() - storage.getEnergyStored(), min);
            if (min > 0) {
                cond.drainPower(EnergyConverter.RFtoW(min));
                storage.modifyEnergyStored(min);
                energyBar.addValue(min);
            }
        }
        boolean working = energyBar.getAverage() > 0;

        if (worldObj.getTotalWorldTime() % 20 == 0) {
            if (working && !isActive()) {
                setActive(true);
            } else if (!working && isActive()) {
                setActive(false);
            }
        }

        if (storage.getEnergyStored() > 0) {
            TileEntity t = MgUtils.getTileEntity(this, getDirection());
            if (t instanceof IEnergyReceiver) {
                IEnergyReceiver e = (IEnergyReceiver) t;
                if (e.canConnectEnergy(getDirection().opposite().toForgeDir())) {
                    int transfer = Math.min(400, storage.getEnergyStored());
                    int accepted = e.receiveEnergy(getDirection().opposite().toForgeDir(), transfer, false);
                    storage.modifyEnergyStored(-accepted);
                }
            }
        }
    }

    private void setActive(boolean b) {
        if (b)
            worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, getBlockMetadata() + 6, 2);
        else
            worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, getBlockMetadata() - 6, 2);
    }

    public boolean isActive() {
        return getBlockMetadata() > 5;
    }

    public float getDelta() {
        long aux = time;
        time = System.nanoTime();
        return (float) ((time - aux) * 1E-6);
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from) {
        return getDirection().toForgeDir() == from;
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
        return storage.extractEnergy(maxExtract, simulate);
    }

    @Override
    public int getEnergyStored(ForgeDirection from) {
        return storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from) {
        return storage.getMaxEnergyStored();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        storage.readFromNBT(nbt);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        storage.writeToNBT(nbt);
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
        return 0;
    }

    @Override
    public void sendGUINetworkData(Container cont, ICrafting craft) {
        craft.sendProgressBarUpdate(cont, 0, (int) cond.getVoltage());
        craft.sendProgressBarUpdate(cont, 1, storage.getEnergyStored());
        craft.sendProgressBarUpdate(cont, 2, (int) (energyBar.getAverage()*16));
    }

    @Override
    public void getGUINetworkData(int id, int value) {
        switch (id) {
            case 0:
                cond.setVoltage(value);
                break;
            case 1:
                storage.setEnergyStored(value);
                break;
            case 2:
                energyBar.setStorage(value/16f);
                break;
        }
    }

    public IBarProvider getProductionBar(){
        return new IBarProvider() {
            @Override
            public String getMessage() {
                return String.format("%d RF", (int)energyBar.getStorage());
            }

            @Override
            public float getLevel() {
                return energyBar.getStorage();
            }

            @Override
            public float getMaxLevel() {
                return 400;
            }
        };
    }
}
