package com.cout970.magneticraft.util.multiblock;

import net.minecraft.world.World;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;

public abstract class Multiblock {

	public int x,y,z;//legths
	public VecInt tran;
	public Mg_Component[][][] matrix;
	
	public abstract void init();
	
	public VecInt translate(World w, VecInt p, int x, int y, int z, Multiblock c, MgDirection e, int meta){//yzx
		if(meta < 6){
			if(e == MgDirection.SOUTH)return new VecInt(-z, x, -y).add(-tran.getX(),tran.getY(),tran.getZ());
			if(e == MgDirection.WEST)return new VecInt(y, x, -z).add(tran.getZ(),tran.getY(),-tran.getX());
			if(e == MgDirection.EAST)return new VecInt(-y, x, z).add(tran.getZ(),tran.getY(),tran.getX());
			return new VecInt(z, x, y).add(tran.getX(),tran.getY(),tran.getZ());
		}else{
			if(e == MgDirection.NORTH)return new VecInt(-z, x, y).add(-tran.getX(),tran.getY(),tran.getZ());
			if(e == MgDirection.EAST)return new VecInt(-y, x, -z).add(tran.getZ(),tran.getY(),-tran.getX());
			if(e == MgDirection.WEST)return new VecInt(y, x, z).add(tran.getZ(),tran.getY(),tran.getX());
			return new VecInt(z, x, -y).add(tran.getX(),tran.getY(),tran.getZ());
		}
	}

	public int[] getDimensions(MgDirection e) {
		return new int[]{x,y,z};
	}

	public abstract int getID();
}
