package com.cout970.magneticraft.util.network;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.api.util.VectorOffset;

public class BasicNetwork {

	protected List<NetworkNode> nodes = new LinkedList<>();
	protected NetworkNode start;
	
	public BasicNetwork(NetworkNode startPoint){
		nodes.add(startPoint);
		start = startPoint;
	}
	
	public NetworkNode getStart(){
		return start;
	}
	
	public boolean canAddToNetwork(NetworkNode node){
		return start.canAddToNetwork(node);
	}
	
	public int getNodeCount(){
		return nodes.size();
	}
	
	public NetworkNode[] getNodes(){
		return nodes.toArray(new NetworkNode[nodes.size()]);
	}
	
	public void addNode(NetworkNode node){
		if(!nodes.contains(node)){
			nodes.add(node);
			node.setNetwork(this);
			onNetworkChange();
		}
		node.setNetwork(this);
	}
	
	public void removeNode(NetworkNode node){
		if(nodes.contains(node)){
			nodes.remove(node);
			refresh();
		}
	}
	
	public void refresh(){
		NetworkNode startingPoint = null;
		for(NetworkNode node : nodes){
			if(node.isValid()){
				startingPoint = node;
				break;
			}
		}
		if(startingPoint == null){
			destroyNetwork();
			return;
		}
		NetworkPathFinder pathfinder = new NetworkPathFinder(startingPoint);
		pathfinder.init();
		pathfinder.addBlock(new VectorOffset(new VecInt(startingPoint.getParent()), VecInt.NULL_VECTOR));
		pathfinder.addNeigBlocks(new VecInt(startingPoint.getParent()));
		while(pathfinder.iterate()){
		}
		nodes.stream().filter(nd -> !pathfinder.nodes.contains(nd)).forEach(nd -> nd.setNetwork(null));
		nodes.clear();
		pathfinder.nodes.forEach(this::addNode);
		onNetworkChange();
	}
	
	protected void onNetworkChange() {}

	public void mergeWith(BasicNetwork net){
		nodes.forEach(net::addNode);
		nodes.clear();
		net.onNetworkChange();
	}

	public void destroyNetwork() {
		Iterator<NetworkNode> it = nodes.iterator();
		while(it.hasNext()){
			it.next().setNetwork(null);
			it.remove();
		}
	}
}
