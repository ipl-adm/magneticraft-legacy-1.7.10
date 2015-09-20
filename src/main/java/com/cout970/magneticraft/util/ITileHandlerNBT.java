package com.cout970.magneticraft.util;

import net.minecraft.nbt.NBTTagCompound;

public interface ITileHandlerNBT {


    void saveInServer(NBTTagCompound nbt);

    void loadInClient(NBTTagCompound nbt);

}
