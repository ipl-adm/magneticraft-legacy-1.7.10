package com.cout970.magneticraft.util.multiblock.types;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import com.cout970.magneticraft.ManagerBlocks;
import com.cout970.magneticraft.api.util.BlockPosition;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.util.multiblock.MB_Register;
import com.cout970.magneticraft.util.multiblock.Multiblock;
import com.cout970.magneticraft.util.multiblock.MutableComponent;

public class MultiblockGrinder extends Multiblock{

	@Override
	public void init() {
		MutableComponent a = new MutableComponent(Blocks.air){
			public boolean isCorrect(World w, BlockPosition p, int x, int y, int z, Multiblock c, MgDirection e) {
				return true;
			}
		};
		MutableComponent i = new MutableComponent(ManagerBlocks.multi_io);
		MutableComponent e = new MutableComponent(ManagerBlocks.multi_energy_low);
		MutableComponent b = new MutableComponent(ManagerBlocks.chasis);
		MutableComponent gc = new MutableComponent(ManagerBlocks.grinder);

		MutableComponent[][][] m = 
			{//     {{z2,z1,z0}x2,{z2,z1,z0}x1,{z2,z1,z0}x0}y0
				{ { b, b, b }, { b, b, b }, { b, b, b } },
				{ { b, gc, b}, { e, a, e }, { b, i, b } },
				{ { b, b, b }, { b, a, b }, { b, b, b } },
				{ { b, b, b }, { b, a, b }, { b, b, b } },
				{ { b, b, b }, { b, i, b }, { b, b, b } },
			};

		BlockPosition p = new BlockPosition(-1,-1,0);
		x = m.length;
		y = m[0].length;
		z = m[0][0].length;
		matrix = m;
		tran = p;
	}

	@Override
	public int getID() {
		return MB_Register.ID_GRINDER;
	}

}
