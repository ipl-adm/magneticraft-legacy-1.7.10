package com.cout970.magneticraft.handlers;

import com.cout970.magneticraft.ManagerItems;
import com.cout970.magneticraft.api.util.MgUtils;
import cpw.mods.fml.common.IFuelHandler;
import net.minecraft.item.ItemStack;

public class SolidFuelHandler implements IFuelHandler {

    @Override
    public int getBurnTime(ItemStack fuel) {
        if (MgUtils.areEqual(fuel, new ItemStack(ManagerItems.dustSulfur), false)) return 1600;
        return 0;
    }

}
