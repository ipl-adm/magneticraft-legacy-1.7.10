package com.cout970.magneticraft.api.kinetic;

import net.minecraft.util.EnumFacing;

public interface IKineticTile {

    IKineticConductor getKineticConductor(EnumFacing dir);

    EnumFacing[] getValidSides();
}
