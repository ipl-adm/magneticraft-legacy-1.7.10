package com.cout970.magneticraft.items;

import java.util.List;

import com.cout970.magneticraft.tabs.CreativeTabsMg;

import codechicken.multipart.JItemMultiPart;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack item, EntityPlayer player, List list, boolean flag) {
		super.addInformation(item, player, list, flag);
		list.add(ItemBlockMg.format+"FMP compatible");
	}
}
