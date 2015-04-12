package com.cout970.magneticraft.tileentity;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

import com.cout970.magneticraft.api.electricity.BatteryConductor;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.util.EnergyConversor;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.client.gui.component.IGuiSync;
import com.cout970.magneticraft.client.gui.component.IProductor;
import com.cout970.magneticraft.update1_8.IFluidHandler1_8;
import com.cout970.magneticraft.util.fluid.TankMg;
import com.cout970.magneticraft.util.tile.TileConductorLow;

public class TileSteamEngine extends TileConductorLow implements IFluidHandler1_8,IGuiSync,IProductor{

	public static final int STEAM_LIMIT = 40;
	public float animation;
	private long time;
	public TankMg tank = new TankMg(this, 6000);
	private boolean working;
	//info
	public int steamConsumition;
	public int electricProduction;
	public float steamConsumitionM;
	public float electricProductionM;

	public float getDelta() {
		long aux = time;
		time = System.nanoTime();
		return (float)((time-aux)*1E-6);
	}
	
	public void updateEntity(){
		super.updateEntity();
		if(worldObj.isRemote)return;
		if(worldObj.provider.getWorldTime() % 20 == 0){
			steamConsumitionM = steamConsumition;
			electricProductionM = electricProduction;
			steamConsumition = 0;
			electricProduction = 0;
			if(working && !isActive()){
				setActive(true);
			}else if(!working && isActive()){
				setActive(false);
			}
		}
		if(tank.getFluidAmount() > 0 && cond.getVoltage() <= ElectricConstants.MAX_VOLTAGE && isControled()){
			int steam = Math.min(tank.getFluidAmount(), STEAM_LIMIT);
			if(steam > 0){
				tank.drain(steam, true);
				cond.applyPower(EnergyConversor.STEAMtoKW(steam)*1000);
				electricProduction += EnergyConversor.STEAMtoKW(steam);
				steamConsumition += steam;
				working = true;
			}else{
				working = false;
			}
		}else{
			working = false;
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

	@Override
	public int fill(MgDirection from, FluidStack resource, boolean doFill) {
		if(resource != null && "steam".equalsIgnoreCase(FluidRegistry.getFluidName(resource))){
			return tank.fill(resource, doFill);
		}
		return 0;
	}

	@Override
	public FluidStack drain(MgDirection from, FluidStack resource,
			boolean doDrain) {
		return null;
	}

	@Override
	public FluidStack drain(MgDirection from, int maxDrain, boolean doDrain) {
		return null;
	}

	@Override
	public boolean canFill(MgDirection from, Fluid fluid) {
		return fluid != null && FluidRegistry.getFluidName(fluid.getID()) == "steam";
	}

	@Override
	public boolean canDrain(MgDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(MgDirection from) {
		return new FluidTankInfo[]{tank.getInfo()};
	}

	@Override
	public IElectricConductor initConductor() {
		return new BatteryConductor(this, ElectricConstants.RESISTANCE_COPPER_2X2, 8000, ElectricConstants.GENERATOR_DISCHARGE, ElectricConstants.GENERATOR_CHARGE);
	}

	@Override
	public void sendGUINetworkData(Container cont, ICrafting c) {
		c.sendProgressBarUpdate(cont, 0, (int)cond.getVoltage());
		c.sendProgressBarUpdate(cont, 1, (int)cond.getStorage());
		c.sendProgressBarUpdate(cont, 3, tank.getFluidAmount());
		c.sendProgressBarUpdate(cont, 4, (int) steamConsumitionM);
		c.sendProgressBarUpdate(cont, 5, (int) (electricProductionM*1000));
	}

	@Override
	public void getGUINetworkData(int id, int value) {
		if(id == 0)cond.setVoltage(value);
		if(id == 1)cond.setStorage(value);
		if(id == 3)tank.setFluid(FluidRegistry.getFluidStack("steam", value));
		if(id == 4)steamConsumitionM = value;
		if(id == 5)electricProductionM = value;
	}

	@Override
	public float getProductionInTheLastTick() {
		return 0;
	}

	@Override
	public float getProductionInTheLastSecond() {
		return electricProductionM;
	}

	@Override
	public float getMaxProduction() {
		return (float) (EnergyConversor.STEAMtoKW(STEAM_LIMIT)*1000);
	}

}
