package com.cout970.magneticraft.util.network;

import net.minecraft.tileentity.TileEntity;

public interface NetworkNode {

	BasicNetwork getNetwork();
	
	void setNetwork(BasicNetwork net);

	boolean canAddToNetwork(NetworkNode node);

	boolean isValid();

	TileEntity getParent();
}
