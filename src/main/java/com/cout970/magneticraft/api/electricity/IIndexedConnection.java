package com.cout970.magneticraft.api.electricity;

import com.cout970.magneticraft.api.util.VecInt;

public interface IIndexedConnection {

    VecInt getOffset();

    IElectricConductor getSource();

    IElectricConductor getConductor();

    IEnergyInterface getEnergyInterface();

    int getIndex();
}
