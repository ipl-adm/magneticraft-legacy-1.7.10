package com.cout970.magneticraft.items;

import com.cout970.magneticraft.api.tool.IFurnaceCoil;
import com.cout970.magneticraft.api.util.EnergyConverter;
import com.cout970.magneticraft.tabs.CreativeTabsMg;

public class ItemHeatCoilIron extends ItemBasic implements IFurnaceCoil {

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
        return EnergyConverter.RFtoW(40);
    }
}
