package com.cout970.magneticraft.parts.micro;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.tileentity.TileEntity;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Vector3;

import com.cout970.magneticraft.ManagerItems;
import com.cout970.magneticraft.api.heat.CompoundHeatCables;
import com.cout970.magneticraft.api.heat.HeatConductor;
import com.cout970.magneticraft.api.heat.IHeatConductor;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.client.tilerender.TileRenderCableHigh;
import com.cout970.magneticraft.client.tilerender.TileRenderHeatCable;

public class PartHeatCable extends PartHeat{

	public byte conMask = -1;
	
	public PartHeatCable() {
		super(ManagerItems.partheatcable);
	}

	@Override
	public void create() {
		heat = new HeatConductor(getTile(), 1400, 1000);
	}

	@Override
	public void updateConnections() {
		conMask = 0;
		for(MgDirection d : MgDirection.values()){
			TileEntity t = MgUtils.getTileEntity(tile(), d);
			CompoundHeatCables c = MgUtils.getHeatCond(t, d.toVecInt().getOpposite());
			if(c != null && c.count() > 0){
				conMask |= (1 << d.ordinal());
			}
		}
	}
	
	@Override
	public void onNeighborChanged() {
		super.onNeighborChanged();
		conMask = -1;
	}
	
	public static List<Cuboid6> boxes = new ArrayList<Cuboid6>();
	
	static{
		double w = 6/16d;
		boxes.add(new Cuboid6(0.5-w, 0, 0.5-w, 0.5+w, 0.5-w, 0.5+w));//up
		boxes.add(new Cuboid6(0.5-w, 0.5+w, 0.5-w, 0.5+w, 1, 0.5+w));//down
		boxes.add(new Cuboid6(0.5-w, 0.5-w, 0, 0.5+w, 0.5+w, 0.5-w));//north
		boxes.add(new Cuboid6(0.5-w, 0.5-w, 0.5+w, 0.5+w, 0.5+w, 1));//south
		boxes.add(new Cuboid6(0, 0.5-w, 0.5-w, 0.5-w, 0.5+w, 0.5+w));//west
		boxes.add(new Cuboid6(0.5+w, 0.5-w, 0.5-w, 1, 0.5+w, 0.5+w));//east
		boxes.add(new Cuboid6(0.5-w,0.5-w,0.5-w,0.5+w,0.5+w,0.5+w));//base
	}

	@Override
	public List<Cuboid6> getOcclusionCubes() {
		return Arrays.asList(new Cuboid6[] { boxes.get(6) });
	}
	
	@Override
	public List<Cuboid6> getCollisionCubes() {
		ArrayList<Cuboid6> t2 = new ArrayList<Cuboid6>();
		t2.add(boxes.get(6));
		for(int i=0; i<6; i++){
			if((conMask & (1 << i)) > 0){
				t2.add(boxes.get(i));
			}
		}
		return t2;
	}

	private TileRenderHeatCable render;

	@Override
	public void renderPart(Vector3 pos) {
		if (render == null) render = new TileRenderHeatCable();
			render.render(this, pos);
	}
}
