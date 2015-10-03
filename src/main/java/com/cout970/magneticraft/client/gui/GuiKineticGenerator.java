package com.cout970.magneticraft.client.gui;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.client.gui.component.*;
import com.cout970.magneticraft.tileentity.TileKineticGenerator;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

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
        comp.add(new CompEnergyTrackerBar(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/productionbar.png"), new GuiPoint(32, 20), ((TileKineticGenerator) tile).getEnergyTracker()) {
            @Override
            public void renderTop(int mx, int my, TileEntity tile, GuiBasic gui) {
                if (track != null) {
                    if (gui.isIn(mx, my, gui.xStart + pos.x, gui.yStart + pos.y, 6, 44)) {
                        List<String> data = new ArrayList<String>();
                        float prod = track.getChangeInTheLastSecond() <= 0 ? track.getChangeInTheLastTick() : track.getChangeInTheLastSecond() / 20;
                        data.add((int) prod + "RF/t");
                        gui.drawHoveringText2(data, mx - gui.xStart, my - gui.yStart);
                        RenderHelper.enableGUIStandardItemLighting();
                    }
                }
            }
        });
    }
}
