package com.cout970.magneticraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileWindTurbineGap extends TileBase {

    public int x, y, z;

    public void onBlockBreaks() {
        TileEntity t = worldObj.getTileEntity(x, y, z);
        if (t instanceof TileWindTurbine) {
            ((TileWindTurbine) t).onTurbineBreaks();
        }
    }

    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        x = nbt.getInteger("turbineX");
        y = nbt.getInteger("turbineY");
        z = nbt.getInteger("turbineZ");
    }

    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("turbineX", x);
        nbt.setInteger("turbineY", y);
        nbt.setInteger("turbineZ", z);
    }
}
