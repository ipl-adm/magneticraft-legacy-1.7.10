package com.cout970.magneticraft.items;

import com.cout970.magneticraft.tool.IFurnaceTool;

public class ItemHeatCoilTungsten extends ItemBasic implements IFurnaceTool{

	public ItemHeatCoilTungsten(String unlocalizedname) {
		super(unlocalizedname);
	}

	@Override
	public int getCookTime() {
		return 25;
	}

	@Override
	public double getElectricConsumption() {
		return 8.0D;
	}

}
