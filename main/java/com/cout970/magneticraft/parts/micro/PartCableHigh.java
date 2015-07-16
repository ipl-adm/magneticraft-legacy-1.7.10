package com.cout970.magneticraft.parts.micro;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.tileentity.TileEntity;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Vector3;
import codechicken.microblock.ISidedHollowConnect;
import codechicken.multipart.NormallyOccludedPart;
import codechicken.multipart.TileMultipart;

import com.cout970.magneticraft.ManagerItems;
import com.cout970.magneticraft.api.electricity.CompoundElectricCables;
import com.cout970.magneticraft.api.electricity.ConnectionClass;
import com.cout970.magneticraft.api.electricity.ElectricConductor;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.IEnergyInterface;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.client.tilerender.TileRenderCableHigh;

public class PartCableHigh extends PartElectric implements ISidedHollowConnect{

	public boolean[] connections = new boolean[6];
	public static List<Cuboid6> boxes = new ArrayList<Cuboid6>();
	
	static{
		double w = 4/16d;
		boxes.add(new Cuboid6(0.5-w, 0, 0.5-w, 0.5+w, 0.5-w, 0.5+w));//down
		boxes.add(new Cuboid6(0.5-w, 0.5+w, 0.5-w, 0.5+w, 1, 0.5+w));//up
		boxes.add(new Cuboid6(0.5-w, 0.5-w, 0, 0.5+w, 0.5+w, 0.5-w));//north
		boxes.add(new Cuboid6(0.5-w, 0.5-w, 0.5+w, 0.5+w, 0.5+w, 1));//south
		boxes.add(new Cuboid6(0, 0.5-w, 0.5-w, 0.5-w, 0.5+w, 0.5+w));//west
		boxes.add(new Cuboid6(0.5+w, 0.5-w, 0.5-w, 1, 0.5+w, 0.5+w));//east
		boxes.add(new Cuboid6(0.5-w,0.5-w,0.5-w,0.5+w,0.5+w,0.5+w));//base
	}
	
	public PartCableHigh() {
		super(ManagerItems.cablehigh);
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
	
	public void create(){
		cond = new ElectricConductor(getTile(), getTier(), ElectricConstants.RESISTANCE_COPPER_HIG){
			public boolean isAbleToConnect(IElectricConductor c, VecInt d){
				if(c.getConnectionClass(d.getOpposite()) == ConnectionClass.FULL_BLOCK || c.getConnectionClass(d.getOpposite()) == ConnectionClass.CABLE_HIGH){
					if(d.toMgDirection() == null)return false;
					if(((TileMultipart)getTile()).canAddPart(new NormallyOccludedPart(boxes.get(d.toMgDirection().ordinal()))))
						return true;
				}
				return false;
			}
			@Override
			public ConnectionClass getConnectionClass(VecInt v) {
				return ConnectionClass.CABLE_HIGH;
			}
		};
	}

	public void updateConnections() {
		Arrays.fill(connections, false);
		for(MgDirection d : MgDirection.values()){
			TileEntity t = MgUtils.getTileEntity(getTile(), d);
			CompoundElectricCables c = MgUtils.getElectricCond(t, VecInt.fromDirection(d).getOpposite(), getTier());
			if(c != null){
				for(IElectricConductor e : c.list()){
					if(e.isAbleToConnect(cond, VecInt.fromDirection(d.opposite())) && cond.isAbleToConnect(e, VecInt.fromDirection(d))){
						connections[d.ordinal()] = true;
					}
				}
			}
			IEnergyInterface inter = MgUtils.getInterface(t, d.toVecInt().getOpposite(), getTier());
			if(inter != null && inter.canConnect(d.toVecInt())){
				if(((TileMultipart)getTile()).canAddPart(new NormallyOccludedPart(boxes.get(d.ordinal()))))
				connections[d.ordinal()] = true;
			}
		}
	}
	
	//Render
	
	private TileRenderCableHigh render;

	@Override
	public void renderPart(Vector3 pos) {
		if (render == null) render = new TileRenderCableHigh();
			render.render(this, pos);
	}
	
	@Override
	public int getTier() {
		return 4;
	}
}
