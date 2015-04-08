package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.electricity.CableCompound;
import com.cout970.magneticraft.api.electricity.Conductor;
import com.cout970.magneticraft.api.electricity.IElectricTile;
import com.cout970.magneticraft.api.util.VecInt;

public class TileMB_Energy extends TileMB_Base implements IElectricTile{

	public Conductor cond = new Conductor(this);
	
	@Override
	public CableCompound getConds(VecInt dir, int Vtier) {
		return new CableCompound(cond);
	}

}
