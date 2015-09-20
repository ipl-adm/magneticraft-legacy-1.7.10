package com.cout970.magneticraft.api.kinetic;

import com.cout970.magneticraft.api.util.MgDirection;

public interface IKineticTile {

    IKineticConductor getKineticConductor(MgDirection dir);

    MgDirection[] getValidSides();
}
