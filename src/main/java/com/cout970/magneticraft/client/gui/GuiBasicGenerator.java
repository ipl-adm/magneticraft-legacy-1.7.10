package com.cout970.magneticraft.client.gui;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.client.gui.component.*;
import com.cout970.magneticraft.tileentity.TileBasicGenerator;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import java.util.Locale;

public class GuiBasicGenerator extends GuiBasic {

    public GuiBasicGenerator(Container c, TileEntity tile) {
        super(c, tile);
    }

    @Override
    public void initComponents() {
        comp.add(new CompBackground(new ResourceLocation(Magneticraft.NAME.toLowerCase(Locale.US) + ":textures/gui/basic_generator.png")));
        comp.add(new CompEnergyBar(new ResourceLocation(Magneticraft.NAME.toLowerCase(Locale.US) + ":textures/gui/energybar.png"), new GuiPoint(15, 19), ((TileBasicGenerator) tile).cond));
        comp.add(new CompBurningTime(new ResourceLocation(Magneticraft.NAME.toLowerCase(Locale.US) + ":textures/gui/fire.png"), new GuiPoint(35, 32), ((TileBasicGenerator) tile).getBurningTimeBar()));
        comp.add(new CompHeatBar(new ResourceLocation(Magneticraft.NAME.toLowerCase(Locale.US) + ":textures/gui/heatbar.png"), new GuiPoint(54, 23), ((TileBasicGenerator) tile).heat));
        comp.add(new CompFluidRender(((TileBasicGenerator) tile).water, new GuiPoint(64, 28), new GuiPoint(82, 67), new ResourceLocation(Magneticraft.NAME.toLowerCase(Locale.US) + ":textures/gui/tank.png")));
        comp.add(new CompFluidRender(((TileBasicGenerator) tile).steam, new GuiPoint(96, 28), new GuiPoint(114, 67), new ResourceLocation(Magneticraft.NAME.toLowerCase(Locale.US) + ":textures/gui/tank.png")));
        comp.add(new CompGenericBar(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/efficiencybar.png"), new GuiPoint(24, 23),((TileBasicGenerator) tile).getEnergyProductionBar()));
        comp.add(new CompGenericBar(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/efficiencybar.png"), new GuiPoint(86, 23),((TileBasicGenerator) tile).getSteamProductionBar()));
        comp.add(new CompButtonRedstoneControl(new GuiPoint(150, 8)));
    }
}
