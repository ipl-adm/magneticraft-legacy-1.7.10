package com.cout970.magneticraft.util;

import net.minecraft.nbt.NBTTagCompound;

public interface IBlockWithData {

    public static final String KEY = "DATA";

    public void saveData(NBTTagCompound nbt);

    public void loadData(NBTTagCompound nbt);
}
