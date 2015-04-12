package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.electricity.Conductor;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.util.IReactorComponent;
import com.cout970.magneticraft.util.tile.TileConductorLow;

public class TileReactorController extends TileConductorLow implements IReactorComponent{

	@Override
	public IElectricConductor initConductor() {
		return new Conductor(this);
	}

	public void updateEntity(){
		super.updateEntity();
		
	}
	
	@Override
	public int getType() {
		return IReactorComponent.ID_CONTROLLER;
	}
}
