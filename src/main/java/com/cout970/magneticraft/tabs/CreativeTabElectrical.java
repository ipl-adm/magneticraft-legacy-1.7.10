package com.cout970.magneticraft.tabs;

import com.cout970.magneticraft.ManagerItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabElectrical extends CreativeTabs {

    public CreativeTabElectrical(String s) {
        super(s);
    }

    @Override
    public Item getTabIconItem() {
        return ManagerItems.voltmeter;
    }

    @SideOnly(Side.CLIENT)
    public String getTranslatedTabLabel() {
        return "Magneticraft Electrical Age";
    }
}