package com.cout970.magneticraft.api.heat;

import net.minecraft.util.EnumFacing;

/**
 * @author Cout970
 */
public interface IHeatTile {

    /**
     * return de conductor in the block, if the VecInt is VecInt.NULL_VECTOR the method must return the conductor always
     *
     * @param c
     * @return
     */
    IHeatConductor[] getHeatCond(EnumFacing c);

}
