package com.cout970.magneticraft.api.kinetic;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.util.Log;

import net.minecraft.tileentity.TileEntity;

public class KineticConductor implements IKineticConductor{

	public TileEntity tile;
	public KineticType type;
	public double lose = 0.1;
	public double speed;
	public double lastSpeed;
	
	public KineticNetwork net;
	
	public KineticConductor(TileEntity p) {
		tile = p;
		type = KineticType.Transport;
	}
	
	public KineticConductor(TileEntity p, double lose) {
		tile = p;
		this.lose = lose;
		type = KineticType.Consumer;
	}
	
	@Override
	public double getLose() {
		return lose;
	}

	@Override
	public void iterate() {
		lastSpeed = speed;
		speed = 0;
	}

	@Override
	public KineticNetwork getNetwork() {
		return net;
	}

	@Override
	public void setNetwork(KineticNetwork net) {
		this.net = net;
	}

	@Override
	public TileEntity getParent() {
		return tile;
	}

	@Override
	public KineticType getFunction() {
		return type;
	}

	@Override
	public double getWork() {
		return 0.5*getMass()*getSpeed()*getSpeed();
	}

	@Override
	public MgDirection[] getValidSides() {
		return ((IKineticTile)tile).getValidSides();
	}

	@Override
	public double getMass() {
		return 5;
	}

	@Override
	public double getSpeed() {
		return lastSpeed;
	}

	@Override
	public void setSpeed(double speed) {
		this.speed += speed;
	}

}
