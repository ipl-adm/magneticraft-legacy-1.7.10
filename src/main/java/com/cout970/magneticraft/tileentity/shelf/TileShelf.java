package com.cout970.magneticraft.tileentity.shelf;

import com.cout970.magneticraft.ManagerBlocks;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.tileentity.TileBase;
import com.cout970.magneticraft.util.InventoryResizable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public abstract class TileShelf extends TileBase implements IInventory {
    @Nullable
    public abstract TileShelvingUnit getMainTile();

    public InventoryResizable getInventory() {
        int invNum = getOffset().getY() - 1;
        if (invNum < 0) {
            if (worldObj.getBlock(xCoord, yCoord - 1, zCoord) == ManagerBlocks.shelving_unit) {
                TileShelvingUnit below = ((TileShelf) worldObj.getTileEntity(xCoord, yCoord - 1, zCoord)).getMainTile();
                if (below != null) {
                    return below.getInv(2);
                }
            }
            return null;
        }
        if (getMainTile() == null) {
            return null;
        }
        return getMainTile().getInv(invNum);
    }

    public abstract VecInt getOffset();

    @Override
    public int getSizeInventory() {
        int size = getRealSize();
        if ((size == 0) && (getMainTile() != null) && getMainTile().isPlacing()) {
            size = 1;
        }
        return size;
    }

    public int getRealSize() {
        return (getInventory() != null) ? getInventory().getCurSlots() : 0;
    }

    @Override
    @Nullable
    public ItemStack getStackInSlot(int i) {
        return (getInventory() != null) ? getInventory().getStackInSlot(i) : null;
    }

    @Override
    @Nullable
    public ItemStack decrStackSize(int i, int i1) {
        return (getInventory() != null) ? getInventory().decrStackSize(i, i1) : null;
    }

    @Override
    @Nullable
    public ItemStack getStackInSlotOnClosing(int i) {
        return (getInventory() != null) ? getInventory().getStackInSlotOnClosing(i) : null;
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemStack) {
        TileShelvingUnit main = getMainTile();
        if (main == null) {
            return;
        }
        if (main.isPlacing() && (itemStack != null) && (itemStack.getItem() == Item.getItemFromBlock(Blocks.chest))) {
            ItemStack old = getStackInSlot(i);
            if ((old == null) || (old.getItem() != itemStack.getItem())) {
                old = itemStack.copy();
                old.stackSize = 0;
            }
            int diff = itemStack.stackSize - old.stackSize;

            while (diff > 0 && (main.addCrate())) {
                diff--;
            }
            itemStack.stackSize = old.stackSize + diff;
            if (itemStack.stackSize == 0) {
                itemStack = null;
            }
            main.getWorldObj().markBlockForUpdate(main.xCoord, main.yCoord, main.zCoord);
        }

        if ((getInventory() != null) && ((i > 0) || (getRealSize() > 0))) {
            getInventory().setInventorySlotContents(i, itemStack);
        }
    }

    @Override
    @Nullable
    public String getInventoryName() {
        return (getInventory() != null) ? getInventory().getInventoryName() : null;
    }

    @Override
    public boolean hasCustomInventoryName() {
        return (getInventory() != null) && getInventory().hasCustomInventoryName();
    }

    @Override
    public int getInventoryStackLimit() {
        if ((getOffset().getY() == 0) && (getRealSize() == 0)) {
            if (getMainTile() == null) {
                return 0;
            }
            return TileShelvingUnit.MAX_CRATES - getMainTile().getCrateCount();
        }
        return (getInventory() != null)? getInventory().getInventoryStackLimit() : 0;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityPlayer) {
        return (getInventory() != null) && getInventory().isUseableByPlayer(entityPlayer);
    }

    @Override
    public void openInventory() {
        if (getInventory() != null) {
            getInventory().openInventory();
        }
    }

    @Override
    public void closeInventory() {
        if (getInventory() != null) {
            getInventory().closeInventory();
        }
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemStack) {
        return getMainTile() != null &&
                ((itemStack.getItem() == Item.getItemFromBlock(Blocks.chest)) && getMainTile().isPlacing() && (getMainTile().getCrateCount() < TileShelvingUnit.MAX_CRATES)
                        || (getInventory() != null) && getInventory().isItemValidForSlot(i, itemStack));
    }
}
