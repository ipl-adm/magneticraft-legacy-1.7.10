package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.prefab.ElectricConductor;
import com.cout970.magneticraft.util.tile.TileConductorMedium;
import net.minecraft.util.AxisAlignedBB;

/**
 * Created by cout970 on 23/12/2015.
 */
public class TileElectricCableMedium extends TileConductorMedium {

    @Override
    public IElectricConductor initConductor() {
        return new ElectricConductor(this, 1, ElectricConstants.RESISTANCE_COPPER_MED) {
            @Override
            public double getVoltageCapacity() {
                return ElectricConstants.CABLE_MEDIUM_CAPACITY;
            }
        };
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1);
    }
}
