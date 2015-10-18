package com.cout970.magneticraft.util.network.pressure;

import java.util.List;

import com.cout970.magneticraft.api.pressure.IPressureConductor;
import com.cout970.magneticraft.api.pressure.prefab.PressureConductor;
import com.cout970.magneticraft.api.util.EnergyConverter;
import com.cout970.magneticraft.util.network.NetworkNode;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class NodePressureConductor extends PressureConductor {

	public NetworkNode node;

	public NodePressureConductor(TileEntity t, double volume, NetworkNode node) {
		super(t, volume);
		this.node = node;
	}

	public PressureNetwork getNet() {
		return ((PressureNetwork) node.getNetwork());
	}
	
	public void filter(List<IPressureConductor> data, List<IPressureConductor> result){
    	for (IPressureConductor p : data) {
    		
    		if (p instanceof NodePressureConductor) {
				if (((NodePressureConductor) p).node.getNetwork() != node.getNetwork()) {
					if (p.getFluid() == null || p.getFluid() == getFluid()) {
						result.add(p);
					}
				}
			}else{
				if (p.getFluid() == null || p.getFluid() == getFluid()) {
					result.add(p);
				}
			}
    	}
    }

	@Override
	public double getVolume() {
		return getNet().getPressureCond().getVolume();
	}

	public double getVolumeNode() {
		return volume;
	}

	@Override
	public void setVolume(double vol) {
		volume = vol;
	}

	@Override
	public double getPressure() {
		return getNet().getPressureCond().getPressure();
	}

	@Override
	public double getTemperature() {
		return getNet().getPressureCond().getTemperature();
	}

	public double getTemperatureNode() {
		return temperature;
	}

	@Override
	public void setTemperature(double temp) {
		getNet().getPressureCond().setTemperature(temp);
	}

	public void setTemperatureNode(double temp) {
		temperature = temp;
	}

	@Override
	public double getMoles() {
		return getNet().getPressureCond().getMoles();
	}

	public double getMolesNode() {
		return moles;
	}

	@Override
	public void setMoles(double mol) {
		getNet().getPressureCond().setMoles(mol);
	}

	public void setMolesNode(double mol) {
		moles = mol;
	}

	@Override
	public int applyGas(FluidStack gas, boolean doFill) {
		return getNet().getPressureCond().applyGas(gas, doFill);
	}

	@Override
	public FluidStack drainGas(int amount, boolean doDrain) {
		return getNet().getPressureCond().drainGas(amount, doDrain);
	}

	@Override
	public double getMaxPressure() {
		return EnergyConverter.BARtoPA(200);
	}
	
	@Override
    public Fluid getFluid() {
		return getNet().getPressureCond().getFluid();
    }

    @Override
    public void setFluid(Fluid f) {
    	getNet().getPressureCond().setFluid(f);
    }
	
    public Fluid getFluidNode() {
        return currentGas;
    }

    public void setFluidNode(Fluid f) {
        currentGas = f;
    }
}
