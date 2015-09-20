package com.cout970.magneticraft.api.computer;

import net.minecraft.tileentity.TileEntity;

public interface IComputer extends IPeripheralProvider {

    IModuleCPU getCPU();

    IModuleMemoryController getMemory();

    TileEntity getParent();

}
