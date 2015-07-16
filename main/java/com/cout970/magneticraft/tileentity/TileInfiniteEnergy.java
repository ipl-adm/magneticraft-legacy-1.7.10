package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.electricity.CompoundElectricCables;
import com.cout970.magneticraft.api.electricity.ElectricConductor;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.IElectricTile;
import com.cout970.magneticraft.api.util.VecInt;

public class TileInfiniteEnergy extends TileBase implements IElectricTile{

	private IElectricConductor cond0 = new ElectricConductor(this, 0, 0.001){
		@Override
		public void computeVoltage() {
			V = ElectricConstants.MAX_VOLTAGE*getVoltageMultiplier();
			I = 0;
			Itot = Iabs*0.5;
			Iabs = 0;
		}
	};
	private IElectricConductor cond2 = new ElectricConductor(this, 2, 0.001){
		@Override
		public void computeVoltage() {
			V = ElectricConstants.MAX_VOLTAGE*getVoltageMultiplier();
			I = 0;
			Itot = Iabs*0.5;
			Iabs = 0;
		}
	};

	@Override
	public CompoundElectricCables getConds(VecInt dir, int Vtier) {
		if(Vtier == 0)return new CompoundElectricCables(cond0);
		if(Vtier == 2)return new CompoundElectricCables(cond2);
		return null;
	}

}
