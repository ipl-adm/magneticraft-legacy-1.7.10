package com.cout970.magneticraft.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import com.cout970.magneticraft.api.computer.IHardwareComponent;
import com.cout970.magneticraft.api.computer.IHardwareProvider;
import com.cout970.magneticraft.api.computer.impl.ModuleCPU_MIPS;
import com.cout970.magneticraft.tabs.CreativeTabsMg;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemModuleCPU_MIPS extends ItemBasic implements IHardwareProvider{

	public ItemModuleCPU_MIPS(String unlocalizedname) {
		super(unlocalizedname);
		setCreativeTab(CreativeTabsMg.InformationAgeTab);
	}

	@Override
	public IHardwareComponent getHardware(ItemStack item) {
		return new ModuleCPU_MIPS();
	}

	@Override
	public ModuleType getModuleType(ItemStack item) {
		return ModuleType.CPU;
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack item, EntityPlayer player, List list, boolean flag) {
		super.addInformation(item, player, list, flag);
		list.add(ItemBlockMg.format+"Still WIP");
	}
}
