package com.cout970.magneticraft.compact.minetweaker;

import minetweaker.MineTweakerAPI;
import minetweaker.api.block.IBlock;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class MgMinetweaker {

	public static void init(){
		MineTweakerAPI.registerClass(Crusher.class);
		MineTweakerAPI.registerClass(Grinder.class);
		MineTweakerAPI.registerClass(Thermopile.class);
		MineTweakerAPI.registerClass(BiomassBurner.class);
	}
	
	public static ItemStack toStack(IItemStack iStack){
		return MineTweakerMC.getItemStack(iStack);
	}

	public static Block getBlock(IBlock block) {
		return MineTweakerMC.getBlock(block);
	}
}
