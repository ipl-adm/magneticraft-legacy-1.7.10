package com.cout970.magneticraft.tabs;

import com.cout970.magneticraft.ManagerItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabInformation extends CreativeTabs {

    public CreativeTabInformation(String s) {
        super(s);
    }

    @Override
    public Item getTabIconItem() {
        return ManagerItems.chip_ram;
    }

    @SideOnly(Side.CLIENT)
    public String getTranslatedTabLabel() {
        return "Magneticraft Information Age";
    }
}