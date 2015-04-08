package com.cout970.magneticraft.items;

import com.cout970.magneticraft.tool.IFurnaceTool;

public class ItemHeatCoilCopper extends ItemBasic implements IFurnaceTool{

	public ItemHeatCoilCopper(String unlocalizedname) {
		super(unlocalizedname);
	}

	@Override
	public int getCookTime() {
		return 100;
	}

	@Override
	public double getElectricConsumption() {
		return 2;
	}

}
