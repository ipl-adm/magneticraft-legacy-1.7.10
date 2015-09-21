package com.cout970.magneticraft.util;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public interface IClientInformer {

    TileEntity getParent();

    void saveInfoToMessage(NBTTagCompound nbt);

    void loadInfoFromMessage(NBTTagCompound nbt);
}
