package com.cout970.magneticraft.util;

import net.minecraft.nbt.NBTTagCompound;

public interface ITileHandlerNBT {


    public void saveInServer(NBTTagCompound nbt);

    public void loadInClient(NBTTagCompound nbt);

}
