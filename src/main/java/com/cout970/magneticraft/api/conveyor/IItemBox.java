package com.cout970.magneticraft.api.conveyor;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IItemBox {

    ItemStack getContent();

    void setContent(ItemStack content);

    int getPosition();

    void setPosition(int positions);

    boolean isOnLeft();

    void setOnLeft(boolean isLeft);

    void save(NBTTagCompound t);

    void load(NBTTagCompound t);

    long getLastUpdateTick();

    void setLastUpdateTick(long tick);
}
