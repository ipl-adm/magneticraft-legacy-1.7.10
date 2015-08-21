package com.cout970.magneticraft.api.tool;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Used on the hammer table to smash ores
 * 
 * @author cout970
 *
 */
public interface IHammer {

	/**
	 * called to damage the tool, return the item to leave in the player's hands
	 */
	public ItemStack tick(ItemStack hammer, World w, int x, int y, int z);
	
	/**
	 * Return true if the tool can work or not on the hammer table
	 */
	public boolean canHammer(ItemStack hammer, World w, int x, int y, int z);
	
	/**
	 * Return the number of hits needed to break the ore
	 */
	public int getMaxHits(ItemStack hammer, World w, int x, int y, int z);
}
