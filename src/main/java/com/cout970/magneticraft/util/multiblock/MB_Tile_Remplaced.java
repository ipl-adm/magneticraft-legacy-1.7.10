package com.cout970.magneticraft.util.multiblock;


import java.util.List;

import net.minecraft.item.ItemStack;

public interface MB_Tile_Remplaced {
	
	public void setDrops(List<ItemStack> drops);
	public List<ItemStack> getDrops();
	
}
