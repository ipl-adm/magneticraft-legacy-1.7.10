package com.cout970.magneticraft.tileentity.shelf;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.tileentity.TileBase;
import com.cout970.magneticraft.util.ITileShelf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;

public class TileShelvingUnit extends TileBase implements ITileShelf {
    private int crates;
    private static final int MAX_CRATES = 24;

    public TileShelvingUnit() {
        crates = 0;
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
        
        return INFINITE_EXTENT_AABB;

    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        crates = nbt.getInteger("crates");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("crates", crates);
    }
}
