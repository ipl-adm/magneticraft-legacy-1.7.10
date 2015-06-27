package com.cout970.magneticraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import mods.railcraft.api.electricity.IElectricGrid;
import mods.railcraft.api.electricity.IElectricGrid.ChargeHandler.ConnectType;

import com.cout970.magneticraft.api.electricity.ElectricConductor;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.util.EnergyConversor;
import com.cout970.magneticraft.util.tile.TileConductorLow;

import cpw.mods.fml.common.Optional.Interface;

public class TileRCAlternator extends TileConductorLow implements IElectricGrid{

	private ChargeHandler charge = new ChargeHandler(this, ConnectType.BLOCK);
	public int maxStorage = 10000;
	public double level = ElectricConstants.BATTERY_DISCHARGE;
	
	@Override
	public IElectricConductor initConductor() {
		return new ElectricConductor(this){
			@Override
			public void iterate(){
				super.iterate();
				if(!isControled())return;
				if(getVoltage() < level && charge.getCharge() > 0){
					int change;
					change = (int) Math.min((level - getVoltage())*10, 512);
					change = (int) Math.min(change, charge.getCharge());
					applyPower(EnergyConversor.EUtoW(change));
					charge.addCharge(-change);
				}else if (getVoltage() > level && charge.getCharge() < maxStorage){
					int change;
					change = (int) Math.min((getVoltage() - level)*10, 512);
					change = (int) Math.min(change, maxStorage - charge.getCharge());
					drainPower(EnergyConversor.EUtoW(change));
					charge.addCharge(change);
				}
			}

			@Override
			public int getStorage() {
				return (int) charge.getCharge();
			}

			@Override
			public int getMaxStorage() {
				return maxStorage;
			}

			@Override
			public void setStorage(int charg) {
				charge.setCharge(charg);
			}

			@Override
			public void applyCharge(int charg) {
				charge.addCharge(charg);
				if(charge.getCharge() > maxStorage)
					charge.addCharge(maxStorage);
			}

			@Override
			public void drainCharge(int charg){
				charge.addCharge(-charg);
				if(charge.getCharge() < 0)charge.setCharge(0);
			}
		};
	}

	@Override
	public ChargeHandler getChargeHandler() {
		return charge;
	}

	@Override
	public TileEntity getTile() {
		return this;
	}
	
	@Override
	public void updateEntity(){
		super.updateEntity();
		if(worldObj.isRemote)return;
		charge.tick();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		charge.readFromNBT(nbt);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
		charge.writeToNBT(nbt);
	}

}
