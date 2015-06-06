package com.cout970.magneticraft.api.kinetic;

import net.minecraft.tileentity.TileEntity;

public class KineticGenerator extends KineticConductor{

	public KineticGenerator(TileEntity p) {
		super(p);
		type = KineticType.Generator;
	}

	@Override
	public void iterate() {
		super.iterate();
		if(net == null){
			net = new KineticNetwork(this);
			net.findComponents();
		}
		if(net.world.getWorldTime() % 20 == 0){
			net.findComponents();
		}
	}
}
