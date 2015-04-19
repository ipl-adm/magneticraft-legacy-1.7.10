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
import codechicken.multipart.TMultiPart;
import codechicken.multipart.TileMultipart;

import com.cout970.magneticraft.ManagerItems;
import com.cout970.magneticraft.api.electricity.CableCompound;
import com.cout970.magneticraft.api.electricity.Conductor;
import com.cout970.magneticraft.api.electricity.ConnectionClass;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.ICompatibilityInterface;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.IElectricMultiPart;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.client.tilerender.TileRenderCableLow;

public class PartCableLow extends ElectricPart implements ISidedHollowConnect,IElectricMultiPart{

	public byte connections;
	public static List<Cuboid6> boxes = new ArrayList<Cuboid6>();

	static{
		double w = 2/16d;

		boxes.add(new Cuboid6(0.5-w, 0, 0.5-w, 0.5+w, 0.5-w, 0.5+w));//up
		boxes.add(new Cuboid6(0.5-w, 0.5+w, 0.5-w, 0.5+w, 1, 0.5+w));//down
		boxes.add(new Cuboid6(0.5-w, 0.5-w, 0, 0.5+w, 0.5+w, 0.5-w));//north
		boxes.add(new Cuboid6(0.5-w, 0.5-w, 0.5+w, 0.5+w, 0.5+w, 1));//south
		boxes.add(new Cuboid6(0, 0.5-w, 0.5-w, 0.5-w, 0.5+w, 0.5+w));//west
		boxes.add(new Cuboid6(0.5+w, 0.5-w, 0.5-w, 1, 0.5+w, 0.5+w));//east
		boxes.add(new Cuboid6(0.5-w,0.5-w,0.5-w,0.5+w,0.5+w,0.5+w));//base
	}

	public PartCableLow() {
		super(ManagerItems.cablelow);
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
		for(byte i=0; i<6; i++){
			if((connections & (1 << i)) > 0){
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
		cond = new Conductor(getTile(), ElectricConstants.RESISTANCE_COPPER_2X2){
			
			@Override
			public VecInt[] getValidConnections() {
				VecInt[] FORGE_DIRECTIONS = {
						VecInt.getConnexion(MgDirection.DOWN),
						VecInt.getConnexion(MgDirection.UP),
						VecInt.getConnexion(MgDirection.NORTH),
						VecInt.getConnexion(MgDirection.SOUTH),
						VecInt.getConnexion(MgDirection.WEST),
						VecInt.getConnexion(MgDirection.EAST),
						VecInt.NULL_VECTOR};
				return FORGE_DIRECTIONS;
			}
			
			public boolean isAbleToConnect(IElectricConductor c, VecInt d){
				if(d.equals(VecInt.NULL_VECTOR))return true;
				if(d.toMgDirection() == null)return false;
				if(c.getConnectionClass(d.getOpposite()) == ConnectionClass.FULL_BLOCK || c.getConnectionClass(d.getOpposite()) == ConnectionClass.CABLE_LOW){
					if(((TileMultipart)getTile()).canAddPart(new NormallyOccludedPart(boxes.get(d.toMgDirection().ordinal())))){
						return true;
					}
				}
				return false;
			}
			@Override
			public ConnectionClass getConnectionClass(VecInt v) {
				return ConnectionClass.CABLE_LOW;
			}
		};
	}

	public void updateConnections() {
		connections = 0;
		for(MgDirection d : MgDirection.values()){
			TileEntity t = MgUtils.getTileEntity(getTile(), d);
			CableCompound c = MgUtils.getConductor(t, VecInt.getConnexion(d).getOpposite(), getTier());
			if(c != null && cond != null){
				for(IElectricConductor e : c.list()){
					if(e.isAbleToConnect(cond, VecInt.getConnexion(d.opposite())) && cond.isAbleToConnect(e, VecInt.getConnexion(d))){
						connections = (byte) (connections | (1 << d.ordinal()));
					}
				}
			}
			ICompatibilityInterface inter = MgUtils.getInterface(t, d.getVecInt().getOpposite(), getTier());
			if(inter != null){
				if(((TileMultipart)getTile()).canAddPart(new NormallyOccludedPart(boxes.get(d.ordinal()))))
					connections = (byte) (connections | (1 << d.ordinal()));
			}
		}
		for(TMultiPart t:tile().jPartList()){
			if(t instanceof IElectricMultiPart && ((IElectricMultiPart) t).getCond(getTier()) != null){
				if(t instanceof PartWireCopper){
					connections = (byte) (connections | (1 << ((PartWireCopper) t).getDirection().ordinal()));
				}
			}
		}
	}

	
	//Render
	
	private TileRenderCableLow render;

	@Override
	public int getTier() {
		return 0;
	}

	@Override
	public void renderPart(Vector3 pos) {
		if (render == null) render = new TileRenderCableLow();
		render.render(this, pos);
	}

}
