package com.cout970.magneticraft.util.multiblock.types;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import com.cout970.magneticraft.ManagerBlocks;
import com.cout970.magneticraft.api.util.BlockPosition;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.util.multiblock.MB_Register;
import com.cout970.magneticraft.util.multiblock.Multiblock;
import com.cout970.magneticraft.util.multiblock.MutableComponent;

public class MultiblockPolymerizer extends Multiblock{

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
		MutableComponent t = new MutableComponent(ManagerBlocks.tank_mg);
		MutableComponent ht = new MutableComponent(ManagerBlocks.heater);
		MutableComponent d = new MutableComponent(ManagerBlocks.polimerizer);

		MutableComponent[][][] m =
			{
				{ { b, b, b }, { b, b, b }, { b, b, b }, { b, b, b }, { b, b, b } },
				{ { b, d, b }, { i, a, i }, { b, a, b }, { e, ht,e }, { b, t, b } },
				{ { b, b, b }, { b, b, b }, { b, b, b }, { b, b, b }, { b, b, b } }
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
		return MB_Register.ID_POLIMERIZER;
	}

}
