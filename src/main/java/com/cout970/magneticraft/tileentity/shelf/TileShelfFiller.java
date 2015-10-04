package com.cout970.magneticraft.tileentity.shelf;

import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.tileentity.TileBase;
import com.cout970.magneticraft.util.ITileShelf;
import net.minecraft.nbt.NBTTagCompound;

public class TileShelfFiller extends TileBase implements ITileShelf {
    private VecInt offset;

    @Override
    public TileShelvingUnit getMainTile() {
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
