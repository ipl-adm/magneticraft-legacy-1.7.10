package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.prefab.ElectricConductor;
import com.cout970.magneticraft.util.tile.TileConductorLow;
import net.minecraft.util.AxisAlignedBB;

/**
 * Created by cout970 on 23/12/2015.
 */
public class TileElectricCableLow extends TileConductorLow {
    @Override
    public IElectricConductor initConductor() {
        return new ElectricConductor(this, ElectricConstants.RESISTANCE_COPPER_LOW) {
            @Override
            public double getVoltageCapacity() {
                return ElectricConstants.CABLE_LOW_CAPACITY;
            }
        };
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1);
    }
}
