package com.cout970.magneticraft.items;

import net.minecraft.item.ItemStack;

import com.cout970.magneticraft.api.computer.IHardwareModule;
import com.cout970.magneticraft.api.computer.IHardwareProvider;
import com.cout970.magneticraft.api.computer.impl.ModuleMemoryController;

public class ItemModuleRam64K extends ItemBasic implements IHardwareProvider{

	public ItemModuleRam64K(String unlocalizedname) {
		super(unlocalizedname);
	}

	@Override
	public IHardwareModule getHardware(ItemStack item) {
		return new ModuleMemoryController(0x10000, false, 8);
	}

	@Override
	public ModuleType getModuleType(ItemStack item) {
		return ModuleType.RAM;
	}

}
