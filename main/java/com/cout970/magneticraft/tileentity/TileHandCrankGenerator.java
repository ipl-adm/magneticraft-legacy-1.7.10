package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.kinetic.IKineticConductor;
import com.cout970.magneticraft.api.kinetic.KineticGenerator;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.util.Log;
import com.cout970.magneticraft.util.tile.TileKineticConductor;

public class TileHandCrankGenerator extends TileKineticConductor{

	public int tickCounter;
	
	@Override
	public IKineticConductor initKineticCond() {
		return new KineticGenerator(this);
	}

	@Override
	public MgDirection[] getValidSides() {
		return MgDirection.values();
	}

	public void updateEntity(){
		super.updateEntity();
		if(tickCounter > 0){
			tickCounter--;
			kinetic.getNetwork().applyForce(500);
		}
	}
}
