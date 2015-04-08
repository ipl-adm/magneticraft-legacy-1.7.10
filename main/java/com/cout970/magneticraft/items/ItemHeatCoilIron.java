package com.cout970.magneticraft.items;

import com.cout970.magneticraft.tool.IFurnaceTool;

public class ItemHeatCoilIron extends ItemBasic implements IFurnaceTool{

	public ItemHeatCoilIron(String unlocalizedname) {
		super(unlocalizedname);
	}

	@Override
	public int getCookTime() {
		return 60;
	}

	@Override
	public double getElectricConsumption() {
		return 3.4D;
	}

}
