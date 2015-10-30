package com.cout970.magneticraft.tileentity.shelf;

import com.cout970.magneticraft.ManagerBlocks;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.tileentity.TileBase;
import com.cout970.magneticraft.util.ITileShelf;
import com.cout970.magneticraft.util.InventoryResizable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileShelfFiller extends TileBase implements ITileShelf {
    private VecInt offset;
    public boolean silentRemoval = false;

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
    public InventoryResizable getInventory() {
        int invNum = offset.getY() - 1;
        if (invNum < 0) {
            if (worldObj.getBlock(xCoord, yCoord - 1, zCoord) == ManagerBlocks.shelving_unit) {
                return ((ITileShelf) worldObj.getTileEntity(xCoord, yCoord - 1, zCoord)).getMainTile().getInv(2);
            }
            return null;
        }
        return getMainTile().getInv(invNum);
    }
}
