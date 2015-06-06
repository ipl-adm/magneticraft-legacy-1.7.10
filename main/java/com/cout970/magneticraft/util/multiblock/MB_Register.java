package com.cout970.magneticraft.util.multiblock;

import java.util.ArrayList;
import java.util.List;

import com.cout970.magneticraft.util.multiblock.types.MultiblockCrusher;
import com.cout970.magneticraft.util.multiblock.types.MultiblockGrinder;
import com.cout970.magneticraft.util.multiblock.types.MultiblockOilDistillery;
import com.cout970.magneticraft.util.multiblock.types.MultiblockPolymerizer;
import com.cout970.magneticraft.util.multiblock.types.MultiblockRefinery;
import com.cout970.magneticraft.util.multiblock.types.MultiblockStirlig;
import com.cout970.magneticraft.util.multiblock.types.MultiblockGrindingMill;
import com.cout970.magneticraft.util.multiblock.types.MultiblockTurbine;

public class MB_Register {

	private static List<Multiblock> mb = new ArrayList<Multiblock>();
	public static final int ID_REFINERY = 0;
	public static final int ID_CRUSHER = 1;
	public static final int ID_GRINDER = 2;
	public static final int ID_POLIMERIZER = 3;
	public static final int ID_TURBINE = 4;
	public static final int ID_STIRLING = 5;
	public static final int ID_OIL_DISTILLERY = 6;
	public static final int ID_GRINDING_MILL = 7;
	
	
	public static void init(){
		mb.add(new MultiblockRefinery());
		mb.add(new MultiblockCrusher());
		mb.add(new MultiblockGrinder());
		mb.add(new MultiblockPolymerizer());
		mb.add(new MultiblockTurbine());
		mb.add(new MultiblockStirlig());
		mb.add(new MultiblockOilDistillery());
		mb.add(new MultiblockGrindingMill());
		
		for(Multiblock b : mb)
			b.init();
	}


	public static Multiblock getMBbyID(int i) {
		for(Multiblock b : mb)
			if(b.getID() == i)return b;
		return null;
	}
}
