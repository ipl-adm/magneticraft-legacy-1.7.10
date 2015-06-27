package com.cout970.magneticraft.items;

import net.minecraft.item.ItemStack;

import com.cout970.magneticraft.api.computer.IHardwareModule;
import com.cout970.magneticraft.api.computer.IHardwareProvider;
import com.cout970.magneticraft.api.computer.impl.ModuleROM;
import com.cout970.magneticraft.tabs.CreativeTabsMg;

public class ItemModuleROM extends ItemBasic implements IHardwareProvider{

	public ItemModuleROM(String unlocalizedname) {
		super(unlocalizedname);
		setCreativeTab(CreativeTabsMg.InformationAgeTab);
	}

	@Override
	public IHardwareModule getHardware(ItemStack item) {
		return new ModuleROM();
	}

	@Override
	public ModuleType getModuleType(ItemStack item) {
		return ModuleType.ROM;
	}

}
