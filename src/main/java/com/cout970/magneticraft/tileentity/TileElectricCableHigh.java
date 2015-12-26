package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.IElectricTile;
import com.cout970.magneticraft.api.electricity.prefab.ElectricConductor;
import com.cout970.magneticraft.api.util.VecInt;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by cout970 on 23/12/2015.
 */
public class TileElectricCableHigh extends TileBase implements IElectricTile {

    public IElectricConductor cond = initConductor();

    public IElectricConductor initConductor() {
        return new ElectricConductor(this, 2, ElectricConstants.RESISTANCE_COPPER_HIGH) {
            @Override
            public double getVoltageCapacity() {
                return ElectricConstants.CABLE_HIGH_CAPACITY;
            }
        };
    }

    @Override
    public IElectricConductor[] getConds(VecInt dir, int tier) {
        if (tier != 2) return null;
        return new IElectricConductor[]{cond};
    }

    @Override
    public void onNeigChange() {
        super.onNeigChange();
        cond.disconnect();
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (worldObj.isRemote) return;
        cond.recache();
        cond.iterate();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        cond.load(nbt);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        cond.save(nbt);
    }
}
