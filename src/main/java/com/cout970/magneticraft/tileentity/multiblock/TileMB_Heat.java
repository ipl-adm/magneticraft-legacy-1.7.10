package com.cout970.magneticraft.tileentity.multiblock;

import com.cout970.magneticraft.api.heat.IHeatConductor;
import com.cout970.magneticraft.api.heat.IHeatTile;
import com.cout970.magneticraft.api.heat.prefab.HeatConductor;
import com.cout970.magneticraft.api.util.VecInt;
import net.minecraft.nbt.NBTTagCompound;

public class TileMB_Heat extends TileMB_Base implements IHeatTile {

    public IHeatConductor heat = initHeatCond();
    public int oldHeat;

    @Override
    public IHeatConductor[] getHeatCond(VecInt c) {
        return new IHeatConductor[]{heat};
    }

    public IHeatConductor initHeatCond() {
        return new HeatConductor(this, 1400, 1000);
    }

    public void updateEntity() {
        super.updateEntity();
        if (!this.worldObj.isRemote) {
            heat.iterate();
            if (((int) heat.getTemperature()) != oldHeat && worldObj.provider.getWorldTime() % 10 == 0) {
                sendUpdateToClient();
                oldHeat = (int) heat.getTemperature();
            }
        }
    }

    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        heat.load(nbt);
    }

    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        heat.save(nbt);
    }
}
