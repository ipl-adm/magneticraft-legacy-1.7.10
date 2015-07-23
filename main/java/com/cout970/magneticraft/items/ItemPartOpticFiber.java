package com.cout970.magneticraft.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import codechicken.lib.vec.BlockCoord;
import codechicken.lib.vec.Vector3;
import codechicken.multipart.TMultiPart;

import com.cout970.magneticraft.parts.micro.PartOpticFiber;
import com.cout970.magneticraft.tabs.CreativeTabsMg;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemPartOpticFiber extends ItemPartBase{

	public ItemPartOpticFiber(String unlocalizedname){
		super(unlocalizedname);
		setCreativeTab(CreativeTabsMg.InformationAgeTab);
	}

	@Override
	public TMultiPart newPart(ItemStack arg0, EntityPlayer arg1, World arg2,
			BlockCoord arg3, int arg4, Vector3 arg5) {
		return new PartOpticFiber();
	}
	
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack item, EntityPlayer player, List list, boolean flag) {
		super.addInformation(item, player, list, flag);
		list.add(ItemBlockMg.format+"Still WIP");
	}
}
