package com.cout970.magneticraft.util.network.pressure;

import java.util.ArrayList;
import java.util.List;

import com.cout970.magneticraft.api.pressure.IPressureConductor;
import com.cout970.magneticraft.api.pressure.PressureUtils;
import com.cout970.magneticraft.api.pressure.prefab.PressureConductor;
import com.cout970.magneticraft.api.util.EnergyConversor;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.util.network.NetworkNode;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class NodePressureConductor extends PressureConductor{

	public NetworkNode node;
	
	public NodePressureConductor(TileEntity t, double volume, NetworkNode node) {
		super(t, volume);
		this.node = node;
	}
	
	public PressureNetwork getNet(){
		return ((PressureNetwork) node.getNetwork());
	}
	
	@Override
    public void iterate() {
        World w = parent.getWorldObj();
        if (w.isRemote)
            return;
        if (getFluid() == null)
            return;
        for (MgDirection dir : MgDirection.values()) {
            TileEntity tile = MgUtils.getTileEntity(parent, dir);
            if (tile != null) {
                List<IPressureConductor> pre = PressureUtils.getPressureCond(tile, dir.opposite().toVecInt());
                List<IPressureConductor> conds = new ArrayList<IPressureConductor>();
                for (IPressureConductor p : pre) {
                	if(p instanceof NodePressureConductor){
                		if(((NodePressureConductor)p).node.getNetwork() != node.getNetwork()){
                			if (p.getFluid() == null) {
                    			p.setFluid(getFluid());
                    			p.setTemperature(getTemperature());
                    			conds.add(p);
                    		} else if (p.getFluid() == getFluid()) {
                    			conds.add(p);
                    		}
                		}
                	}else{
                		if (p.getFluid() == null) {
                			p.setFluid(getFluid());
                			p.setTemperature(getTemperature());
                			conds.add(p);
                		} else if (p.getFluid() == getFluid()) {
                			conds.add(p);
                		}
                	}
                }
                for (IPressureConductor p : conds) {
                    double sum = getMoles() + p.getMoles();
                    double vol = getVolume() + p.getVolume();
                    setMoles(sum * (getVolume() / vol));
                    p.setMoles(sum * (p.getVolume() / vol));
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
    
    public void setMolesNode(double mol){
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
        return EnergyConversor.BARtoPA(200);
    }

    @Override
    public Fluid getFluid() {
        return currentGas;
    }

    @Override
    public void setFluid(Fluid f) {
        currentGas = f;
    }
}
