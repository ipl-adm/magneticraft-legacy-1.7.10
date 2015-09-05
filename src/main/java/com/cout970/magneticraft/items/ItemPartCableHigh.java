package com.cout970.magneticraft.items;

import com.cout970.magneticraft.parts.electric.PartCableHigh;
import com.cout970.magneticraft.tabs.CreativeTabsMg;

import codechicken.lib.vec.BlockCoord;
import codechicken.lib.vec.Vector3;
import codechicken.multipart.TMultiPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemPartCableHigh extends ItemPartBase {


    public ItemPartCableHigh(String unlocalizedname) {
        super(unlocalizedname);
        setCreativeTab(CreativeTabsMg.ElectricalAgeTab);
    }

    @Override
    public TMultiPart newPart(ItemStack arg0, EntityPlayer arg1, World arg2,
                              BlockCoord arg3, int arg4, Vector3 arg5) {
        return new PartCableHigh();
    }
}
