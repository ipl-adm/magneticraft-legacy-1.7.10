package com.cout970.magneticraft.api.electricity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public interface IEnergyInterfaceFactory {

    boolean shouldHandleTile(TileEntity tile, EnumFacing f, int tier);

    IEnergyInterface getEnergyInterface(TileEntity tile, EnumFacing f, int tier);
}
