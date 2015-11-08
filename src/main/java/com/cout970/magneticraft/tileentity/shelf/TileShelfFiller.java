package com.cout970.magneticraft.tileentity.shelf;

import com.cout970.magneticraft.api.util.VecInt;
import net.minecraft.nbt.NBTTagCompound;

public class TileShelfFiller extends TileShelf {
    public boolean silentRemoval = false;
    private VecInt offset;

    @Override
    public TileShelvingUnit getMainTile() {
        if (!worldObj.blockExists(xCoord - offset.getX(), yCoord - offset.getY(), zCoord - offset.getZ())) {
            return null;
        }
        return (TileShelvingUnit) worldObj.getTileEntity(xCoord - offset.getX(), yCoord - offset.getY(), zCoord - offset.getZ());
    }

    public VecInt getOffset() {
        return offset;
    }

    public void setOffset(VecInt offset) {
        this.offset = offset.copy();
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("offsetX", offset.getX());
        nbt.setInteger("offsetY", offset.getY());
        nbt.setInteger("offsetZ", offset.getZ());
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        offset = new VecInt(nbt.getInteger("offsetX"), nbt.getInteger("offsetY"), nbt.getInteger("offsetZ"));
    }
}
