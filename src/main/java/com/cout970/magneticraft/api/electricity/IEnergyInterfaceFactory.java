package com.cout970.magneticraft.api.electricity;

import com.cout970.magneticraft.api.util.VecInt;
import net.minecraft.tileentity.TileEntity;

public interface IEnergyInterfaceFactory {

    boolean shouldHandleTile(TileEntity tile, VecInt f, int tier);

    IEnergyInterface getEnergyInterface(TileEntity tile, VecInt f, int tier);
}
