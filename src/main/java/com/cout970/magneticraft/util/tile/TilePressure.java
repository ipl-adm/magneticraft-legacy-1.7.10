package com.cout970.magneticraft.util.tile;

import com.cout970.magneticraft.api.pressure.IPressureConductor;
import com.cout970.magneticraft.api.pressure.IPressurePipe;
import com.cout970.magneticraft.tileentity.TileBase;
import net.minecraft.nbt.NBTTagCompound;

public abstract class TilePressure extends TileBase implements IPressurePipe {

    public IPressureConductor pressure = initConductor();

    @Override
    public IPressureConductor[] getPressureConductor() {
        return new IPressureConductor[]{pressure};
    }

    public abstract IPressureConductor initConductor();

    public void updateEntity() {
        super.updateEntity();
        if (worldObj.isRemote) return;
        pressure.iterate();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        pressure.load(nbt);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        pressure.save(nbt);
    }
}
