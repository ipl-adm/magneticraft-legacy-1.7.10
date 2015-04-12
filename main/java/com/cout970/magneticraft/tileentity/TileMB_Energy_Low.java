package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.electricity.CableCompound;
import com.cout970.magneticraft.api.electricity.Conductor;
import com.cout970.magneticraft.api.electricity.ConnectionClass;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.IElectricTile;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;

public class TileMB_Energy_Low extends TileMB_Base implements IElectricTile{

	public Conductor cond = new Conductor(this){
		@Override
		public boolean isAbleToConnect(IElectricConductor e, VecInt v) {
			return e.getConnectionClass(v.getOpposite()).orientation == null;
		}
		
		@Override
		public ConnectionClass getConnectionClass(VecInt v) {
			return ConnectionClass.CABLE_LOW;
		}
	};
	
	@Override
	public CableCompound getConds(VecInt dir, int Vtier) {
		return new CableCompound(cond);
	}

}
