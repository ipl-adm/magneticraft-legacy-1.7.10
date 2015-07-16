package com.cout970.magneticraft.util.multiblock.types;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import com.cout970.magneticraft.ManagerBlocks;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.block.BlockGrindingMill;
import com.cout970.magneticraft.util.multiblock.MB_Register;
import com.cout970.magneticraft.util.multiblock.Mg_Component;
import com.cout970.magneticraft.util.multiblock.Multiblock;
import com.cout970.magneticraft.util.multiblock.SimpleComponent;
import com.cout970.magneticraft.util.multiblock.RemplaceComponent;

public class MultiblockGrindingMill extends Multiblock{

	@Override
	public void init() {
		SimpleComponent a = new SimpleComponent(Blocks.air){
			public boolean isCorrect(World w, VecInt p, int x, int y, int z, Multiblock c, MgDirection e, int meta) {
				return true;
			}
		};
		Mg_Component i = new SimpleComponent(ManagerBlocks.multi_kinetic);
		Mg_Component b = new RemplaceComponent(Blocks.wooden_slab, ManagerBlocks.grinding_mill_gap);
		Mg_Component e = new RemplaceComponent(Blocks.stone_slab, ManagerBlocks.grinding_mill_gap);
		Mg_Component g = new SimpleComponent(ManagerBlocks.grinding_mill);
		Mg_Component v = new RemplaceComponent(Blocks.air, ManagerBlocks.grinding_mill_gap);

		Mg_Component[][][] m = 
			{//     {{z2,z1,z0}x2,{z2,z1,z0}x1,{z2,z1,z0}x0}y0
				{ { b, b, b }, { b, i, b }, { b, b, b } },
				{ { v, e, v }, { e, v, e }, { v, e, v } },
				{ { a, g, a }, { a, a, a }, { a, a, a } },
			};

		VecInt p = new VecInt(-1,-2,0);
		x = m.length;
		y = m[0].length;
		z = m[0][0].length;
		matrix = m;
		tran = p;
	}

	@Override
	public int getID() {
		return MB_Register.ID_GRINDING_MILL;
	}

}
