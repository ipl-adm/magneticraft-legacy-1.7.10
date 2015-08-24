package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.heat.IHeatConductor;
import com.cout970.magneticraft.api.heat.prefab.HeatConductor;
import com.cout970.magneticraft.util.tile.TileHeatConductor;

public class TileHeatResistance extends TileHeatConductor {

    public IHeatConductor initHeatCond() {
        return new HeatConductor(this, 2800, 1000, 9.5D);
    }
}
