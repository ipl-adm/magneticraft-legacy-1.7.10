package com.cout970.magneticraft.client.gui;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.client.gui.component.*;
import com.cout970.magneticraft.tileentity.TileSteamEngine;
import com.cout970.magneticraft.util.RenderUtil;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class GuiSteamEngine extends GuiBasic {

    public GuiSteamEngine(Container c, TileEntity tile) {
        super(c, tile);
    }

    @Override
    public void initComponents() {
        comp.add(new CompBackground(new ResourceLocation("magneticraft:textures/gui/steam_engine.png")));
        comp.add(new CompEnergyBar(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/energybar.png"), new GuiPoint(23, 16), ((TileSteamEngine) tile).cond));
        comp.add(new CompStorageBar(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/energybar.png"), new GuiPoint(31, 16), ((TileSteamEngine) tile).cond));
        comp.add(new CompFluidRender(((TileSteamEngine) tile).tank, new GuiPoint(66, 25), new GuiPoint(84, 64), new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/tank.png")));
        comp.add(new CompInfoDisplay(new GuiPoint(90, 25)));
        comp.add(new CompEnergyTrackerBar(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/productionbar.png"), new GuiPoint(47, 20), ((TileSteamEngine) tile).getEnergyTracker()));
        comp.add(new CompSteamEngine(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/consumptionbar.png"), new GuiPoint(56, 20)));
        comp.add(new CompButtonRedstoneControl(new GuiPoint(150, 8)));
    }

    public class CompInfoDisplay implements IGuiComp {

        public GuiPoint pos;

        public CompInfoDisplay(GuiPoint p) {
            pos = p;
        }

        @Override
        public void render(int mx, int my, TileEntity tile, GuiBasic gui) {
            if (tile instanceof TileSteamEngine) {
                TileSteamEngine g = (TileSteamEngine) tile;
                String s = "Steam: " + String.format("%.2f", g.steamConsumitionM / 20f) + "mB/t";
                gui.drawString(fontRendererObj, s, gui.xStart + pos.x, gui.yStart + pos.y + 8, RenderUtil.fromRGB(255, 255, 255));
                s = "Energy: " + (int) (g.electricProductionM / 20f) + "W";
                gui.drawString(fontRendererObj, s, gui.xStart + pos.x, gui.yStart + pos.y + 26, RenderUtil.fromRGB(255, 255, 255));
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
        }
    }

    public class CompSteamEngine implements IGuiComp {

        public ResourceLocation texture;
        public GuiPoint pos;

        public CompSteamEngine(ResourceLocation tex, GuiPoint p) {
            texture = tex;
            pos = p;
        }

        @Override
        public void render(int mx, int my, TileEntity tile, GuiBasic gui) {
            if (tile instanceof TileSteamEngine) {
                TileSteamEngine c = (TileSteamEngine) tile;
                float prod = (c.steamConsumitionM) / (TileSteamEngine.STEAM_LIMIT * 20);
                int scale = (int) (44 * prod);
                gui.mc.renderEngine.bindTexture(texture);
                RenderUtil.drawTexturedModalRectScaled(gui.xStart + pos.x, gui.yStart + pos.y + (44 - scale), 0, 44 - scale, 6, scale, 12, 45);
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
            if (tile instanceof TileSteamEngine) {
                TileSteamEngine c = (TileSteamEngine) tile;
                if (isIn(mx, my, gui.xStart + pos.x, gui.yStart + pos.y, 6, 44)) {
                    List<String> data = new ArrayList<>();
                    float prod = c.steamConsumitionM / 20f;
                    data.add("Steam consumption " + ((int) prod) + "mB/t");
                    gui.drawHoveringText2(data, mx - gui.xStart, my - gui.yStart);
                    RenderHelper.enableGUIStandardItemLighting();
                }
            }
        }
    }
}
