package com.cout970.magneticraft.util.multiblock;

import com.cout970.magneticraft.api.util.BlockPosition;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;

public class Multiblock {

	public int x,y,z;//legths
	public BlockPosition tran;
	public MutableComponent[][][] matrix;
	public int id;
	
	public Multiblock(MutableComponent[][][] m,BlockPosition b,int id){
		//yzx
		x = m.length;
		y = m[0].length;
		z = m[0][0].length;
		matrix = m;
		tran = b;
		this.id = id;
	}
	
	public VecInt translate(int x,int y,int z,MgDirection e){//yzx
		if(e == MgDirection.SOUTH)return new VecInt(-z, x, -y).add(-tran.getX(),tran.getY(),tran.getZ());
		if(e == MgDirection.WEST)return new VecInt(y, x, -z).add(tran.getZ(),tran.getY(),-tran.getX());
		if(e == MgDirection.EAST)return new VecInt(-y, x, z).add(tran.getZ(),tran.getY(),tran.getX());
		return new VecInt(z, x, y).add(tran.getX(),tran.getY(),tran.getZ());
	}

	public int[] getDimensions(MgDirection e) {
		return new int[]{x,y,z};
	}

	public int getID() {
		return id;
	}
}
