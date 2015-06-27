package com.cout970.magneticraft.items;

import net.minecraft.item.ItemStack;

import com.cout970.magneticraft.api.computer.IHardwareModule;
import com.cout970.magneticraft.api.computer.IHardwareProvider;
import com.cout970.magneticraft.api.computer.impl.ModuleCPU_MIPS;
import com.cout970.magneticraft.tabs.CreativeTabsMg;

public class ItemModuleCPU_MIPS extends ItemBasic implements IHardwareProvider{

	public ItemModuleCPU_MIPS(String unlocalizedname) {
		super(unlocalizedname);
		setCreativeTab(CreativeTabsMg.InformationAgeTab);
	}

	@Override
	public IHardwareModule getHardware(ItemStack item) {
		return new ModuleCPU_MIPS();
	}

	@Override
	public ModuleType getModuleType(ItemStack item) {
		return ModuleType.CPU;
	}

}
