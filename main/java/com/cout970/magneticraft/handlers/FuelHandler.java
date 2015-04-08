package com.cout970.magneticraft.handlers;

import net.minecraft.item.ItemStack;

import com.cout970.magneticraft.ManagerItems;
import com.cout970.magneticraft.api.util.MgUtils;

import cpw.mods.fml.common.IFuelHandler;

public class FuelHandler implements IFuelHandler{

	@Override
	public int getBurnTime(ItemStack fuel) {
		if(MgUtils.areEcuals(fuel, new ItemStack(ManagerItems.dustSulfur),false))return 1600;
		return 0;
	}

}
