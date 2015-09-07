package com.cout970.magneticraft.items;

import com.cout970.magneticraft.items.block.ManagerTooltip;
import com.cout970.magneticraft.tabs.CreativeTabsMg;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;
import java.util.Locale;

import org.lwjgl.input.Keyboard;

public class ItemBasic extends Item {

    public static final String base = "magneticraft:";

    public ItemBasic(String unlocalizedname, String texture) {
        super();
        setUnlocalizedName(unlocalizedname);
        setCreativeTab(CreativeTabsMg.MainTab);
        setTextureName(base + texture);
    }

    public ItemBasic(String unlocalizedname) {
        this(unlocalizedname, unlocalizedname);
    }

    public String getUnlocalizedName(ItemStack i) {
        return getUnlocalizedName();
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister IR) {
        this.itemIcon = IR.registerIcon(this.getIconString().toLowerCase(Locale.US));
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
