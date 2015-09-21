package com.cout970.magneticraft.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class InventoryComponent implements IInventory {

    public TileEntity tile;
    public ItemStack[] inventory;
    public String name;

    public InventoryComponent(TileEntity t, int slots, String n) {
        inventory = new ItemStack[slots];
        name = n;
        tile = t;
    }

    @Override
    public int getSizeInventory() {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int var1) {
        return inventory[var1];
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        ItemStack itemStack = getStackInSlot(slot);
        if (itemStack != null) {
            if (itemStack.stackSize <= amount) {
                setInventorySlotContents(slot, null);
            } else {
                itemStack = itemStack.splitStack(amount);
                if (itemStack.stackSize == 0) {
                    setInventorySlotContents(slot, null);
                }
            }
        }
        return itemStack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int var1) {
        return inventory[var1];
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack itemStack) {
        inventory[slot] = itemStack;

        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
            itemStack.stackSize = this.getInventoryStackLimit();
        }
        markDirty();
    }

    @Override
    public String getInventoryName() {
        return name;
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer var1) {
        return true;
    }

    @Override
    public void openInventory() {
    }

    @Override
    public void closeInventory() {
    }

    @Override
    public boolean isItemValidForSlot(int var1, ItemStack var2) {
        return true;
    }

    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        readFromNBT(nbtTagCompound, "Inventory");
    }

    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        writeToNBT(nbtTagCompound, "Inventory");
    }

    public void readFromNBT(NBTTagCompound nbtTagCompound, String name) {

        NBTTagList tagList = nbtTagCompound.getTagList(name, 10);
        inventory = new ItemStack[this.getSizeInventory()];
        for (int i = 0; i < tagList.tagCount(); ++i) {
            NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
            byte slot = tagCompound.getByte("Slot");
            if (slot >= 0 && slot < inventory.length) {
                setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(tagCompound));
            }
        }
    }

    public void writeToNBT(NBTTagCompound nbtTagCompound, String name) {

        NBTTagList list = new NBTTagList();
        for (int currentIndex = 0; currentIndex < inventory.length; ++currentIndex) {
            if (inventory[currentIndex] != null) {
                NBTTagCompound nbt = new NBTTagCompound();
                nbt.setByte("Slot", (byte) currentIndex);
                inventory[currentIndex].writeToNBT(nbt);
                list.appendTag(nbt);
            }
        }
        nbtTagCompound.setTag(name, list);
    }

    @Override
    public void markDirty() {
        tile.markDirty();
    }
}
