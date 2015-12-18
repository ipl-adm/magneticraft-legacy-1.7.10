package com.cout970.magneticraft.util.tile;

import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.IElectricTile;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.tileentity.TileBase;
import net.minecraft.nbt.NBTTagCompound;

public abstract class TileConductorMedium extends TileBase implements IElectricTile {

    public IElectricConductor cond = initConductor();

    public abstract IElectricConductor initConductor();

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
