package com.cout970.magneticraft.client.gui;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.client.gui.component.*;
import com.cout970.magneticraft.tileentity.TileThermopile;
import com.cout970.magneticraft.util.RenderUtil;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class GuiThermopile extends GuiBasic {

    public GuiThermopile(Container c, TileEntity tile) {
        super(c, tile);
    }

    @Override
    public void initComponents() {
        comp.add(new CompBackground(new ResourceLocation("magneticraft:textures/gui/thermopile.png")));
        comp.add(new CompEnergyBar(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/energybar.png"), new GuiPoint(23, 16), ((TileThermopile) tile).cond));
        comp.add(new CompHeatDifference(new GuiPoint(32, 20)));
        comp.add(new CompButtonRedstoneControl(new GuiPoint(150, 8)));
    }

    public class CompHeatDifference implements IGuiComp {

        public ResourceLocation texture0, texture1, texture2;
        public GuiPoint pos;

        public CompHeatDifference(GuiPoint p) {
            texture0 = new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/consumptionbar.png");
            texture1 = new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/lavabar.png");
            texture2 = new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/productionbar.png");
            pos = p;
        }

        @Override
        public void render(int mx, int my, TileEntity tile, GuiBasic gui) {
            if (tile instanceof TileThermopile) {
                TileThermopile t = (TileThermopile) tile;
                int scale = (int) (44d * Math.min(t.getCurrentFromDiff() / t.getMaxCurrentFromDiff(), 1d));
                gui.mc.renderEngine.bindTexture(texture0);
                RenderUtil.drawTexturedModalRectScaled(gui.xStart + pos.x, gui.yStart + pos.y + (44 - scale), 0, 44 - scale, 6, scale, 12, 45);
                scale = (int) (44d * Math.min(t.tempHot / 200f, 1d));
                gui.mc.renderEngine.bindTexture(texture1);
                RenderUtil.drawTexturedModalRectScaled(gui.xStart + pos.x + 9, gui.yStart + pos.y + (44 - scale), 0, 44 - scale, 6, scale, 12, 45);
                scale = (int) (44d * Math.min(t.tempCold / 200f, 1d));
                gui.mc.renderEngine.bindTexture(texture2);
                RenderUtil.drawTexturedModalRectScaled(gui.xStart + pos.x + 18, gui.yStart + pos.y + (44 - scale), 0, 44 - scale, 6, scale, 12, 45);
            }
        }

        @Override
        public void onClick(int mx, int my, int button, GuiBasic gui) {
        }

        @Override
        public boolean onKey(int n, char key, GuiBasic gui) {
            return false;
        }

        @Override
        public void renderTop(int mx, int my, TileEntity tile, GuiBasic gui) {
            if (tile instanceof TileThermopile) {
                TileThermopile t = (TileThermopile) tile;
                if (isIn(mx, my, gui.xStart + pos.x, gui.yStart + pos.y, 6, 50)) {
                    List<String> data = new ArrayList<>();
                    data.add(String.format("%dW", (int) t.getCurrentFromDiff()));
                    gui.drawHoveringText2(data, mx - gui.xStart, my - gui.yStart);
                    RenderHelper.enableGUIStandardItemLighting();
                } else if (isIn(mx, my, gui.xStart + pos.x + 9, gui.yStart + pos.y, 6, 50)) {
                    List<String> data = new ArrayList<>();
                    data.add(String.format("Hot Source: %d", t.tempHot));
                    gui.drawHoveringText2(data, mx - gui.xStart, my - gui.yStart);
                    RenderHelper.enableGUIStandardItemLighting();
                } else if (isIn(mx, my, gui.xStart + pos.x + 18, gui.yStart + pos.y, 6, 50)) {
                    List<String> data = new ArrayList<>();
                    data.add(String.format("Cold Source: %d", t.tempCold));
                    gui.drawHoveringText2(data, mx - gui.xStart, my - gui.yStart);
                    RenderHelper.enableGUIStandardItemLighting();
                }
            }
        }

    }
}
