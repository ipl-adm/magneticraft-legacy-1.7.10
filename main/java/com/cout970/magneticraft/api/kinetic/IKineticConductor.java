package com.cout970.magneticraft.api.kinetic;

import com.cout970.magneticraft.api.util.MgDirection;

import net.minecraft.tileentity.TileEntity;

public interface IKineticConductor {
	
	public double getWork();
	
	public double getLose();
	public double getMass();
	
	public double getSpeed();
	public void setSpeed(double speed);
	
	public void iterate();
	
	public KineticNetwork getNetwork();
	public void setNetwork(KineticNetwork net);
	
	public TileEntity getParent();
	
	public KineticType getFunction();
	
	public MgDirection[] getValidSides();

	
}
