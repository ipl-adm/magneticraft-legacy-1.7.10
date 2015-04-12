package com.cout970.magneticraft.tileentity;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import buildcraft.api.fuels.BuildcraftFuelRegistry;
import buildcraft.api.fuels.IFuel;
import buildcraft.api.fuels.IFuelManager;

import com.cout970.magneticraft.api.electricity.BatteryConductor;
import com.cout970.magneticraft.api.electricity.Conductor;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.heat.HeatConductor;
import com.cout970.magneticraft.api.heat.IHeatConductor;
import com.cout970.magneticraft.api.heat.IHeatTile;
import com.cout970.magneticraft.api.util.EnergyConversor;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.client.gui.component.IBarProvider;
import com.cout970.magneticraft.client.gui.component.IGuiSync;
import com.cout970.magneticraft.update1_8.IFluidHandler1_8;
import com.cout970.magneticraft.util.Log;
import com.cout970.magneticraft.util.fluid.TankMg;
import com.cout970.magneticraft.util.tile.TileConductorLow;

public class TileCombustionEngine extends TileConductorLow implements IFluidHandler1_8, IHeatTile, IGuiSync, IBarProvider{

	private TankMg tank = new TankMg(this, 4000);
	public IHeatConductor heat = new HeatConductor(this, 600, 800);
	private float buffer;
	private IFuel fuel;
	private float prod,counter;
	private int maxProd;
	private int oldHeat;
	
	public TankMg getTank(){
		return tank;
	}
	
	public void updateEntity(){
		super.updateEntity();

		if(worldObj.isRemote)return;
		heat.iterate();
		if(((int)heat.getTemperature()) != oldHeat && worldObj.provider.getWorldTime() % 10 == 0){
			sendUpdateToClient();
			oldHeat = (int) heat.getTemperature();
		}
		if(buffer > 0 && cond.getVoltage() < ElectricConstants.MAX_VOLTAGE && heat.getTemperature() < 500 && isControled() && fuel != null){
			float speed = getSpeed();
			double p = EnergyConversor.RFtoW(fuel.getPowerPerCycle()) * speed;
			buffer -= speed;
			counter += p;
			cond.applyPower(p);
			heat.applyCalories(10);
		}

		if(buffer <= 0 || (fuel == null && getTank().getFluidAmount() >= 10)){
			FluidStack fluid = getTank().getFluid();
			if(fluid != null){
				IFuel f = BuildcraftFuelRegistry.fuel.getFuel(fluid.getFluid());
				if(f != null){
					buffer += f.getTotalBurningTime()/100;
					fuel = f;
					maxProd = (int) EnergyConversor.RFtoW(f.getPowerPerCycle());
					getTank().drain(10, true);
				}
			}
		}
		
		if(worldObj.getWorldTime() % 20 == 0){
			prod = counter/20;
			counter = 0;
		}
	}
	
	private float getSpeed() {
		return 1f-((int)(((heat.getTemperature()-25)*0.128f))/64f);
	}

	@Override
	public int fillMg(MgDirection from, FluidStack resource, boolean doFill) {
		return getTank().fill(resource, doFill);
	}

	@Override
	public FluidStack drainMg_F(MgDirection from, FluidStack resource, boolean doDrain) {
		return drainMg(from, resource.amount, doDrain);
	}

	@Override
	public FluidStack drainMg(MgDirection from, int maxDrain, boolean doDrain) {
		return getTank().drain(maxDrain, doDrain);
	}

	@Override
	public boolean canFillMg(MgDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public boolean canDrainMg(MgDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public FluidTankInfo[] getTankInfoMg(MgDirection from) {
		return new FluidTankInfo[]{getTank().getInfo()};
	}

	@Override
	public IElectricConductor initConductor() {
		return new BatteryConductor(this,ElectricConstants.RESISTANCE_COPPER_2X2,8000,ElectricConstants.GENERATOR_DISCHARGE,ElectricConstants.GENERATOR_CHARGE);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		buffer = nbt.getFloat("Buffer");
		getTank().readFromNBT(nbt);
		heat.load(nbt);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
		nbt.setFloat("Buffer", buffer);
		getTank().writeToNBT(nbt);
		heat.save(nbt);
	}

	@Override
	public IHeatConductor getHeatCond(VecInt c) {
		return heat;
	}

	@Override
	public void sendGUINetworkData(Container cont, ICrafting craft) {
		craft.sendProgressBarUpdate(cont, 0,(int) cond.getVoltage());
		craft.sendProgressBarUpdate(cont, 1,(int) cond.getStorage());
		craft.sendProgressBarUpdate(cont, 2,(int) heat.getTemperature());
		if(getTank().getFluidAmount() > 0){
			craft.sendProgressBarUpdate(cont, 3,getTank().getFluid().fluidID);
			craft.sendProgressBarUpdate(cont, 4,getTank().getFluidAmount());
		}else{
			craft.sendProgressBarUpdate(cont, 3, -1);
		}
		craft.sendProgressBarUpdate(cont, 5, (int)prod*100);
		craft.sendProgressBarUpdate(cont, 6, maxProd);
	}

	@Override
	public void getGUINetworkData(int id, int value) {
		if(id == 0)cond.setVoltage(value);
		if(id == 1)cond.setStorage(value);
		if(id == 2)heat.setTemperature(value);
		if(id == 3){
			if(value == -1){
				getTank().setFluid(null);
			}else{
				getTank().setFluid(new FluidStack(FluidRegistry.getFluid(value),1));
			}
		}
		if(id == 4){
			getTank().setFluid(new FluidStack(getTank().getFluid(),value));
		}
		if(id == 5)prod = value/100f;
		if(id == 6)maxProd = value;
	}

	@Override
	public String getMessage() {
		return "Generating: "+prod+"W";
	}

	@Override
	public float getLevel() {
		return Math.min(1, maxProd == 0 ? 0 : prod/maxProd);
	}
}
