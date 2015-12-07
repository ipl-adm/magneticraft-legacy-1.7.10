package com.cout970.magneticraft.client.gui;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.client.gui.component.*;
import com.cout970.magneticraft.tileentity.TileKineticGenerator;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class GuiKineticGenerator extends GuiBasic {

    public GuiKineticGenerator(Container c, TileEntity tile) {
        super(c, tile);
    }

    @Override
    public void initComponents() {
        comp.add(new CompBackground(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/kinetic.png")));
        comp.add(new CompEnergyBarMediumVoltage(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/energybar2.png"), new GuiPoint(23, 16), ((TileKineticGenerator) tile).cond));
        comp.add(new CompRFBar(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/rfbar.png"), new GuiPoint(42, 23)));
        comp.add(new CompButtonRedstoneControl(new GuiPoint(150, 8)));
        comp.add(new CompGenericBar(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/efficiencybar.png"), new GuiPoint(32, 20), ((TileKineticGenerator) tile).getProductionBar()));
    }
}
