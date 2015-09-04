package com.cout970.magneticraft.parts.fluid;

import com.cout970.magneticraft.api.pressure.IPressureMultipart;
import com.cout970.magneticraft.parts.MgPart;
import com.cout970.magneticraft.util.network.BasicNetwork;
import com.cout970.magneticraft.util.network.NetworkNode;
import com.cout970.magneticraft.util.network.pressure.PressureNetworkNode;

import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;

public abstract class PartPressurePipe extends MgPart implements PressureNetworkNode, IPressureMultipart{

	public PartPressurePipe(Item i) {
		super(i);
	}

	public BasicNetwork network;
	
	@Override
	public BasicNetwork getNetwork() {
		if(network == null){
			network = createNetwork();
			network.refresh();
		}
		return network;
	}

	public abstract BasicNetwork createNetwork();

	@Override
	public void setNetwork(BasicNetwork net) {
		network = net;
	}

	@Override
	public boolean canAddToNetwork(NetworkNode node) {
		return node instanceof PartPressurePipe;
	}

	@Override
	public boolean isValid() {
		return tile() != null;
	}

	@Override
	public TileEntity getParent() {
		return tile();
	}

}
