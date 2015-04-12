package com.cout970.magneticraft.util.multiblock;

import net.minecraft.world.World;

import com.cout970.magneticraft.api.util.BlockPosition;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.util.Log;

public abstract class Multiblock {

	public int x,y,z;//legths
	public BlockPosition tran;
	public MutableComponent[][][] matrix;
	
	public abstract void init();
	
	public VecInt translate(World w, BlockPosition p, int x, int y, int z, Multiblock c, MgDirection e, int meta){//yzx
		if(meta < 6){
			if(e == MgDirection.SOUTH)return new VecInt(-z, x, -y).add(-tran.getX(),tran.getY(),tran.getZ());
			if(e == MgDirection.WEST)return new VecInt(y, x, -z).add(tran.getZ(),tran.getY(),-tran.getX());
			if(e == MgDirection.EAST)return new VecInt(-y, x, z).add(tran.getZ(),tran.getY(),tran.getX());
			return new VecInt(z, x, y).add(tran.getX(),tran.getY(),tran.getZ());
		}else{
			Log.debug(x+" "+y+" "+z+" "+e);
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
