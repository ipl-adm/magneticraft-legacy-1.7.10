package com.cout970.magneticraft.api.util;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public interface IConnectable {

    TileEntity getParent();

    void iterate();

    VecInt[] getValidConnections();

    boolean isAbleToConnect(IConnectable cond, VecInt dir);

    ConnectionClass getConnectionClass(VecInt v);

    //save and load data
    void save(NBTTagCompound nbt);

    void load(NBTTagCompound nbt);
}
