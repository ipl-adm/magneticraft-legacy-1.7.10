package com.cout970.magneticraft.util;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public interface IClientInformer {

	public TileEntity getParent();
	public void saveInfoToMessage(NBTTagCompound nbt);
	public void loadInfoFromMessage(NBTTagCompound nbt);
}
