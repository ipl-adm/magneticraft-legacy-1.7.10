package com.cout970.magneticraft.util.multiblock.types;

import com.cout970.magneticraft.ManagerBlocks;
import com.cout970.magneticraft.api.util.BlockPosition;
import com.cout970.magneticraft.util.multiblock.MB_Register;
import com.cout970.magneticraft.util.multiblock.Multiblock;
import com.cout970.magneticraft.util.multiblock.MutableComponent;

public class MultiblockRefinery extends Multiblock{

	@Override
	public int getID() {
		return MB_Register.ID_REFINERY;
	}

	@Override
	public void init() {
		MutableComponent r = new MutableComponent(ManagerBlocks.refinery);
		MutableComponent v = new MutableComponent(ManagerBlocks.refinery_gap);
		MutableComponent h = new MutableComponent(ManagerBlocks.refinery_tank);
		MutableComponent t = new MutableComponent(ManagerBlocks.tank_mg);
		MutableComponent ht = new MutableComponent(ManagerBlocks.heater);
		MutableComponent e = new MutableComponent(ManagerBlocks.multi_energy_low);
		MutableComponent b = new MutableComponent(ManagerBlocks.chasis);
		
		MutableComponent[][][] m = 
			{//     {{z2,z1,z0}x2,{z2,z1,z0}x1,{z2,z1,z0}x0}y0
				{ { b, b, b }, { b, b, b }, { b, b, b } },
				{ { b, r, b }, { e, ht,e }, { b, t, b } },
				{ { b, b, b }, { b, b, b }, { b, b, b } },
				{ { v, v, v }, { v, v, v }, { v, v, v } },
				{ { v, v, v }, { v, v, v }, { v, h, v } },
				{ { v, v, v }, { v, v, v }, { v, v, v } },
				{ { v, v, v }, { v, v, v }, { v, v, v } },
				{ { v, v, v }, { v, v, v }, { v, h, v } },
				{ { v, v, v }, { v, v, v }, { v, v, v } },
				{ { v, v, v }, { v, v, v }, { v, v, v } },
				{ { v, v, v }, { v, v, v }, { v, h, v } },
				{ { v, v, v }, { v, v, v }, { v, v, v } },
			};
		BlockPosition p = new BlockPosition(-1,-1,0);
		x = m.length;
		y = m[0].length;
		z = m[0][0].length;
		matrix = m;
		tran = p;
	}

}
