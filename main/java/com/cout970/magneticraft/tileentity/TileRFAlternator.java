package com.cout970.magneticraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyHandler;

import com.cout970.magneticraft.api.electricity.ElectricConductor;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.util.EnergyConversor;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.util.Log;
import com.cout970.magneticraft.util.tile.TileConductorLow;

public class TileRFAlternator extends TileConductorLow implements IEnergyHandler{

	public int storage = 0;
	public int maxStorage = 100000;
	public double min = ElectricConstants.ALTERNATOR_DISCHARGE;
	
	@Override
	public IElectricConductor initConductor() {
		return new ElectricConductor(this){
			@Override
			public void iterate(){
				super.iterate();
				if(!isControled())return;
				if(getVoltage() < min && storage > 0){
					int change;
					change = (int) Math.min((min - getVoltage())*10, 500);
					change = Math.min(change, storage);
					applyPower(EnergyConversor.RFtoW(change));
					storage -= change;
				}
			}
			
			@Override
			public int getStorage() {
				return storage;
			}

			@Override
			public int getMaxStorage() {
				return maxStorage;
			}

			@Override
			public void setStorage(int charge) {
				storage = charge;
			}

			@Override
			public void applyCharge(int charge) {
				storage += charge;
				if(storage > maxStorage)
					storage = maxStorage;
			}

			@Override
			public void drainCharge(int charge){
				storage -= charge;
				if(storage < 0)storage = 0;
			}

			@Override
			public void save(NBTTagCompound nbt) {
				super.save(nbt);
				nbt.setInteger("Storage", storage);
			}

			@Override
			public void load(NBTTagCompound nbt) {
				super.load(nbt);
				storage = nbt.getInteger("Storage");
			}
		};
	}
	
	public MgDirection getDirection(){
		return MgDirection.getDirection(getBlockMetadata());
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return getDirection().toForgeDir() == from;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		int accepted = Math.max(Math.min(maxReceive, maxStorage-storage),0);
		if(!simulate)
			storage += accepted;
		return accepted;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		return 0;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return storage;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return maxStorage;
	}

}
