package com.cout970.magneticraft.api.electricity;

import com.cout970.magneticraft.api.util.VecInt;
import net.minecraft.tileentity.TileEntity;

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
    boolean canConnect(VecInt f);

    /**
     * the tileEntity that has the block
     *
     * @return
     */
    TileEntity getParent();

    boolean canAcceptEnergy(IIndexedConnection f);
}
