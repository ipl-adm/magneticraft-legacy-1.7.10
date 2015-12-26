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
import org.lwjgl.input.Keyboard;

import java.util.List;

public abstract class ItemPartBase extends Item
//JItemMultiPart
{

    public static final String Base = "magneticraft:";

    public ItemPartBase(String unlocalizedname) {
        super();
        setUnlocalizedName(unlocalizedname);
        setCreativeTab(CreativeTabsMg.MainTab);
        setTextureName(Base + "void");
    }

    public String getUnlocalizedName(ItemStack i) {
        return getUnlocalizedName();
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister IR) {
        this.itemIcon = IR.registerIcon(this.getIconString());
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack item, EntityPlayer player, List list, boolean flag) {
        super.addInformation(item, player, list, flag);
        list.add(ManagerTooltip.format + "FMP Not Found");
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
