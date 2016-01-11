package com.cout970.magneticraft.api.electricity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

/**
 * @author Cout970
 */
public interface IEnergyInterface {

    /**
     * @param watts
     * @return energy accepted from the other energy system
     */
    double applyEnergy(double watts);

    /**
     * the capacity to store energy
     *
     * @return
     */
    double getCapacity();

    /**
     * amount of energy stored
     *
     * @return
     */
    double getEnergyStored();

    /**
     * max amount of energy per tick, should be in Watts
     *
     * @return
     */
    double getMaxFlow();

    /**
     * @param f
     * @return
     */
    boolean canConnect(EnumFacing f);

    /**
     * the tileEntity that has the block
     *
     * @return
     */
    TileEntity getParent();

    boolean canAcceptEnergy(IIndexedConnection f);
}
