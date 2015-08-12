package com.cout970.magneticraft.util.energy;

import com.cout970.magneticraft.api.electricity.IEnergyInterface;
import com.cout970.magneticraft.api.electricity.IIndexedConnection;
import com.cout970.magneticraft.api.util.EnergyConversor;
import com.cout970.magneticraft.api.util.VecInt;

import mods.railcraft.api.electricity.IElectricGrid;
import mods.railcraft.api.electricity.IElectricGrid.ChargeHandler;
import mods.railcraft.api.electricity.IElectricGrid.ChargeHandler.ConnectType;
import net.minecraft.tileentity.TileEntity;

/**
 * 
 * @author Cout970
 *
 */
public class RailcraftChargeEnergyInteface implements IEnergyInterface{

	public ChargeHandler c;
	public IElectricGrid grid;
	
	public RailcraftChargeEnergyInteface(IElectricGrid g) {
		c = g.getChargeHandler();
		grid = g;
	}
	
	@Override
	public double applyEnergy(double watts) {
		double energy = Math.min(c.getCapacity()-c.getCharge(), EnergyConversor.WtoRC(watts));
		c.addCharge(energy);
		return EnergyConversor.RCtoW(energy);
	}

	@Override
	public double getCapacity() {
		return EnergyConversor.RCtoW(c.getCapacity());
	}

	@Override
	public double getEnergyStored() {
		return EnergyConversor.RCtoW(c.getCharge());
	}

	@Override
	public double getMaxFlow() {
		return EnergyConversor.RCtoW(512);
	}

	@Override
	public TileEntity getParent() {
		return grid.getTile();
	}

	@Override
	public boolean canConnect(VecInt f) {
		return c.getType() == ConnectType.BLOCK;
	}

	@Override
	public boolean canAcceptEnergy(IIndexedConnection f) {
		return true;
	}
}
