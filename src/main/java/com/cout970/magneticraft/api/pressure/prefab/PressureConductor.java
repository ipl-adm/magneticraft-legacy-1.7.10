package com.cout970.magneticraft.api.pressure.prefab;

import java.util.List;

import com.cout970.magneticraft.api.pressure.IPressureConductor;
import com.cout970.magneticraft.api.pressure.PressureUtils;
import com.cout970.magneticraft.api.util.ConnectionClass;
import com.cout970.magneticraft.api.util.EnergyConversor;
import com.cout970.magneticraft.api.util.IConnectable;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.api.util.VecIntUtil;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class PressureConductor implements IPressureConductor{

	protected TileEntity parent;
	protected double volume;
	protected double temperature;
	protected double moles;
	protected Fluid currentGas;
	
	public PressureConductor(TileEntity t, double volume){
		parent = t;
		this.volume = volume;
	}
	
	@Override
	public TileEntity getParent() {
		return parent;
	}

	@Override
	public void iterate() {
		World w = parent.getWorldObj();
		if(w.isRemote)return;
		for(MgDirection dir : MgDirection.values()){
			TileEntity tile = MgUtils.getTileEntity(parent, dir);
			if(tile != null){
				List<IPressureConductor> conds = PressureUtils.getPressureCond(tile, dir.opposite().toVecInt());
				if(!conds.isEmpty()){
					int size = conds.size() + 1;
					double sum = getMoles();
					for(IPressureConductor pres : conds){
						sum += pres.getMoles();
					}
					double newValue = sum / size;
					setMoles(newValue);
					for(IPressureConductor pres : conds){
						pres.setMoles(newValue);
					}
				}
			}
		}
	}

	@Override
	public VecInt[] getValidConnections() {
		return VecIntUtil.FORGE_DIRECTIONS;
	}

	@Override
	public boolean isAbleToConnect(IConnectable cond, VecInt dir) {
		return true;
	}

	@Override
	public ConnectionClass getConnectionClass(VecInt v) {
		return ConnectionClass.FULL_BLOCK;
	}

	@Override
	public void save(NBTTagCompound nbt) {
		nbt.setDouble("vol", volume);
		nbt.setDouble("temp", temperature);
		nbt.setDouble("mol", moles);
	}

	@Override
	public void load(NBTTagCompound nbt) {
		volume = nbt.getDouble("vol");
		temperature = nbt.getDouble("temp");
		moles = nbt.getDouble("mol");
	}

	@Override
	public double getVolume() {
		return volume;
	}

	@Override
	public void setVolume(double vol) {
		volume = vol;
	}

	@Override
	public double getPressure() {
		return ((moles*temperature*R)/(volume/1000D))*1000;
	}

	@Override
	public double getTemperature() {
		return temperature;
	}

	@Override
	public void setTemperature(double temp) {
		temperature = temp;
	}

	@Override
	public void onBlockExplode() {}

	@Override
	public double getMoles() {
		return moles;
	}

	@Override
	public void setMoles(double moles) {
		this.moles = moles;
	}

	@Override
	public int applyGas(FluidStack gas, boolean doFill) {
		if(gas == null)return 0;
		if(gas.amount == 0)return 0;
		if(!gas.getFluid().isGaseous())return 0;
		if(currentGas == null || gas.getFluid().equals(currentGas)){
			if(doFill){
				currentGas = gas.getFluid();
				temperature = currentGas.getTemperature();
				moles += EnergyConversor.MBtoMOL(gas.amount);
			}
			return gas.amount;
		}
		return 0;
	}

	@Override
	public FluidStack drainGas(int amount, boolean doDrain) {
		if(currentGas == null)return null;
		if(amount <= 0)return null;
		int mB = (int) Math.min(amount, EnergyConversor.MOLtoMB(moles));
		if(mB > 0){
			if(doDrain){
				moles -=  EnergyConversor.MBtoMOL(mB);
			}
			return new FluidStack(currentGas, mB);
		}
		return null;
	}

	@Override
	public double getMaxPressure() {
		return EnergyConversor.BARtoPA(200);
	}

	@Override
	public Fluid getFluid() {
		return currentGas;
	}
}
