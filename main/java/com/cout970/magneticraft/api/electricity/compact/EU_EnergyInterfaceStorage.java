package com.cout970.magneticraft.api.electricity.compact;

import ic2.api.tile.IEnergyStorage;
import net.minecraft.tileentity.TileEntity;

import com.cout970.magneticraft.api.electricity.IEnergyInterface;
import com.cout970.magneticraft.api.electricity.IndexedConnection;
import com.cout970.magneticraft.api.util.EnergyConversor;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.util.Log;

public class EU_EnergyInterfaceStorage implements IEnergyInterface{

	public IEnergyStorage tile;
	
	public EU_EnergyInterfaceStorage(IEnergyStorage t){
		tile = t;
	}
	
	@Override
	public double applyEnergy(double watts) {
		double before = getEnergyStored();
		double after = EnergyConversor.EUtoW(tile.addEnergy((int) EnergyConversor.WtoEU(watts)));
		return after-before;
	}

	@Override
	public double getCapacity() {
		return EnergyConversor.EUtoJ(tile.getCapacity());
	}

	@Override
	public double getEnergyStored() {
		return EnergyConversor.EUtoJ(tile.getStored());
	}

	@Override
	public double getMaxFlow() {
		return EnergyConversor.EUtoW(512);
	}

	@Override
	public boolean canConnect(VecInt f) {
		return true;
	}

	@Override
	public TileEntity getParent() {
		return (TileEntity) tile;
	}

	@Override
	public boolean canAcceptEnergy(IndexedConnection f) {
		return true;
	}

}
