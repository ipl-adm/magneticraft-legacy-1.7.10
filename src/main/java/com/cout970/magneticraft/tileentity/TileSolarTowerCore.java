package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.heat.IHeatConductor;
import com.cout970.magneticraft.api.heat.prefab.HeatConductor;
import com.cout970.magneticraft.util.tile.TileHeatConductor;

public class TileSolarTowerCore extends TileHeatConductor {

    @Override
    public IHeatConductor initHeatCond() {
        return new HeatConductor(this, 3000, 750);
    }

}
