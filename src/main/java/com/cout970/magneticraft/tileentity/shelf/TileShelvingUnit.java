package com.cout970.magneticraft.tileentity.shelf;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.api.util.VecIntUtil;
import com.cout970.magneticraft.tileentity.TileBase;
import com.cout970.magneticraft.util.ITileShelf;
import com.cout970.magneticraft.util.InventoryComponent;
import com.cout970.magneticraft.util.InventoryResizable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;

public class TileShelvingUnit extends TileBase implements ITileShelf {
    private int crates;
    public static final int MAX_CRATES = 24;
    public static final int MAX_SHELVES = 3;
    public static final int CRATE_SIZE = 27;
    public static final int SHELF_CRATES = MAX_CRATES / MAX_SHELVES;

    private InventoryResizable[] rowInv = new InventoryResizable[MAX_SHELVES];

    public TileShelvingUnit() {
        crates = 0;
        for (int i = 0; i < MAX_SHELVES; i++) {
            rowInv[i] = new InventoryResizable(this, CRATE_SIZE * SHELF_CRATES, "Shelf " + i);
            rowInv[i].lock();
        }
    }

    public InventoryComponent getInv(int i) {
        return rowInv[i];
    }

    @Override
    public void updateEntity() {
        if (worldObj.getTotalWorldTime() % 400 == 0) {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    @Override
    public TileShelvingUnit getMainTile() {
        return this;
    }

    public boolean addCrate() {
        if (crates < MAX_CRATES) {
            if (rowInv[crates / SHELF_CRATES].resize(CRATE_SIZE)) {
                crates++;
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                return true;
            }
        }
        return false;
    }

    public boolean removeCrate() {
        if (crates > 0) {
            if (rowInv[(crates - 1) / SHELF_CRATES].resize(-CRATE_SIZE)) {
                crates--;
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                return true;
            }
        }
        return false;
    }

    public int getCrateCount() {
        return crates;
    }

    public MgDirection getDirection() {
        return MgDirection.getDirection(getBlockMetadata());
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        VecInt v1 = VecIntUtil.getRotatedOffset(MgDirection.getDirection(getBlockMetadata()), -2, 0, 0);
        VecInt v2 = VecIntUtil.getRotatedOffset(MgDirection.getDirection(getBlockMetadata()), 2, 3, 1);
        VecInt block = new VecInt(xCoord, yCoord, zCoord);

        return VecIntUtil.getAABBFromVectors(v1.add(block), v2.add(block));
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        crates = nbt.getInteger("crates");
        for (int i = 0; i < MAX_SHELVES; i++) {
            rowInv[i].readFromNBT(nbt, rowInv[i].name);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("crates", crates);
        for (int i = 0; i < MAX_SHELVES; i++) {
            rowInv[i].writeToNBT(nbt, rowInv[i].name);
        }
    }
}
