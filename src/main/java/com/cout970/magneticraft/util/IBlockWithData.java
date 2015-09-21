package com.cout970.magneticraft.util;

import net.minecraft.nbt.NBTTagCompound;

public interface IBlockWithData {

    String KEY = "DATA";

    void saveData(NBTTagCompound nbt);

    void loadData(NBTTagCompound nbt);
}
