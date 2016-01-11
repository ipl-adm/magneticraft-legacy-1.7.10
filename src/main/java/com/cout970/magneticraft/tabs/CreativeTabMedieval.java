package com.cout970.magneticraft.tabs;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class CreativeTabMedieval extends CreativeTabs {

    public CreativeTabMedieval(String s) {
        super(s);
    }

    @Override
    public Item getTabIconItem() {
        return Items.stick;
    }

    @SideOnly(Side.CLIENT)
    public String getTranslatedTabLabel() {
        return "Magneticraft Medieval Age";
    }
}