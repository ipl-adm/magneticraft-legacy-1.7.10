package com.cout970.magneticraft.client.gui;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.ManagerNetwork;
import com.cout970.magneticraft.client.gui.component.*;
import com.cout970.magneticraft.messages.MessageGuiClick;
import com.cout970.magneticraft.tileentity.TileMiner;
import com.cout970.magneticraft.util.RenderUtil;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class GuiMiner extends GuiBasic {

    public GuiMiner(Container c, TileEntity tile) {
        super(c, tile);
    }

    @Override
    public void initComponents() {
        comp.add(new CompBackground(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/miner.png")));
        comp.add(new CompEnergyBarMediumVoltage(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/energybar2.png"), new GuiPoint(23, 16), ((TileMiner) tile).cond));
        comp.add(new CompEnergyTrackerBar(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/consumptionbar.png"), new GuiPoint(32, 19), ((TileMiner) tile).getEnergyTracker()));
        comp.add(new CompGenericBar(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/efficiencybar.png"), new GuiPoint(41, 19), ((TileMiner) tile).getBlocksMinedLastSecondBar()));
        comp.add(new CompMiningBar(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/windbar.png"), new GuiPoint(50, 19)));
        comp.add(new CompMinerGui(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/miner.png"), new GuiPoint(104, 47)));
        comp.add(new CompButtonRedstoneControl(new GuiPoint(150, 8)));
    }


    public class CompMiningBar implements IGuiComp {

        public ResourceLocation texture;
        public GuiPoint pos;

        public CompMiningBar(ResourceLocation tex, GuiPoint p) {
            texture = tex;
            pos = p;
        }

        @Override
        public void render(int mx, int my, TileEntity tile, GuiBasic gui) {
            if (tile instanceof TileMiner) {
                TileMiner c = (TileMiner) tile;
                float prod = c.hole / ((float) c.dim * c.dim);
                int scale = (int) (44 * prod);
                gui.mc.renderEngine.bindTexture(texture);
                RenderUtil.drawTexturedModalRectScaled(gui.xStart + pos.x, gui.yStart + pos.y + (44 - scale), 0, 44 - scale, 6, scale, 12, 45);
                gui.drawString(getFontRenderer(), c.dim + "x" + c.dim, gui.xStart + 60, gui.yStart + 40, RenderUtil.fromRGB(255, 255, 255));
            }
        }

        @Override
        public void onClick(int mx, int my, int button, GuiBasic gui) {
            if (isIn(mx, my, gui.xStart + 61, gui.yStart + 56, 9, 9)) {
                MessageGuiClick msg = new MessageGuiClick(gui.tile, 0, isShiftKeyDown() ? 10 : 1);
                ManagerNetwork.INSTANCE.sendToServer(msg);
            } else if (isIn(mx, my, gui.xStart + 72, gui.yStart + 56, 9, 9)) {
                MessageGuiClick msg = new MessageGuiClick(gui.tile, 1, isShiftKeyDown() ? 10 : 1);
                ManagerNetwork.INSTANCE.sendToServer(msg);
            }
        }

        @Override
        public boolean onKey(int n, char key, GuiBasic gui) {
            return false;
        }

        @Override
        public void renderTop(int mx, int my, TileEntity tile, GuiBasic gui) {
            if (tile instanceof TileMiner) {
                TileMiner c = (TileMiner) tile;
                if (isIn(mx, my, gui.xStart + pos.x, gui.yStart + pos.y, 6, 44)) {
                    List<String> data = new ArrayList<>();
                    float prod = c.hole * 100 / (c.dim * c.dim);
                    data.add("Mined " + ((int) prod) + "%");
                    gui.drawHoveringText2(data, mx - gui.xStart, my - gui.yStart);
                    RenderHelper.enableGUIStandardItemLighting();
                }
            }
        }
    }

    public class CompMinerGui implements IGuiComp {

        public GuiPoint pos;
        public ResourceLocation texture;

        public CompMinerGui(ResourceLocation resourceLocation, GuiPoint guiPoint) {
            texture = resourceLocation;
            pos = guiPoint;
        }

        @Override
        public void render(int mx, int my, TileEntity tile, GuiBasic gui) {
            if (tile instanceof TileMiner) {
                TileMiner t = (TileMiner) tile;
                if (!t.replaceWithDirt) {
                    RenderUtil.bindTexture(texture);
                    gui.drawTexturedModalRect(gui.xStart + pos.x, gui.yStart + pos.y, 176, 0, 17, 17);
                }

                if (!t.removeWater) {
                    RenderUtil.bindTexture(texture);
                    gui.drawTexturedModalRect(gui.xStart + pos.x + 21, gui.yStart + pos.y, 193, 0, 17, 17);
                }
            }
        }

        @Override
        public void onClick(int mx, int my, int button, GuiBasic gui) {
            if (gui.tile instanceof TileMiner) {
                if (isIn(mx, my, pos.x + gui.xStart, pos.y + gui.yStart, 17, 17)) {
                    ManagerNetwork.INSTANCE.sendToServer(new MessageGuiClick(gui.tile, 2, ((TileMiner) gui.tile).replaceWithDirt ? 0 : 1));
                } else if (isIn(mx, my, pos.x + gui.xStart + 21, pos.y + gui.yStart, 17, 17)) {
                    ManagerNetwork.INSTANCE.sendToServer(new MessageGuiClick(gui.tile, 3, ((TileMiner) gui.tile).removeWater ? 0 : 1));
                }
            }
        }

        @Override
        public boolean onKey(int n, char key, GuiBasic gui) {
            return false;
        }

        @Override
        public void renderTop(int mx, int my, TileEntity tile, GuiBasic gui) {
            if (gui.tile instanceof TileMiner) {
                TileMiner t = (TileMiner) gui.tile;
                if (isIn(mx, my, pos.x + gui.xStart, pos.y + gui.yStart, 17, 17)) {
                    List<String> data = new ArrayList<>();
                    data.add(t.replaceWithDirt ? "Replace Blocks with Dirt" : "Leave Spaces");
                    gui.drawHoveringText2(data, mx - gui.xStart, my - gui.yStart);
                    RenderHelper.enableGUIStandardItemLighting();
                } else if (isIn(mx, my, pos.x + gui.xStart + 21, pos.y + gui.yStart, 17, 17)) {
                    List<String> data = new ArrayList<>();
                    data.add(t.removeWater ? "Remove Water and Lava" : "Leave Water and Lava");
                    gui.drawHoveringText2(data, mx - gui.xStart, my - gui.yStart);
                    RenderHelper.enableGUIStandardItemLighting();
                }
            }
        }
    }
}
