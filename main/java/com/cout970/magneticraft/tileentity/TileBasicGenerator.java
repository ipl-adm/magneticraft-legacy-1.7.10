package com.cout970.magneticraft.tileentity;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

import com.cout970.magneticraft.api.electricity.BatteryConductor;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.heat.HeatConductor;
import com.cout970.magneticraft.api.heat.IHeatConductor;
import com.cout970.magneticraft.api.heat.IHeatTile;
import com.cout970.magneticraft.api.util.EnergyConversor;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.client.gui.component.IBurningTime;
import com.cout970.magneticraft.client.gui.component.IGuiSync;
import com.cout970.magneticraft.update1_8.IFluidHandler1_8;
import com.cout970.magneticraft.util.IManagerInventory;
import com.cout970.magneticraft.util.InventoryComponent;
import com.cout970.magneticraft.util.fluid.TankMg;
import com.cout970.magneticraft.util.tile.TileConductorLow;

public class TileBasicGenerator extends TileConductorLow implements IFluidHandler1_8,IGuiSync,IManagerInventory,IBurningTime,IHeatTile{

	//cook
	public float Progres = 0;
	public int maxProgres;
	//heat values 25-120
	public int maxHeat = 500;
	public IHeatConductor heat = new HeatConductor(this, 1400, 1000);
	//gui data
	public int steamProduction;
	public int electricProduction;
	
	public float steamProductionM;
	public float electricProductionM;
	//steam tank
	public TankMg steam = new TankMg(this, 4000);
	public TankMg water = new TankMg(this, 2000);
	
	private InventoryComponent inv = new InventoryComponent(this, 1, "Basic Generator");
	private int oldHeat;
	private boolean working;
	
	@Override
	public IElectricConductor initConductor() {
		return new BatteryConductor(this, ElectricConstants.RESISTANCE_BASE, 16000, ElectricConstants.GENERATOR_DISCHARGE, ElectricConstants.GENERATOR_CHARGE);
	}
	
	@Override
	public void updateEntity(){
		super.updateEntity();
		if(worldObj.isRemote)return;
		heat.iterate();
		if(((int)heat.getTemperature()) != oldHeat && worldObj.provider.getWorldTime() % 20 == 0){
			sendUpdateToClient();
			oldHeat = (int) heat.getTemperature();
		}
		if(worldObj.provider.getWorldTime() % 20 == 0){
			steamProduction = 0;
			electricProduction = 0;
			if(working && !isActive()){
				setActive(true);
			}else if(!working && isActive()){
				setActive(false);
			}
		}
		if(Progres > 0){
			//fuel to temp
			int extract = 2;
			if(heat.getTemperature() < maxHeat && isControled()){
				heat.applyCalories(EnergyConversor.FUELtoCALORIES(extract));
				if(Progres - extract < 0){
					Progres = 0;
					working = false;
				}else{
					Progres -= extract;
				}
			}

		}
		//temp to steam
		if(heat.getTemperature() > 100){
			int change = Math.min(water.getFluidAmount(),EnergyConversor.STEAMtoWATER( steam.getCapacity()-steam.getFluidAmount()));
			change = Math.min(change,2);
			if(change > 0){
				heat.drainCalories(EnergyConversor.WATERtoSTEAM_HEAT(change));
				water.drain(change, true);
				steam.fill(FluidRegistry.getFluidStack("steam", EnergyConversor.WATERtoSTEAM(change)), true);
				steamProduction += EnergyConversor.WATERtoSTEAM(change);
			}
		}
			//steam to power
		int gas = Math.min(steam.getFluidAmount(),(int)EnergyConversor.KWtoSTEAM(2));
		if(gas > 0 && cond.getVoltage() < ElectricConstants.MAX_VOLTAGE && isControled()){
			cond.applyPower(EnergyConversor.STEAMtoW(gas));
			electricProduction += EnergyConversor.STEAMtoKW(gas);
			steam.drain(gas, true);
		}
		if(Progres <= 0){
			ItemStack a = getInv().getStackInSlot(0);
			if(a != null && isControled()){
				int fuel = TileEntityFurnace.getItemBurnTime(a);
				if(fuel > 0 && cond.getVoltage() < 120 && steam.getFluidAmount() < 1){
					Progres = fuel;
					maxProgres = fuel;
					if(a != null){
						a.stackSize--;
						if(a.stackSize <= 0){
							a = a.getItem().getContainerItem(a);
						}
						getInv().setInventorySlotContents(0, a);
					}
					working = true;
					markDirty();
				}else working = false;
			}else working = false;
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

	//Save & Load

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		Progres = nbt.getFloat("progres");
		heat.load(nbt);
		water.readFromNBT(nbt,"water");
		steam.readFromNBT(nbt,"steam");
		getInv().readFromNBT(nbt);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setFloat("progres", Progres);
		heat.save(nbt);
		water.writeToNBT(nbt,"water");
		steam.writeToNBT(nbt,"steam");
		getInv().writeToNBT(nbt);
	}

	@Override
	public void sendGUINetworkData(Container cont, ICrafting c) {
		c.sendProgressBarUpdate(cont, 0, (int)cond.getVoltage());
		c.sendProgressBarUpdate(cont, 1, (int)cond.getStorage());
		c.sendProgressBarUpdate(cont, 2, (int)Progres);
		c.sendProgressBarUpdate(cont, 3, maxProgres);
		c.sendProgressBarUpdate(cont, 4, (int)Math.ceil(heat.getTemperature()));
		c.sendProgressBarUpdate(cont, 5, steam.getFluidAmount());
		c.sendProgressBarUpdate(cont, 6, water.getFluidAmount());
		if(worldObj.provider.getWorldTime() % 20 == 0){
			c.sendProgressBarUpdate(cont, 7, steamProduction);
			c.sendProgressBarUpdate(cont, 8, electricProduction);
		}
	}

	@Override
	public void getGUINetworkData(int id, int value) {
		if(id == 0)cond.setVoltage(value);
		if(id == 1)cond.setStorage(value);
		if(id == 2)Progres = value;
		if(id == 3)maxProgres = value;
		if(id == 4)heat.setTemperature(value);
		if(id == 5)steam.setFluid(FluidRegistry.getFluidStack("steam", value));
		if(id == 6)water.setFluid(FluidRegistry.getFluidStack("water", value));
		if(id == 7)steamProductionM = value/20f;
		if(id == 8)electricProductionM = value/20f;
	}

	@Override
	public int fill(MgDirection from, FluidStack resource, boolean doFill) {
		if(resource != null && resource.fluidID == FluidRegistry.getFluidID("water"))
			return water.fill(resource, doFill);
		return 0;
	}

	@Override
	public FluidStack drain(MgDirection from, FluidStack resource,
			boolean doDrain) {
		return drain(from, resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(MgDirection from, int maxDrain, boolean doDrain) {
		return steam.drain(maxDrain, doDrain);
	}

	@Override
	public boolean canFill(MgDirection from, Fluid fluid) {
		return FluidRegistry.WATER == fluid;
	}

	@Override
	public boolean canDrain(MgDirection from, Fluid fluid) {
		return FluidRegistry.getFluid("steam") == fluid;
	}

	@Override
	public FluidTankInfo[] getTankInfo(MgDirection from) {
		return new FluidTankInfo[]{water.getInfo(),steam.getInfo()};
	}

	@Override
	public int getProgres() {
		return (int) Progres;
	}

	@Override
	public int getMaxProgres() {
		return maxProgres;
	}

	@Override
	public IHeatConductor getHeatCond(VecInt c){
		return heat;
	}
	
	public InventoryComponent getInv() {
		return inv;
	}
}
