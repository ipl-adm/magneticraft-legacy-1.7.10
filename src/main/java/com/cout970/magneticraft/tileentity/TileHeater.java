package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.IElectricTile;
import com.cout970.magneticraft.api.electricity.prefab.ElectricConductor;
import com.cout970.magneticraft.api.heat.IHeatConductor;
import com.cout970.magneticraft.api.heat.IHeatTile;
import com.cout970.magneticraft.api.heat.prefab.HeatConductor;
import com.cout970.magneticraft.api.util.EnergyConverter;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.client.gui.component.IGuiSync;
import com.cout970.magneticraft.tileentity.multiblock.TileMB_Base;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.nbt.NBTTagCompound;

public class TileHeater extends TileMB_Base implements IHeatTile, IGuiSync, IElectricTile {

    public IHeatConductor heat = new HeatConductor(this, 1400, 1000);
    public IElectricConductor cond = initConductor();
    public static int MaxProduction = 120;//RF
    public int oldHeat;
    private boolean working;

    public IElectricConductor initConductor() {
        return new ElectricConductor(this);
    }

    @Override
    public IHeatConductor[] getHeatCond(VecInt c) {
        return new IHeatConductor[]{heat};
    }

    @Override
    public void onNeigChange() {
        super.onNeigChange();
        cond.disconnect();
    }

    public void updateEntity() {
        super.updateEntity();
        if (!this.worldObj.isRemote) {
            cond.recache();
            cond.iterate();
            if (worldObj.getTotalWorldTime() % 20 == 0) {
                if (working && !isActive()) {
                    setActive(true);
                } else if (!working && isActive()) {
                    setActive(false);
                }
            }
            heat.iterate();
            if (((int) heat.getTemperature()) != oldHeat && worldObj.provider.getWorldTime() % 10 == 0) {
                sendUpdateToClient();
                oldHeat = (int) heat.getTemperature();
            }
            double i = EnergyConverter.RFtoCALORIES(getComsumption());

            if (i > 0) {
                cond.drainPower(EnergyConverter.CALORIEStoW(i));
                heat.applyCalories(i);
                working = true;
            } else working = false;
        }
    }

    private void setActive(boolean b) {
        if (getBlockMetadata() != 2) {
            if (b)
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 2);
            else
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 2);
        }
    }

    public boolean isActive() {
        return getBlockMetadata() == 1;
    }

    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        heat.load(nbt);
        cond.load(nbt);
    }

    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        heat.save(nbt);
        cond.save(nbt);
    }

    public int getComsumption() {
        if (cond.getVoltage() > ElectricConstants.MACHINE_WORK && heat.getTemperature() < 1200) {
            return (int) (MaxProduction * (cond.getVoltage() / 240));
        }
        return 0;
    }

    @Override
    public void sendGUINetworkData(Container cont, ICrafting craft) {
        craft.sendProgressBarUpdate(cont, 10, (int) cond.getVoltage());
        craft.sendProgressBarUpdate(cont, 11, (int) heat.getTemperature());
    }

    @Override
    public void getGUINetworkData(int id, int value) {
        if (id == 10) cond.setVoltage(value);
        if (id == 11) heat.setTemperature(value);
    }

    @Override
    public IElectricConductor[] getConds(VecInt dir, int tier) {
        if (tier != 0) return null;
        return new IElectricConductor[]{cond};
    }
}
