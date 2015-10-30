package com.cout970.magneticraft.util;

import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.tileentity.shelf.TileShelvingUnit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface ITileShelf extends IInventory {
    TileShelvingUnit getMainTile();
    InventoryResizable getInventory();
    VecInt getOffset();

    @Override
    default int getSizeInventory() {
        int size = getRealSize();
        if (size == 0 && getMainTile().isPlacing()) {
            size = 1;
        }
        return size;
    }

    default int getRealSize() {
        return (getInventory() != null) ? getInventory().getCurSlots() : 0;
    }

    @Override
    default ItemStack getStackInSlot(int i) {
        return (getInventory() != null) ? getInventory().getStackInSlot(i) : null;
    }

    @Override
    default ItemStack decrStackSize(int i, int i1) {
        return (getInventory() != null) ? getInventory().decrStackSize(i, i1) : null;
    }

    @Override
    default ItemStack getStackInSlotOnClosing(int i) {
        return (getInventory() != null) ? getInventory().getStackInSlotOnClosing(i) : null;
    }

    @Override
    default void setInventorySlotContents(int i, ItemStack itemStack) {
        TileShelvingUnit main = getMainTile();
        if (main.isPlacing() && (itemStack.getItem() == Item.getItemFromBlock(Blocks.chest))) {
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
    default String getInventoryName() {
        return (getInventory() != null) ? getInventory().getInventoryName() : null;
    }

    @Override
    default boolean hasCustomInventoryName() {
        return (getInventory() != null) && getInventory().hasCustomInventoryName();
    }

    @Override
    default int getInventoryStackLimit() {
        return (getInventory() != null)? getInventory().getInventoryStackLimit() : 0;
    }

    @Override
    default boolean isUseableByPlayer(EntityPlayer entityPlayer) {
        return (getInventory() != null) && getInventory().isUseableByPlayer(entityPlayer);
    }

    @Override
    default void openInventory() {
        if (getInventory() != null) {
            getInventory().openInventory();
        }
    }

    @Override
    default void closeInventory() {
        if (getInventory() != null) {
            getInventory().closeInventory();
        }
    }

    @Override
    default boolean isItemValidForSlot(int i, ItemStack itemStack) {
        return (itemStack.getItem() == Item.getItemFromBlock(Blocks.chest)) && getMainTile().isPlacing() && (getMainTile().getCrateCount() < TileShelvingUnit.MAX_CRATES)
                || (getInventory() != null) && getInventory().isItemValidForSlot(i, itemStack);
    }
}
