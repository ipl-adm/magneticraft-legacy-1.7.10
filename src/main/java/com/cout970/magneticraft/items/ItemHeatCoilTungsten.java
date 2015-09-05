package com.cout970.magneticraft.items;

import com.cout970.magneticraft.api.tool.IFurnaceCoil;
import com.cout970.magneticraft.api.util.EnergyConversor;
import com.cout970.magneticraft.tabs.CreativeTabsMg;

public class ItemHeatCoilTungsten extends ItemBasic implements IFurnaceCoil {

    public ItemHeatCoilTungsten(String unlocalizedname) {
        super(unlocalizedname);
        setCreativeTab(CreativeTabsMg.ElectricalAgeTab);
    }

    @Override
    public int getCookTime() {
        return 25;
    }

    @Override
    public double getElectricConsumption() {
        return EnergyConversor.RFtoW(80);
    }
}
