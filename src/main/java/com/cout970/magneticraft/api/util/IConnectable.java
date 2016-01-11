package com.cout970.magneticraft.api.util;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public interface IConnectable {

    TileEntity getParent();

    void iterate();

    EnumFacing[] getValidConnections();

    boolean isAbleToConnect(IConnectable cond, EnumFacing dir);

    ConnectionClass getConnectionClass(EnumFacing v);

    //save and load data
    void save(NBTTagCompound nbt);

    void load(NBTTagCompound nbt);
}
