package com.cout970.magneticraft.util.network.pressure;

import com.cout970.magneticraft.api.pressure.PressurizedFluid;
import com.cout970.magneticraft.api.pressure.IPressureConductor;
import com.cout970.magneticraft.api.util.ConnectionClass;
import com.cout970.magneticraft.api.util.IConnectable;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.util.network.NetworkNode;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class NetworkPressureConductor implements IPressureConductor{

	public PressureNetwork network;
	
	public NetworkPressureConductor(PressureNetwork net) {
		network = net;
	}

	@Override
	public TileEntity getParent() {
		return network.getStart().getParent();
	}

	@Override
	public void iterate() {}

	@Override
	public VecInt[] getValidConnections() {
		return new VecInt[0];
	}

	@Override
	public boolean isAbleToConnect(IConnectable cond, VecInt dir) {
		return false;
	}

	@Override
	public ConnectionClass getConnectionClass(VecInt v) {
		return ConnectionClass.FULL_BLOCK;
	}

	@Override
	public void save(NBTTagCompound nbt) {}

	@Override
	public void load(NBTTagCompound nbt) {}

	@Override
	public double getVolume() {
		double vol = 0;
		for(NetworkNode nd : network.getNodes()){
			if(nd instanceof PressureNetworkNode && ((PressureNetworkNode) nd).getConductor() != null){
				vol += ((PressureNetworkNode) nd).getConductor().getVolumeNode();
			}
		}
		return vol;
	}

	@Override
	public void setVolume(double c) {}

	@Override
	public double getPressure() {
		return ((getMoles() * getTemperature() * R) / (getVolume() / 1000D)) * 1000;
	}

	@Override
	public double getMaxPressure() {
		double min = 0;
		for(NetworkNode nd : network.getNodes()){
			if(nd instanceof PressureNetworkNode && ((PressureNetworkNode) nd).getConductor() != null){
				if(min == 0){
					min = ((PressureNetworkNode) nd).getConductor().getMaxPressure();
				}else{
					min = Math.min(min, ((PressureNetworkNode) nd).getConductor().getMaxPressure());
				}
			}
		}
		return min;
	}

	@Override
	public double getMoles() {
		double mol = 0;
		for(NetworkNode nd : network.getNodes()){
			if(nd instanceof PressureNetworkNode && ((PressureNetworkNode) nd).getConductor() != null){
				mol += ((PressureNetworkNode) nd).getConductor().getMolesNode();
			}
		}
		return mol;
	}

	@Override
	public void setMoles(double moles) {
		double part = moles / network.getNodeCount();
		for(NetworkNode nd : network.getNodes()){
			if(nd instanceof PressureNetworkNode && ((PressureNetworkNode) nd).getConductor() != null){
				((PressureNetworkNode) nd).getConductor().setMolesNode(part);
			}
		}
	}

	@Override
	public double getTemperature() {
		for(NetworkNode nd : network.getNodes()){
			if(nd instanceof PressureNetworkNode && ((PressureNetworkNode) nd).getConductor() != null){
				return ((PressureNetworkNode) nd).getConductor().getTemperatureNode();
			}
		}
		return 0;
	}

	@Override
	public void setTemperature(double temp) {
		for(NetworkNode nd : network.getNodes()){
			if(nd instanceof PressureNetworkNode && ((PressureNetworkNode) nd).getConductor() != null){
				((PressureNetworkNode) nd).getConductor().setTemperatureNode(temp);
			}
		}
	}

	@Override
	public void onBlockExplode() {}

	@Override
	public Fluid getFluid() {
		for(NetworkNode nd : network.getNodes()){
			if(nd instanceof PressureNetworkNode && ((PressureNetworkNode) nd).getConductor() != null){
				return ((PressureNetworkNode) nd).getConductor().getFluidNode();
			}
		}
		return null;
	}

	@Override
	public void setFluid(Fluid fluid) {
		for(NetworkNode nd : network.getNodes()){
			if(nd instanceof PressureNetworkNode && ((PressureNetworkNode) nd).getConductor() != null){
				((PressureNetworkNode) nd).getConductor().setFluidNode(fluid);
			}
		}
	}
	
	@Override
	public int applyGas(FluidStack gas, boolean doFill) {
		return 0;
	}

	@Override
	public FluidStack drainGas(int amount, boolean doDrain) {
		return null;
	}

	@Override
	public PressurizedFluid moveFluid(PressurizedFluid pack) {
		if(pack.getAmount() < 0 || pack.getFluid() == null)return pack;
		setFluid(pack.getFluid());
		double total = getMoles() + pack.getAmount();
		setTemperature(getTemperature()*(getMoles()/total)+pack.getTemperature()*(pack.getAmount()/total));
		setMoles(total);
		return null;
	}
}
