package com.cout970.magneticraft.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class InventoryResizable extends InventoryComponent {
    private int maxSlots, curSlots;
    public InventoryResizable(TileEntity t, int slots, String n) {
        super(t, slots, n);
        curSlots = maxSlots = slots;
    }

    public int getCurSlots() {
        return curSlots;
    }

    public void unlock() {
        markDirty();
        curSlots = maxSlots;
    }

    public void lock() {
        markDirty();
        for (ItemStack is : inventory) {
            if (is != null) {
                return;
            }
        }
        curSlots = 0;
    }

    public boolean resize(int delta) {
        markDirty();
        int newSlots = curSlots + delta;
        if ((newSlots < 0) || (newSlots > maxSlots)) {
            return false;
        }

        if (delta < 0) {
            for (int i = newSlots; i < curSlots; i++) {
                if (inventory[i] != null) {
                    return false;
                }
            }
        }

        curSlots = newSlots;
        return true;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack is) {
        return (slot < curSlots) && super.isItemValidForSlot(slot, is);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt, String name) {
        super.readFromNBT(nbt, name);
        curSlots = nbt.getInteger(name + "curSlots");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt, String name) {
        super.writeToNBT(nbt, name);
        nbt.setInteger(name + "curSlots", curSlots);
    }
}
