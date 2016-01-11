package com.cout970.magneticraft.util;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;

public interface IBlockWithData {

    String KEY = "DATA";

    void saveData(NBTTagCompound nbt);

    void loadData(NBTTagCompound nbt);
}
