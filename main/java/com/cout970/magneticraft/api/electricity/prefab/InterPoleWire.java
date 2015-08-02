package com.cout970.magneticraft.api.electricity.prefab;

import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.ElectricUtils;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.IElectricPole;
import com.cout970.magneticraft.api.electricity.IInterPoleWire;
import com.cout970.magneticraft.api.util.VecDouble;
import com.cout970.magneticraft.api.util.VecInt;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class InterPoleWire implements IInterPoleWire{

	protected World w;
	protected VecInt start;
	protected VecInt end;
	//cache
	protected IElectricPole cache_start;
	protected IElectricPole cache_end;
	protected double distance;
	protected double energyFlow;
	
	public InterPoleWire(VecInt s, VecInt e){
		start = s;
		end = e;
		VecDouble vec = new VecDouble(s).add(new VecDouble(e).getOpposite());
		distance = vec.mag();
	}
	
	public InterPoleWire() {}

	public void iterate(){
		valanceConductors(this);
	}

	public double getEnergyFlow() {
		return energyFlow;
	}

	public void setEnergyFlow(double energyFlow) {
		this.energyFlow = energyFlow;
	}

	public IElectricPole getStart() {
		if(cache_start == null){
			cache_start = ElectricUtils.getElectricPole(start.getTileEntity(w));
		}
		return cache_start;
	}

	public IElectricPole getEnd() {
		if(cache_end == null){
			cache_end = ElectricUtils.getElectricPole(end.getTileEntity(w));
		}
		return cache_end;
	}
	
	public VecInt vecStart(){
		return start.copy();
	}
	
	public VecInt vecEnd(){
		return end.copy();
	}

	public double getDistance() {
		return distance;
	}
	
	public void save(NBTTagCompound nbt){
		start.save(nbt,"Start");
		end.save(nbt,"End");
		nbt.setDouble("EnergyFlow", energyFlow);
	}
	
	public void load(NBTTagCompound nbt){
		start = new VecInt(nbt, "Start");
		end = new VecInt(nbt, "End");
		energyFlow= nbt.getDouble("EnergyFlow");
		VecDouble vec = new VecDouble(start).add(new VecDouble(end).getOpposite());
		distance = vec.mag();
	}

	public static void valanceConductors(IInterPoleWire con) {
		if(con.getStart() == null || con.getEnd() == null)return;
		//the resistance of the connection
		double resistence = con.getDistance() * ElectricConstants.RESISTANE_COPPER_WIRE;
		IElectricConductor cond_start = con.getStart().getConductor();
		IElectricConductor cond_end = con.getEnd().getConductor();
		//the voltage differennce
		double deltaV = cond_start.getVoltage() - cond_end.getVoltage();
		//sanity check for infinite current
		if(Double.isNaN(con.getEnergyFlow()))con.setEnergyFlow(0);
		//the extra current from the last tick
		double current = con.getEnergyFlow();
		// (V - I*R) I*R is the voltage difference that this conductor should have using the ohm's law, and V the actual one
		//vDiff is the voltage difference between the current voltager difference and the proper voltage difference using the ohm's law
		double vDiff = (deltaV - current * resistence);
		//make sure the vDiff is not in the incorrect direction when the resistance is too big
		vDiff = Math.min(vDiff, Math.abs(deltaV));
		vDiff = Math.max(vDiff, -Math.abs(deltaV));
		// add to the next tick current an extra to get the proper voltage difference on the two conductors
		con.setEnergyFlow(con.getEnergyFlow() + (vDiff * cond_start.getIndScale())/cond_start.getVoltageMultiplier());	
		// to the extra current add the current generated by the voltage difference
		current += (deltaV * cond_start.getCondParallel())/(cond_start.getVoltageMultiplier());
		//moves the charge
		cond_start.applyCurrent(-current);
		cond_end.applyCurrent(current);
	}

	@Override
	public void setWorld(World w) {
		this.w = w;
	}

	@Override
	public World getWorld() {
		return w;
	}
}