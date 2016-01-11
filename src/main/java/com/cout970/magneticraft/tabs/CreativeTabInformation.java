package com.cout970.magneticraft.tabs;

import com.cout970.magneticraft.ManagerItems;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
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