package com.cout970.magneticraft.tileentity;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;

import com.cout970.magneticraft.api.electricity.BatteryConductor;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.heat.HeatConductor;
import com.cout970.magneticraft.api.heat.IHeatConductor;
import com.cout970.magneticraft.api.heat.IHeatTile;
import com.cout970.magneticraft.api.util.EnergyConversor;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.client.gui.component.IBurningTime;
import com.cout970.magneticraft.client.gui.component.IGuiSync;
import com.cout970.magneticraft.util.IManagerInventory;
import com.cout970.magneticraft.util.InventoryComponent;
import com.cout970.magneticraft.util.tile.TileConductorLow;

public class TileStirlingGenerator extends TileConductorLow implements IHeatTile,IManagerInventory,IGuiSync,IBurningTime{

	private static final double MAX_PRODUCTION = 1000;
	public IHeatConductor heat = new HeatConductor(this, 1400, 1000);
	public InventoryComponent inv = new InventoryComponent(this, 1, "Stirling generator");
	public int oldHeat;
	private int Progres;
	private boolean working;
	private int maxProgres;
	
	@Override
	public IElectricConductor initConductor() {
		return new BatteryConductor(this, ElectricConstants.RESISTANCE_BASE, 8000, ElectricConstants.GENERATOR_DISCHARGE, ElectricConstants.GENERATOR_CHARGE);
	}

	@Override
	public IHeatConductor getHeatCond(VecInt c) {
		return heat;
	}
	
	public void updateEntity(){
		super.updateEntity();
		if(!this.worldObj.isRemote){
			heat.iterate();
			if(worldObj.getWorldTime()%20 == 0){
				if(working && !isActive()){
					setActive(true);
				}else if(!working && isActive()){
					setActive(false);
				}
			}
			if(Progres > 0){
				//fuel to heat
				if(heat.getTemperature() < heat.getMaxTemp()-200 && isControled()){
					int i = 1;//burning speed
					if(Progres - i < 0){
						heat.applyCalories(EnergyConversor.FUELtoCALORIES(Progres));
						Progres = 0;
					}else{
						Progres -= i;
						heat.applyCalories(EnergyConversor.FUELtoCALORIES(i));
					}
					
				}
			}
			if(Progres <= 0){
				working = false;
				if(getInv().getStackInSlot(0) != null && isControled()){
					int fuel = TileEntityFurnace.getItemBurnTime(getInv().getStackInSlot(0));
					if(fuel > 0 && heat.getTemperature() < heat.getMaxTemp()){
						Progres = fuel;
						maxProgres = fuel;
						if(getInv().getStackInSlot(0) != null){
							getInv().getStackInSlot(0).stackSize--;
							if(getInv().getStackInSlot(0).stackSize <= 0){
								getInv().setInventorySlotContents(0, getInv().getStackInSlot(0).getItem().getContainerItem(getInv().getStackInSlot(0)));
							}
						}
						working = true;
						markDirty();
					}
				}
			}
			
			if(heat.getTemperature() > 30 && cond.getVoltage() < ElectricConstants.MAX_VOLTAGE){
				int prod = (int) Math.min(MAX_PRODUCTION, (heat.getTemperature()-30)*10);
				heat.drainCalories(EnergyConversor.WtoCALORIES(prod));
				cond.applyPower(prod);
			}
			
			if(((int)heat.getTemperature()) != oldHeat && worldObj.provider.getWorldTime() % 10 == 0){
				sendUpdateToClient();
				oldHeat = (int) heat.getTemperature();
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
	
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		heat.load(nbt);
		Progres = nbt.getInteger("Progres");
		maxProgres = nbt.getInteger("maxProgres");
		getInv().readFromNBT(nbt);
	}
	
	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
		nbt.setInteger("Progres", Progres);
		nbt.setInteger("maxProgres", maxProgres);
		heat.save(nbt);
		getInv().writeToNBT(nbt);
	}
	
	public InventoryComponent getInv(){
		return inv;
	}

	@Override
	public void sendGUINetworkData(Container cont, ICrafting craft) {
		craft.sendProgressBarUpdate(cont, 0, (int) cond.getVoltage());
		craft.sendProgressBarUpdate(cont, 1, (int) Progres);
		craft.sendProgressBarUpdate(cont, 2, cond.getStorage());
		craft.sendProgressBarUpdate(cont, 3, maxProgres);
		craft.sendProgressBarUpdate(cont, 4, (int)heat.getTemperature()*10);
	}

	@Override
	public void getGUINetworkData(int id, int value) {
		if(id == 0)cond.setVoltage(value);
		if(id == 1)Progres = value;
		if(id == 2)cond.setStorage(value);
		if(id == 3)maxProgres = value;
		if(id == 4)heat.setTemperature(value/10);
	}

	@Override
	public int getProgres() {
		return Progres;
	}

	@Override
	public int getMaxProgres() {
		return Math.max(maxProgres, 1);
	}
}
