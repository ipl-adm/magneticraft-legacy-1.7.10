package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.heat.IHeatConductor;
import com.cout970.magneticraft.api.heat.prefab.HeatConductor;
import com.cout970.magneticraft.util.tile.TileHeatConductor;

/**
 * Created by cout970 on 23/12/2015.
 */
public class TileHeatCable extends TileHeatConductor {

    @Override
    public IHeatConductor initHeatCond() {
        return new HeatConductor(this, 1400);
    }
}
