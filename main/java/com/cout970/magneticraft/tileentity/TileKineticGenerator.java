package com.cout970.magneticraft.tileentity;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyReceiver;

import com.cout970.magneticraft.api.electricity.CableCompound;
import com.cout970.magneticraft.api.electricity.Conductor;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.util.EnergyConversor;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.client.gui.component.IGuiSync;
import com.cout970.magneticraft.client.gui.component.IProductor;
import com.cout970.magneticraft.util.tile.TileConductorMedium;

public class TileKineticGenerator extends TileConductorMedium implements IEnergyHandler,IGuiSync,IProductor{

	protected EnergyStorage storage = new EnergyStorage(32000);
	public float rotation = 0;
	private long time;
	private int lastProd = 0;
	private int prodCount = 0;
	private int prodLastSec = 0;
	private boolean working;
	
	
	public MgDirection getOrientation(){
		return MgDirection.getDirection(getBlockMetadata()%6);
	}
	
	@Override
	public IElectricConductor initConductor() {
		return new Conductor(this,2,ElectricConstants.RESISTANCE_BASE);
	}
	
	@Override
	public CableCompound getConds(VecInt dir, int tier) {
		if(VecInt.NULL_VECTOR == dir){
			return new CableCompound(cond);
		}
		if(dir.toMgDirection() == getOrientation().opposite() && (tier == 2 || tier == -1))return new CableCompound(cond);
		return null;
	}

	public void updateEntity(){
		super.updateEntity();
		if(worldObj.isRemote)return;
		lastProd = 0;
		if(cond.getVoltage() > ElectricConstants.MACHINE_WORK*100 && isControled()){
			float f = (storage.getMaxEnergyStored()-storage.getEnergyStored())*10f/storage.getMaxEnergyStored();
			int min = (int) Math.min((cond.getVoltage() - ElectricConstants.MACHINE_WORK*100)/10, 40*Math.ceil(f));
			min = Math.min(storage.getMaxEnergyStored()-storage.getEnergyStored(),min);
			if(min > 0){
				cond.drainPower(EnergyConversor.RFtoW(min));
				storage.modifyEnergyStored(min);
				lastProd = min;
				prodCount += min;
				working = true;
			}else{
				working = false;
			}
		}else{
			working = false;
		}
		
		if(worldObj.getWorldTime() % 20 == 0){
			prodLastSec = prodCount;
			prodCount = 0;
			if(working && !isActive()){
				setActive(true);
			}else if(!working && isActive()){
				setActive(false);
			}
		}
		
		if(storage.getEnergyStored() > 0){
			TileEntity t = MgUtils.getTileEntity(this, getOrientation());
			if(t instanceof IEnergyReceiver){
				IEnergyReceiver e = (IEnergyReceiver) t;
				if(e.canConnectEnergy(getOrientation().opposite().getForgeDir())){
					int transfer = Math.min(400, storage.getEnergyStored());
					int acepted = e.receiveEnergy(getOrientation().opposite().getForgeDir(), transfer, false);
					storage.modifyEnergyStored(-acepted);
				}
			}
		}
	}

	private void setActive(boolean b) {
		if(b)
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, getBlockMetadata()+6, 2);
		else
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, getBlockMetadata()-6, 2);
	}

	public boolean isActive() {
		return getBlockMetadata() > 5;
	}
	
	public void onNeigChange(){
		super.onNeigChange();
	}

	public float getDelta() {
		long aux = time;
		time = System.nanoTime();
		return (float)((time-aux)*1E-6);
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return getOrientation().getForgeDir() == from;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		return storage.extractEnergy(maxExtract, simulate);
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return storage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return storage.getMaxEnergyStored();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		storage.readFromNBT(nbt);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
		storage.writeToNBT(nbt);
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		return 0;
	}

	@Override
	public void sendGUINetworkData(Container cont, ICrafting craft) {
		craft.sendProgressBarUpdate(cont, 0, (int)cond.getVoltage());
		craft.sendProgressBarUpdate(cont, 1, storage.getEnergyStored());
		craft.sendProgressBarUpdate(cont, 2, lastProd);
		craft.sendProgressBarUpdate(cont, 3, prodLastSec);
	}

	@Override
	public void getGUINetworkData(int id, int value) {
		if(id == 0)cond.setVoltage(value);
		if(id == 1)storage.setEnergyStored(value);
		if(id == 2)lastProd = value;
		if(id == 3)prodLastSec = value;
	}

	@Override
	public float getProductionInTheLastTick() {
		return lastProd;
	}

	@Override
	public float getProductionInTheLastSecond() {
		return prodLastSec;
	}

	@Override
	public float getMaxProduction() {
		return 400;
	}
}
