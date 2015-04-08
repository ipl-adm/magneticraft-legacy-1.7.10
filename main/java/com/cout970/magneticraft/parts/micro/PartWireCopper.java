package com.cout970.magneticraft.parts.micro;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Vector3;
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
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.client.tilerender.TileRenderWireCopper;

public abstract class PartWireCopper extends ElectricPart{
	
	public static List<Cuboid6> Down_Boxes = new ArrayList<Cuboid6>();
	public static List<Cuboid6> Up_Boxes = new ArrayList<Cuboid6>();
	public static List<Cuboid6> North_Boxes = new ArrayList<Cuboid6>();
	public static List<Cuboid6> South_Boxes = new ArrayList<Cuboid6>();
	public static List<Cuboid6> West_Boxes = new ArrayList<Cuboid6>();
	public static List<Cuboid6> East_Boxes = new ArrayList<Cuboid6>();
	
	static{
		float width = 2/16f;
		float h = 3/16f;
		Down_Boxes.clear();
		Up_Boxes.clear();
		North_Boxes.clear();
		South_Boxes.clear();
		West_Boxes.clear();
		East_Boxes.clear();
		
		Down_Boxes.add(new Cuboid6(0.5f-width, 0, 0.5f-width, 		0.5f+width, h, 0.5f+width));//base
		Down_Boxes.add(new Cuboid6(0.5f-width, 0, 0, 				0.5f+width, h, 0.5f-width));//-z
		Down_Boxes.add(new Cuboid6(0.5f-width, 0, 0.5f+width, 		0.5f+width, h, 1));//+z
		Down_Boxes.add(new Cuboid6(0, 		   0, 0.5f-width, 		0.5f-width, h, 0.5f+width));//-x
		Down_Boxes.add(new Cuboid6(0.5f+width, 0, 0.5f-width, 		1, 			h, 0.5f+width));//+x
		
		Up_Boxes.add(new Cuboid6(0.5f-width, 1-h, 0.5f-width, 0.5f+width, 1, 0.5f+width));//base
		Up_Boxes.add(new Cuboid6(0.5f-width, 1-h, 0, 		  0.5f+width, 1, 0.5f-width));//-z
		Up_Boxes.add(new Cuboid6(0.5f-width, 1-h, 0.5f+width, 0.5f+width, 1, 1));//+z
		Up_Boxes.add(new Cuboid6(0, 		 1-h, 0.5f-width, 0.5f-width, 1, 0.5f+width));//-x
		Up_Boxes.add(new Cuboid6(0.5f+width, 1-h, 0.5f-width, 1, 		  1, 0.5f+width));//+x
		
		North_Boxes.add(new Cuboid6(0.5f-width, 0.5f-width, 0, 0.5f+width, 0.5f+width, h));//base
		North_Boxes.add(new Cuboid6(0.5f-width, 0, 			0, 0.5f+width, 0.5f-width, h));//-z
		North_Boxes.add(new Cuboid6(0.5f-width, 0.5f+width, 0, 0.5f+width, 1, h));//+z
		North_Boxes.add(new Cuboid6(0, 			0.5f-width, 0, 0.5f-width, 0.5f+width, h));//-x
		North_Boxes.add(new Cuboid6(0.5f+width, 0.5f-width, 0, 1, 			0.5f+width, h));//+x
		
		South_Boxes.add(new Cuboid6(0.5f-width, 0.5f-width, 1-h, 0.5f+width, 0.5f+width, 1));//base
		South_Boxes.add(new Cuboid6(0.5f-width, 0, 			1-h, 0.5f+width, 0.5f-width, 1));//-z
		South_Boxes.add(new Cuboid6(0.5f-width, 0.5f+width, 1-h, 0.5f+width, 1, 1));//+z
		South_Boxes.add(new Cuboid6(0, 			0.5f-width, 1-h, 0.5f-width, 0.5f+width, 1));//-x
		South_Boxes.add(new Cuboid6(0.5f+width, 0.5f-width, 1-h, 1,			 0.5f+width, 1));//+x
		
		West_Boxes.add(new Cuboid6(0, 0.5f-width, 0.5f-width, h, 0.5f+width, 0.5f+width));//base
		West_Boxes.add(new Cuboid6(0, 0, 		  0.5f-width, h, 0.5f-width, 0.5f+width));//-z
		West_Boxes.add(new Cuboid6(0, 0.5f+width, 0.5f-width, h, 1, 		 0.5f+width));//+z
		West_Boxes.add(new Cuboid6(0, 0.5f-width, 0, 		  h, 0.5f+width, 0.5f-width));//-x
		West_Boxes.add(new Cuboid6(0, 0.5f-width, 0.5f+width, h, 0.5f+width, 1));//+x
		
		East_Boxes.add(new Cuboid6(1-h, 0.5f-width, 0.5f-width, 1, 0.5f+width, 0.5f+width));//base
		East_Boxes.add(new Cuboid6(1-h, 0, 		 	0.5f-width, 1, 0.5f-width, 0.5f+width));//-z
		East_Boxes.add(new Cuboid6(1-h, 0.5f+width, 0.5f-width, 1, 1, 		 0.5f+width));//+z
		East_Boxes.add(new Cuboid6(1-h, 0.5f-width, 0, 		    1, 0.5f+width, 0.5f-width));//-x
		East_Boxes.add(new Cuboid6(1-h, 0.5f-width, 0.5f+width, 1, 0.5f+width, 1));//+x
	}

	private TileRenderWireCopper render;
	private VecInt[] validCon;
	public int Conn = 0;

	public abstract MgDirection getDirection();
	public abstract List<Cuboid6> getBoxes();

	public PartWireCopper() {
		super(ManagerItems.wire_copper);
	}

	public void create(){
		cond = new Conductor(getTile(), 0, ElectricConstants.RESISTANCE_BASE){
			@Override
			public VecInt[] getValidConnections() {
				return PartWireCopper.this.getValidConnexions(getDirection());
			}

			@Override
			public boolean isAbleToConnect(IElectricConductor c, VecInt d){
				if(d.equals(VecInt.NULL_VECTOR))return true;
				if(d.equals(getDirection().getVecInt()))return true;
				if(d.equals(getDirection().opposite().getVecInt()))return false;
				MgDirection dir = d.toMgDirection();
				if(dir != null){
					//FORGE_DIRECTIONS
					return ((TileMultipart)getTile()).canAddPart(new NormallyOccludedPart(getBoxes().get(getBoxBySide(dir))))
							&& (c.getConnectionClass(d.getOpposite()) == this.getConnectionClass(d) || c.getConnectionClass(d.getOpposite()) == ConnectionClass.FULL_BLOCK);
				}else{
					dir = (d.copy().add(getDirection().getVecInt().getOpposite())).toMgDirection();
				}
				if(dir != null){
					//EXTENDED_DIRECTIONS
					VecInt g = dir.getVecInt().add(X(),Y(),Z());
					Block b = W().getBlock(g.getX(), g.getY(), g.getZ());
					return ((TileMultipart)getTile()).canAddPart(new NormallyOccludedPart(getBoxes().get(getBoxBySide(dir))))
							&& isTranspasable(b) 
							&& (c.getConnectionClass(d.getOpposite()) == ConnectionClass.FULL_BLOCK 
							|| ConnectionClass.isSlabCompatible(c.getConnectionClass(d.getOpposite()),getConnectionClass(d)));
				}
				return false;
			}

			@Override
			public ConnectionClass getConnectionClass(VecInt v) {
				return ConnectionType();
			}
		};
	}
	
	public abstract int getBoxBySide(MgDirection dir);
	
	public ConnectionClass ConnectionType(){
		if(getDirection() == MgDirection.DOWN)return ConnectionClass.SLAB_BOTTOM;
		if(getDirection() == MgDirection.UP)return ConnectionClass.SLAB_TOP;
		if(getDirection() == MgDirection.NORTH)return ConnectionClass.SLAB_NORTH;
		if(getDirection() == MgDirection.SOUTH)return ConnectionClass.SLAB_SOUTH;
		if(getDirection() == MgDirection.WEST)return ConnectionClass.SLAB_WEST;
		if(getDirection() == MgDirection.EAST)return ConnectionClass.SLAB_EAST;
		return ConnectionClass.SLAB_BOTTOM;
	}

	public VecInt[] getValidConnexions(MgDirection dir) {
		if(validCon == null){
			validCon = new VecInt[10];
			validCon[0] = VecInt.NULL_VECTOR;
			validCon[1] = dir.getVecInt();
			byte i = 2;
			
			for(MgDirection d : MgDirection.values()){
				if(d != dir && d != dir.opposite()){
					validCon[i] = d.getVecInt();
					i++;
				}
			}
			for(MgDirection d : MgDirection.values()){
				if(d != dir && d != dir.opposite()){
					validCon[i] = d.getVecInt().copy().add(dir.getVecInt());
					i++;
				}
			}
		}
		return validCon;
	}	
	
	@Override
	public int getTier(){return 0;}
	
	@Override
	public void updateConnections() {
		Conn = 0;
		for(MgDirection f : MgDirection.values()){
			TileEntity target = MgUtils.getTileEntity(tile(), f.getVecInt());
			CableCompound c = MgUtils.getConductor(target, f.getVecInt().getOpposite(), getTier());
			ICompatibilityInterface inter = MgUtils.getInterface(target, f.getVecInt().getOpposite(), getTier());
			if(c != null){
				for(IElectricConductor e : c.list()){
					if(cond.isAbleToConnect(e, f.getVecInt()) && e.isAbleToConnect(cond, f.getVecInt().getOpposite())){
						Conn |= 1 << f.ordinal();
					}
				}
			}
			for(TMultiPart t:tile().jPartList()){
				if(t instanceof PartWireCopper && t != this){
					if(((PartWireCopper) t).getDirection() == f){
						Conn |= 1 << f.ordinal();
					}
				}
			}
			if(inter != null){
				Conn |= 1 << f.ordinal();
			}
		}
		for(MgDirection d : MgDirection.values()){
			VecInt f = d.getVecInt().add(getDirection().getVecInt());
			TileEntity target = MgUtils.getTileEntity(tile(), f);
			CableCompound c = MgUtils.getConductor(target, f.getOpposite(), getTier());
			ICompatibilityInterface inter = MgUtils.getInterface(target, f.getOpposite(), getTier());
			if(c != null || inter != null){
				VecInt g = d.getVecInt().copy().add(X(), Y(), Z());
				Block b = W().getBlock(g.getX(), g.getY(), g.getZ());
				if(isTranspasable(b)){
					if(c != null){
						for(IElectricConductor e : c.list()){
							if(cond.isAbleToConnect(e, f) && e.isAbleToConnect(cond, f.getOpposite())){
								Conn |= 1 << d.ordinal();
								Conn |= 1 << d.ordinal()+6;
							}
						}
					}
					if(inter != null){
						Conn |= 1 << d.ordinal();
						Conn |= 1 << d.ordinal()+6;
					}
				}
			}
		}
	}
	
	@Override
	public void onNeighborChanged() {
		super.onNeighborChanged();
		MgDirection d = getDirection();
		if(!world().isRemote && !world().isSideSolid(X()+d.getOffsetX(), Y()+d.getOffsetY(), Z()+d.getOffsetZ(), d.getForgeDir(), false)){
			tile().dropItems(this.getDrops());
			tile().remPart(this);
		}
	}

	private boolean isTranspasable(Block b) {
		if(b == Blocks.air)return true;
		return false;
	}

	@Override
	public String getType() {
		return item.getUnlocalizedName()+"_"+getDirection().name();
	}

	@Override
	public List<Cuboid6> getOcclusionCubes() {
		return Arrays.asList(getBoxes().get(0));
	}

	@Override
	public List<Cuboid6> getCollisionCubes() {
		List<Cuboid6> l = new ArrayList<Cuboid6>();
		l.add(getBoxes().get(0));
		for(MgDirection d : MgDirection.values()){
			if(getBoxBySide(d) != 0 && (Conn & (1 << d.ordinal())) > 0){
				l.add(getBoxes().get(getBoxBySide(d)));
			}
		}
		return l;
	}

	@Override
	public void renderPart(Vector3 pos) {
		if (render == null) render = new TileRenderWireCopper();
		render.render(this, pos);
	}

	public boolean isUnconnected() {
		return (Conn & getConMask()) == 0;
	}

	public int getConMask() {
		if(getDirection() == MgDirection.DOWN)	return 4095 ^ 2;
		if(getDirection() == MgDirection.UP)	return 4095 ^ 1;
		if(getDirection() == MgDirection.NORTH)	return 4095 ^ 8;
		if(getDirection() == MgDirection.SOUTH)	return 4095 ^ 4;
		if(getDirection() == MgDirection.WEST)	return 4095 ^ 32;
		if(getDirection() == MgDirection.EAST)	return 4095 ^ 16;
		return 4095;
	}
}
