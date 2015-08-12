package com.cout970.magneticraft.util.fluid;

import java.util.ArrayList;
import java.util.List;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;

import net.minecraft.tileentity.TileEntity;

public class FluidPathfinder {

	private List<IFluidTransport> visited = new ArrayList<IFluidTransport>();
	
	private IFluidTransport from;
	private IFluidTransport to;
	
	public FluidPathfinder(IFluidTransport from, IFluidTransport to) {
		visited.add(from);
		this.from = from;
		list(from);
		this.to = to;
	}
	
	private void list(IFluidTransport f){
		TileEntity from = f.getTileEntity();
		for(MgDirection dir : MgDirection.values()){
			TileEntity tile = MgUtils.getTileEntity(from, dir);
			if(tile != null){
				if(FluidUtils.isPipe(tile)){
					IFluidTransport e = FluidUtils.getFluidTransport(tile);
					if(this.from.isComaptible(e)){
						if(e.canConectOnSide(dir.opposite()) && f.canConectOnSide(dir)){
							if(!visited.contains(e)){
								visited.add(e);
								list(e);
							}
						}
					}
				}
			}
		}
	}

	public boolean canFluidGoToEnd(){
		if(to == null)return false;
		return visited.contains(to);
	}
	
	
	public List<IFluidTransport> getPipes(){
		return visited;
	}
}
