package com.cout970.magneticraft.util.network;

import java.util.LinkedList;
import java.util.List;

import com.cout970.magneticraft.api.util.PathFinder;
import com.cout970.magneticraft.api.util.VectorOffset;

public class NetworkPathFinder extends PathFinder {

	public List<NetworkNode> nodes;
	public NetworkNode start;

	public NetworkPathFinder(NetworkNode start) {
		nodes = new LinkedList<>();
		this.start = start;
	}

	@Override
	public boolean step(VectorOffset coord) {
		List<NetworkNode> list = Finder.find(start, start.getParent().getWorldObj(), coord.getCoords(), false);
		boolean cont = false;
		for(NetworkNode nd : list){
			if(start.canAddToNetwork(nd)){
				nodes.add(nd);
				cont  = true;
			}
		}
		if(cont){
			addNeigBlocks(coord.getCoords());
		}
		return true;
	}
}
