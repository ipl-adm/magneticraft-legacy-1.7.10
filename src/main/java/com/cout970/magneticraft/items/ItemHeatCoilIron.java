package com.cout970.magneticraft.items;

import java.util.List;

import com.cout970.magneticraft.api.tool.IFurnaceCoil;
import com.cout970.magneticraft.api.util.EnergyConversor;
import com.cout970.magneticraft.tabs.CreativeTabsMg;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemHeatCoilIron extends ItemBasic implements IFurnaceCoil{

	public ItemHeatCoilIron(String unlocalizedname) {
		super(unlocalizedname);
		setCreativeTab(CreativeTabsMg.ElectricalAgeTab);
	}

	@Override
	public int getCookTime() {
		return 50;
	}

	@Override
	public double getElectricConsumption() {
		return EnergyConversor.RFtoW(40);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack item, EntityPlayer player, List list, boolean flag) {
		super.addInformation(item, player, list, flag);
		list.add(ItemBlockMg.format+"Makes the electric furnace run 4 times faster than a normal furnace, uses "+getElectricConsumption()+"W");
	}
}
