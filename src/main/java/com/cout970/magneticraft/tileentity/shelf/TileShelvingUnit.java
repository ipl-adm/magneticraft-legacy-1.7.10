package com.cout970.magneticraft.tileentity.shelf;

import com.cout970.magneticraft.ManagerBlocks;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.api.util.VecIntUtil;
import com.cout970.magneticraft.block.BlockMg;
import com.cout970.magneticraft.util.InventoryResizable;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;

import java.util.ArrayList;
import java.util.List;

public class TileShelvingUnit extends TileShelf {
    public static final int MAX_CRATES = 24;
    public static final int MAX_SHELVES = 3;
    public static final int CRATE_SIZE = 27;
    public static final int SHELF_CRATES = MAX_CRATES / MAX_SHELVES;
    private int crates;
    private InventoryResizable[] rowInv = new InventoryResizable[MAX_SHELVES];
    private boolean placing;

    public TileShelvingUnit() {
        crates = 0;
        for (int i = 0; i < MAX_SHELVES; i++) {
            rowInv[i] = new InventoryResizable(this, CRATE_SIZE * SHELF_CRATES, "Shelf " + i);
            rowInv[i].lock();
        }
    }

    @Override
    public void updateEntity() {
        if ((crates > (MAX_CRATES - SHELF_CRATES))) {
            createTopShelf();
        }
    }

    public InventoryResizable getInv(int i) {
        return rowInv[i];
    }

    @Override
    public VecInt getOffset() {
        return VecInt.NULL_VECTOR;
    }

    @Override
    public TileShelvingUnit getMainTile() {
        return this;
    }

    public boolean addCrate() {
        if (crates < MAX_CRATES) {
            if (crates == (MAX_CRATES - SHELF_CRATES) && !createTopShelf()) {
                return false;
            }
            if (rowInv[crates / SHELF_CRATES].resize(CRATE_SIZE)) {
                crates++;
                return true;
            }
        }
        return false;
    }

    private boolean createTopShelf() {
        List<VecInt> placeCoords = new ArrayList<>();
        for (int r = -2; r <= 2; r++) {
            for (int b = 0; b < 2; b++) {
                VecInt coord = VecIntUtil.getRotatedOffset(MgDirection.getDirection(getBlockMetadata()), r, 3, b).add(xCoord, yCoord, zCoord);
                Block block = coord.getBlock(worldObj);
                if (block != ManagerBlocks.shelving_unit) {
                    if (block == null || coord.isBlockReplaceable(worldObj)) {
                        placeCoords.add(coord);
                    } else {
                        return false;
                    }
                }
            }
        }
        for (VecInt coord : placeCoords) {
            if (!coord.blockExists(worldObj))
                return false;
        }
        for (VecInt coord : placeCoords) {
            coord.setBlockWithMetadata(worldObj, ManagerBlocks.shelving_unit, 10, 3);
            TileEntity tile = coord.getTileEntity(worldObj);
            if (!(tile instanceof TileShelfFiller)) {
                tile = new TileShelfFiller();
                coord.setTileEntity(worldObj, tile);
            }
            ((TileShelfFiller) tile).setOffset(coord.copy().add(-xCoord, -yCoord, -zCoord));
        }
        return true;
    }

    public boolean removeCrate() {
        if (crates > 0) {
            if (rowInv[(crates - 1) / SHELF_CRATES].resize(-CRATE_SIZE)) {
                crates--;
                if (crates == (MAX_CRATES - SHELF_CRATES)) {
                    clearTopShelf();
                }
                return true;
            }
        }
        return false;
    }

    private void clearTopShelf() {
        for (int r = -2; r <= 2; r++) {
            for (int b = 0; b < 2; b++) {
                VecInt coord = VecIntUtil.getRotatedOffset(MgDirection.getDirection(getBlockMetadata()), r, 3, b).add(xCoord, yCoord, zCoord);
                if ((coord.getBlock(worldObj) == ManagerBlocks.shelving_unit) && (coord.getBlockMetadata(worldObj) == 10)) {
                    ((TileShelfFiller) coord.getTileEntity(worldObj)).silentRemoval = true;
                    worldObj.setBlockToAir(coord.getX(), coord.getY(), coord.getZ());
                }
            }
        }
    }

    @Override
    public void onBlockBreaks() {
        if (worldObj.isRemote) return;
        for (InventoryResizable inv : rowInv) {
            for (int i = 0; i < inv.getSizeInventory(); i++) {
                BlockMg.dropItem(inv.getStackInSlot(i), worldObj.rand, xCoord, yCoord, zCoord, worldObj);
            }
        }
        if (getCrateCount() > 0) {
            BlockMg.dropItem(new ItemStack(Blocks.chest, getCrateCount()), worldObj.rand, xCoord, yCoord, zCoord, worldObj);
        }
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
        placing = nbt.getBoolean("isPlacing");
        for (int i = 0; i < MAX_SHELVES; i++) {
            rowInv[i].readFromNBT(nbt, rowInv[i].name);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("crates", crates);
        nbt.setBoolean("isPlacing", placing);
        for (int i = 0; i < MAX_SHELVES; i++) {
            rowInv[i].writeToNBT(nbt, rowInv[i].name);
        }
    }

    public void setPlacing(boolean placing, EntityPlayer p) {
        if (p != null && !worldObj.isRemote) {
            p.addChatComponentMessage(new ChatComponentText(placing ? "Switched to placement mode" : "Switched to inventory mode"));
        }
        this.placing = placing;
    }

    public boolean isPlacing() {
        return placing;
    }
}
