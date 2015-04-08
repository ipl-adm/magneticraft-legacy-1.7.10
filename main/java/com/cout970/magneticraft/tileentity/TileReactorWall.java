package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.heat.HeatConductor;
import com.cout970.magneticraft.api.heat.IHeatConductor;
import com.cout970.magneticraft.util.tile.TileHeatConductor;

public class TileReactorWall extends TileHeatConductor{

	@Override
	public IHeatConductor initHeatCond() {
		return new HeatConductor(this, 2000, 5000);
	}
	
}
