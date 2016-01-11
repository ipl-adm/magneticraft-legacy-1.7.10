package com.cout970.magneticraft.tabs;

import com.cout970.magneticraft.ManagerBlocks;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabIndustrial extends CreativeTabs {

    public CreativeTabIndustrial(String s) {
        super(s);
    }

    @Override
    public Item getTabIconItem() {
        return Item.getItemFromBlock(ManagerBlocks.pole_tier1);
    }

    @SideOnly(Side.CLIENT)
    public String getTranslatedTabLabel() {
        return "Magneticraft Industrial Age";
    }
}