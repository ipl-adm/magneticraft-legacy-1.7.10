package com.cout970.magneticraft.items.block;

import java.util.List;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemBlockMg extends ItemBlock {

	public ItemBlockMg(Block b) {
		super(b);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack item, EntityPlayer player, List list, boolean flag) {
		super.addInformation(item, player, list, flag);
		List<String> tooltips = ManagerTooltip.getTootip(item, player, flag);
		
		if (!tooltips.isEmpty()) {
			if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
				list.add(EnumChatFormatting.DARK_GRAY + "<Press Shift for more info>");
				return;
			}
			list.addAll(tooltips);
		}
	}
}
