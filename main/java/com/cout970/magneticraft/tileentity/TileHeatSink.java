package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.heat.CompoundHeatCables;
import com.cout970.magneticraft.api.heat.HeatConductor;
import com.cout970.magneticraft.api.heat.IHeatConductor;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.util.tile.TileHeatConductor;

public class TileHeatSink extends TileHeatConductor{

	@Override
	public IHeatConductor initHeatCond() {
		return new HeatConductor(this, 600, 800);
	}
	
	@Override
	public CompoundHeatCables getHeatCond(VecInt c) {
		return new CompoundHeatCables(heat);
	}
	
	public void updateEntity(){
		super.updateEntity();
		if(worldObj.isRemote)return;
		if(heat.getTemperature() > 25){
			double diff = Math.min(heat.getTemperature()-25, 125);
			heat.drainCalories(diff*0.5);
		}
	}

	public MgDirection getDirection() {
		return MgDirection.getDirection(getBlockMetadata());
	}
}
