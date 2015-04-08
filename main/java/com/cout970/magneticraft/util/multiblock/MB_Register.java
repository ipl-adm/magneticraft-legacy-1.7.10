package com.cout970.magneticraft.util.multiblock;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import com.cout970.magneticraft.ManagerBlocks;
import com.cout970.magneticraft.api.util.BlockPosition;
import com.cout970.magneticraft.api.util.MgDirection;

public class MB_Register {

	public static Multiblock Polimerizer;
	public static Multiblock Crusher;
	public static Multiblock Refinery;
	public static Multiblock Grinder;
	
	
	public static void init(){
		MutableComponent a = new MutableComponent(Blocks.air){
			public boolean isCorrect(World w, BlockPosition p, int x, int y, int z, Multiblock c, MgDirection e) {
				return true;
			}
		};
		MutableComponent i = new MutableComponent(ManagerBlocks.multi_io);
		MutableComponent r = new MutableComponent(ManagerBlocks.refinery);
		MutableComponent v = new MutableComponent(ManagerBlocks.refinery_gap);
		MutableComponent h = new MutableComponent(ManagerBlocks.refinery_tank);
		MutableComponent t = new MutableComponent(ManagerBlocks.tank_mg);
		MutableComponent ht = new MutableComponent(ManagerBlocks.heater);
		
		MutableComponent e = new MutableComponent(ManagerBlocks.multi_energy);
		MutableComponent b = new MutableComponent(ManagerBlocks.chasis);
		MutableComponent c = new MutableComponent(ManagerBlocks.crusher);
		MutableComponent gc = new MutableComponent(ManagerBlocks.grinder);
		MutableComponent d = new MutableComponent(ManagerBlocks.polimerizer);
		
		MutableComponent[][][] A = 
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

		MutableComponent[][][] B = 
			{// 	{{z2,z1,z0}x2,{z2,z1,z0}x1,{z2,z1,z0}x0}y0
				{ { a, a, b, b, b }, { b, b, b, b, b }, { b, b, b, b, b }, { b, b, b, b, b } },
				{ { a, a, b, c, b }, { b, b, b, a, b }, { i, a, a, a, i }, { b, b, b, e, b } },
				{ { a, a, b, b, b }, { b, b, b, b, b }, { b, b, b, b, b }, { b, b, b, b, b } }
			};

		MutableComponent[][][] C = 
			{//     {{z2,z1,z0}x2,{z2,z1,z0}x1,{z2,z1,z0}x0}y0
				{ { b, b, b }, { b, b, b }, { b, b, b } },
				{ { b, gc, b}, { e, a, e }, { b, i, b } },
				{ { b, b, b }, { b, a, b }, { b, b, b } },
				{ { b, b, b }, { b, a, b }, { b, b, b } },
				{ { b, b, b }, { b, i, b }, { b, b, b } },
			};
		MutableComponent[][][] D =
			{
				{ { b, b, b }, { b, b, b }, { b, b, b }, { b, b, b }, { b, b, b } },
				{ { b, d, b }, { i, a, i }, { b, a, b }, { e, ht,e }, { b, t, b } },
				{ { b, b, b }, { b, b, b }, { b, b, b }, { b, b, b }, { b, b, b } }
			};
		
		Refinery = new Multiblock(A,new BlockPosition(-1,-1,0),0);
		Crusher = new Multiblock(B,new BlockPosition(-3,-1,0),1);
		Grinder = new Multiblock(C,new BlockPosition(-1,-1,0),2);
		Polimerizer = new Multiblock(D,new BlockPosition(-1,-1,0),3);
	}


	public static Multiblock getMBbyID(int i) {
		if(i == 0)return Refinery;
		if(i == 1)return Crusher;
		if(i == 2)return Grinder;
		if(i == 3)return Polimerizer;
		return null;
	}
}
