package com.cout970.magneticraft.api.electricity;

import net.minecraft.util.EnumFacing;

public interface IIndexedConnection {

    EnumFacing getOffset();

    IElectricConductor getSource();

    IElectricConductor getConductor();

    IEnergyInterface getEnergyInterface();

    int getIndex();
}
