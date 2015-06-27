package com.cout970.magneticraft.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;

import com.cout970.magneticraft.tabs.CreativeTabsMg;

import codechicken.multipart.JItemMultiPart;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class ItemPartBase extends JItemMultiPart{
	
	public static final String Base = "magneticraft:";

	public ItemPartBase(String unlocalizedname){
		super();
		setUnlocalizedName(unlocalizedname);
		setCreativeTab(CreativeTabsMg.MainTab);
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
}
