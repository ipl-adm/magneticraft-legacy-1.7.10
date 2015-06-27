package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.electricity.CompoundElectricCables;
import com.cout970.magneticraft.api.electricity.ElectricConductor;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.IndexedConnection;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.util.tile.TileConductorLow;

public class TileElectricSwitch extends TileConductorLow{
	
	private boolean powerCut;

	@Override
	public IElectricConductor initConductor() {
		return new ElectricConductor(this){
			@Override
			public boolean canFlowPower(IndexedConnection con) {
				return !powerCut;
			}
		};
	}
	
	@Override
	public CompoundElectricCables getConds(VecInt dir, int tier) {
		if(tier != 0 && tier !=-1)return null;
		return new CompoundElectricCables(cond);
	}
	
	public void onNeigChange(){
		powered = isPowered();
		if(powered){
			powerCut = true;
		}else{
			powerCut = false;
		}
	}

	public void setResistance(double res){
		cond.setResistence(res);
	}
	
	public double getResistance(){
		return cond.getResistance();
	}

}
