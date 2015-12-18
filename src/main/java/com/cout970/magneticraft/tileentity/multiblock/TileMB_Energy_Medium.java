package com.cout970.magneticraft.tileentity.multiblock;

import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.IElectricTile;
import com.cout970.magneticraft.api.electricity.prefab.ElectricConductor;
import com.cout970.magneticraft.api.util.VecInt;
import net.minecraft.nbt.NBTTagCompound;

public class TileMB_Energy_Medium extends TileMB_Base implements IElectricTile {

    public IElectricConductor cond = new ElectricConductor(this, 1, ElectricConstants.RESISTANCE_COPPER_MED);

    @Override
    public IElectricConductor[] getConds(VecInt dir, int tier) {
        if (tier != 1) return null;
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
