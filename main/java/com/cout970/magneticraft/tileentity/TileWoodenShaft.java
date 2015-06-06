package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.kinetic.IKineticConductor;
import com.cout970.magneticraft.api.kinetic.IKineticTile;
import com.cout970.magneticraft.api.kinetic.KineticConductor;
import com.cout970.magneticraft.api.kinetic.KineticType;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.util.Log;
import com.cout970.magneticraft.util.tile.TileKineticConductor;

public class TileWoodenShaft extends TileKineticConductor{

	public float rotation;
	private long time;

	@Override
	public MgDirection[] getValidSides() {
		return new MgDirection[]{getDirection(),getDirection().opposite()};
	}

	public MgDirection getDirection() {
		return MgDirection.getDirection(getBlockMetadata());
	}

	@Override
	public IKineticConductor initKineticCond() {
		return new KineticConductor(this);
	}

	public void updateEntity(){
		super.updateEntity();
//		Log.debug(kinetic.getWork());
	}

	public float getDelta() {
		long aux = time;
		time = System.nanoTime();
		return time - aux;
	}
}
