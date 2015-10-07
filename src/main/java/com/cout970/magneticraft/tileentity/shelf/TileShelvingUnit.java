package com.cout970.magneticraft.tileentity.shelf;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.api.util.VecIntUtil;
import com.cout970.magneticraft.tileentity.TileBase;
import com.cout970.magneticraft.util.ITileShelf;
import com.cout970.magneticraft.util.InventoryComponent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;

public class TileShelvingUnit extends TileBase implements ITileShelf {
    private int crates;
    public static final int MAX_CRATES = 24;
    public static final int MAX_SHELVES = 3;
    public static final int CRATE_SIZE = 27;

    private InventoryComponent[] rowInv = new InventoryComponent[MAX_SHELVES];

    public TileShelvingUnit() {
        crates = 0;
        for (int i = 0; i < MAX_SHELVES; i++) {
            rowInv[i] = new InventoryComponent(this, CRATE_SIZE * MAX_CRATES / MAX_SHELVES, "Shelf " + i);
        }
    }

    public InventoryComponent getInv(int i) {
        return rowInv[i];
    }

    @Override
    public TileShelvingUnit getMainTile() {
        return this;
    }

    public boolean addCrate() {
        if (crates < MAX_CRATES) {
            crates++;
            return true;
        }
        return false;
    }

    public boolean removeCrate() {
        if (crates > 0) {
            crates--;
            return true;
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
