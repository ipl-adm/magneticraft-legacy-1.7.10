package com.cout970.magneticraft.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.cout970.magneticraft.util.CreativeTabMg;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBasic extends Item{
	
	public static final String Base = "magneticraft:";
	
	public ItemBasic(String unlocalizedname){
		super();
		setUnlocalizedName(unlocalizedname);
		setCreativeTab(CreativeTabMg.MgTab);
		setTextureName(Base+unlocalizedname.toLowerCase());
	}

	public String getUnlocalizedName(ItemStack i){
		return getUnlocalizedName();
	}
	
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister IR)
    {
        this.itemIcon = IR.registerIcon(this.getIconString().toLowerCase());
    }
}
