package com.cout970.magneticraft.client.gui;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.client.gui.component.*;
import com.cout970.magneticraft.tileentity.multiblock.controllers.TileStirlingGenerator;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class GuiStirlingGenerator extends GuiBasic {

    public GuiStirlingGenerator(Container c, TileEntity tile) {
        super(c, tile);
    }

    @Override
    public void initComponents() {
        comp.add(new CompBackground(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/stirling_generator.png")));
        comp.add(new CompEnergyBar(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/energybar.png"), new GuiPoint(23, 16), ((TileStirlingGenerator) tile).cond));
        comp.add(new CompBurningTime(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/fire.png"), new GuiPoint(80, 28), ((TileStirlingGenerator) tile).getBurningTimeBar()));
        comp.add(new CompGenericBar(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/efficiencybar.png"), new GuiPoint(39, 20), ((TileStirlingGenerator) tile).getProductionBar()));
        comp.add(new CompHeatBar(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/heatbar.png"), new GuiPoint(31, 20), ((TileStirlingGenerator) tile).heat));
        comp.add(new CompButtonRedstoneControl(new GuiPoint(150, 8)));
    }

}
