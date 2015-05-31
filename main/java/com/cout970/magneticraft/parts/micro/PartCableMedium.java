package com.cout970.magneticraft.parts.micro;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.tileentity.TileEntity;
import codechicken.lib.raytracer.IndexedCuboid6;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Vector3;
import codechicken.microblock.ISidedHollowConnect;
import codechicken.multipart.NormallyOccludedPart;
import codechicken.multipart.TileMultipart;

import com.cout970.magneticraft.ManagerItems;
import com.cout970.magneticraft.api.electricity.CableCompound;
import com.cout970.magneticraft.api.electricity.ConnectionClass;
import com.cout970.magneticraft.api.electricity.ElectricConductor;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.IElectricMultiPart;
import com.cout970.magneticraft.api.electricity.IEnergyInterface;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.client.tilerender.TileRenderCableMedium;

public class PartCableMedium extends ElectricPart implements ISidedHollowConnect,IElectricMultiPart{

	public boolean[] connections = new boolean[6];
	public static List<Cuboid6> boxes = new ArrayList<Cuboid6>();

	static{
		double w = 3/16d;

		boxes.add(new Cuboid6(0.5-w, 0, 0.5-w, 0.5+w, 0.5-w, 0.5+w));//up
		boxes.add(new Cuboid6(0.5-w, 0.5+w, 0.5-w, 0.5+w, 1, 0.5+w));//down
		boxes.add(new Cuboid6(0.5-w, 0.5-w, 0, 0.5+w, 0.5+w, 0.5-w));//north
		boxes.add(new Cuboid6(0.5-w, 0.5-w, 0.5+w, 0.5+w, 0.5+w, 1));//south
		boxes.add(new Cuboid6(0, 0.5-w, 0.5-w, 0.5-w, 0.5+w, 0.5+w));//west
		boxes.add(new Cuboid6(0.5+w, 0.5-w, 0.5-w, 1, 0.5+w, 0.5+w));//east
		boxes.add(new Cuboid6(0.5-w,0.5-w,0.5-w,0.5+w,0.5+w,0.5+w));//base
	}
	
	public PartCableMedium() {
		super(ManagerItems.cablemedium);
	}

	@Override
	public List<Cuboid6> getOcclusionCubes() {
		return Arrays.asList(new Cuboid6[] { boxes.get(6) });
	}
	
	@Override
	public Iterable<IndexedCuboid6> getSubParts() {
		Iterable<Cuboid6> boxList = getCollisionBoxes();
		LinkedList<IndexedCuboid6> partList = new LinkedList<IndexedCuboid6>();
		for (Cuboid6 c : boxList)
			partList.add(new IndexedCuboid6(0, c));
		((ArrayList<Cuboid6>) boxList).clear();
		return partList;
	}
	
	@Override
	public List<Cuboid6> getCollisionCubes() {
		ArrayList<Cuboid6> t2 = new ArrayList<Cuboid6>();
		t2.add(boxes.get(6));
		for(int i=0; i<6; i++){
			if(connections[i]){
				t2.add(boxes.get(i));
			}
		}
		return t2;
	}

	@Override
	public int getHollowSize(int arg0) {
		return 4;
	}

	@Override
	public void update() {
		super.update();
		if(tile() == null)return;
		if(toUpdate){
			if(cond == null)
				create();
			toUpdate = false;
			updateConnections();
		}
		if(tempNBT != null){
			cond.load(tempNBT);
			tempNBT = null;
		}
		cond.recache();
		cond.iterate();	
	}
	
	public void create(){
		cond = new ElectricConductor(getTile(), getTier(), ElectricConstants.RESISTANCE_COPPER_2X2){
			public boolean isAbleToConnect(IElectricConductor c, VecInt d){
				if(c.getConnectionClass(d.getOpposite()) == ConnectionClass.FULL_BLOCK || c.getConnectionClass(d.getOpposite()) == ConnectionClass.Cable_MEDIUM){
					if(d.toMgDirection() == null)return false;
					if(((TileMultipart)getTile()).canAddPart(new NormallyOccludedPart(boxes.get(d.toMgDirection().ordinal()))))
						return true;
				}
				return false;
			}
			@Override
			public ConnectionClass getConnectionClass(VecInt v) {
				return ConnectionClass.Cable_MEDIUM;
			}
		};
	}

	public void updateConnections() {
		Arrays.fill(connections, false);
		for(MgDirection d : MgDirection.values()){
			TileEntity t = MgUtils.getTileEntity(getTile(), d);
			CableCompound c = MgUtils.getElectricCond(t, VecInt.fromDirection(d).getOpposite(), getTier());
			if(c != null){
				for(IElectricConductor e : c.list()){
					if(e.isAbleToConnect(cond, VecInt.fromDirection(d.opposite())) && cond.isAbleToConnect(e, VecInt.fromDirection(d))){
						connections[d.ordinal()] = true;
					}
				}
			}
			IEnergyInterface inter = MgUtils.getInterface(t, d.getVecInt().getOpposite(), getTier());
			if(inter != null){
				if(((TileMultipart)getTile()).canAddPart(new NormallyOccludedPart(boxes.get(d.ordinal()))))
				connections[d.ordinal()] = true;
			}
		}
	}

	@Override
	public int getTier() {
		return 2;
	}
	
	//Render
	
	private TileRenderCableMedium render;

	@Override
	public void renderPart(Vector3 pos) {
		if (render == null) render = new TileRenderCableMedium();
			render.render(this, pos);
	}
}
