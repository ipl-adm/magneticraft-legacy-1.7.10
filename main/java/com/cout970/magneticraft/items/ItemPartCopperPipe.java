package com.cout970.magneticraft.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import codechicken.lib.vec.BlockCoord;
import codechicken.lib.vec.Vector3;
import codechicken.multipart.JItemMultiPart;
import codechicken.multipart.TMultiPart;

import com.cout970.magneticraft.parts.micro.PartCopperPipe;
import com.cout970.magneticraft.tabs.CreativeTabsMg;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemPartCopperPipe extends ItemPartBase{
		
	public ItemPartCopperPipe(String unlocalizedname){
		super(unlocalizedname);
		setCreativeTab(CreativeTabsMg.SteamAgeTab);
	}
	
	@Override
	public TMultiPart newPart(ItemStack arg0, EntityPlayer arg1, World arg2,
			BlockCoord arg3, int arg4, Vector3 arg5) {
		return new PartCopperPipe();
	}
}
