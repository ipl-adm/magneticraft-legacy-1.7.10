package com.cout970.magneticraft.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import codechicken.lib.vec.BlockCoord;
import codechicken.lib.vec.Vector3;
import codechicken.multipart.JItemMultiPart;
import codechicken.multipart.TMultiPart;

import com.cout970.magneticraft.parts.micro.PartCableHigh;
import com.cout970.magneticraft.util.CreativeTabMg;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemPartCableHigh extends JItemMultiPart{

	public static final String Base = "magneticraft:";

	public ItemPartCableHigh(String unlocalizedname){
		super();
		setUnlocalizedName(unlocalizedname);
		setCreativeTab(CreativeTabMg.MgTab);
		setTextureName(Base+"void");
	}

	public String getUnlocalizedName(ItemStack i){
		return getUnlocalizedName();
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister IR)
	{
		this.itemIcon = IR.registerIcon(this.getIconString());
	}

	@Override
	public TMultiPart newPart(ItemStack arg0, EntityPlayer arg1, World arg2,
			BlockCoord arg3, int arg4, Vector3 arg5) {
		return new PartCableHigh();
	}

}
