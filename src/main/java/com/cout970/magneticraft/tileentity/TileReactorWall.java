package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.heat.IHeatConductor;
import com.cout970.magneticraft.api.heat.prefab.HeatConductor;
import com.cout970.magneticraft.util.IReactorComponent;
import com.cout970.magneticraft.util.tile.TileHeatConductor;

public class TileReactorWall extends TileHeatConductor implements IReactorComponent {

    @Override
    public IHeatConductor initHeatCond() {
        return new HeatConductor(this, 2000, 5000);
    }

    @Override
    public int getType() {
        return IReactorComponent.ID_WALL;
    }

}
