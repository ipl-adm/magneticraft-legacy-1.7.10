package com.cout970.magneticraft.client.gui;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.ManagerNetwork;
import com.cout970.magneticraft.client.gui.component.*;
import com.cout970.magneticraft.messages.MessageGuiClick;
import com.cout970.magneticraft.tileentity.TileBreaker;
import com.cout970.magneticraft.util.RenderUtil;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class GuiBreaker extends GuiBasic {

    public GuiBreaker(Container c, TileEntity tile) {
        super(c, tile);
    }

    @Override
    public void initComponents() {
        comp.add(new CompBackground(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/breaker.png")));
        comp.add(new CompButtonRedstoneControl(new GuiPoint(152, 5)));
        comp.add(new CompEnergyBar(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/energybar.png"), new GuiPoint(85, 30), ((TileBreaker) tile).cond));
        comp.add(new CompBreakerGui(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/breaker.png"), new GuiPoint(6, 6)));
    }

    public class CompBreakerGui implements IGuiComp {

        public GuiPoint pos;
        public ResourceLocation texture;

        public CompBreakerGui(ResourceLocation resourceLocation, GuiPoint guiPoint) {
            texture = resourceLocation;
            pos = guiPoint;
        }

        @Override
        public void render(int mx, int my, TileEntity tile, GuiBasic gui) {
            if (tile instanceof TileBreaker) {
                TileBreaker t = (TileBreaker) tile;
                if (!t.whiteList) {
                    RenderUtil.bindTexture(texture);
                    gui.drawTexturedModalRect(gui.xStart + pos.x, gui.yStart + pos.y, 193, 0, 17, 17);
                }

                if (t.ignoreMeta) {
                    RenderUtil.bindTexture(texture);
                    gui.drawTexturedModalRect(gui.xStart + pos.x + 19, gui.yStart + pos.y, 193, 17, 17, 17);
                }

                if (t.ignoreNBT) {
                    RenderUtil.bindTexture(texture);
                    gui.drawTexturedModalRect(gui.xStart + pos.x + 38, gui.yStart + pos.y, 193, 34, 17, 17);
                }

                if (t.ignoreDict) {
                    RenderUtil.bindTexture(texture);
                    gui.drawTexturedModalRect(gui.xStart + pos.x + 57, gui.yStart + pos.y, 193, 51, 17, 17);
                }
            }
        }

        @Override
        public void onClick(int mx, int my, int button, GuiBasic gui) {
            if (gui.tile instanceof TileBreaker) {
                if (isIn(mx, my, pos.x + gui.xStart, pos.y + gui.yStart, 17, 17)) {
                    ManagerNetwork.INSTANCE.sendToServer(new MessageGuiClick(gui.tile, 0, ((TileBreaker) gui.tile).whiteList ? 0 : 1));
                } else if (isIn(mx, my, pos.x + gui.xStart + 19, pos.y + gui.yStart, 17, 17)) {
                    ManagerNetwork.INSTANCE.sendToServer(new MessageGuiClick(gui.tile, 1, ((TileBreaker) gui.tile).ignoreMeta ? 0 : 1));
                } else if (isIn(mx, my, pos.x + gui.xStart + 38, pos.y + gui.yStart, 17, 17)) {
                    ManagerNetwork.INSTANCE.sendToServer(new MessageGuiClick(gui.tile, 2, ((TileBreaker) gui.tile).ignoreNBT ? 0 : 1));
                } else if (isIn(mx, my, pos.x + gui.xStart + 57, pos.y + gui.yStart, 17, 17)) {
                    ManagerNetwork.INSTANCE.sendToServer(new MessageGuiClick(gui.tile, 3, ((TileBreaker) gui.tile).ignoreDict ? 0 : 1));
                }
            }
        }

        @Override
        public boolean onKey(int n, char key, GuiBasic gui) {
            return false;
        }

        @Override
        public void renderTop(int mx, int my, TileEntity tile, GuiBasic gui) {
            if (gui.tile instanceof TileBreaker) {
                TileBreaker t = (TileBreaker) gui.tile;
                if (isIn(mx, my, pos.x + gui.xStart, pos.y + gui.yStart, 17, 17)) {
                    List<String> data = new ArrayList<>();
                    data.add(t.whiteList ? "Whitelist" : "Blacklist");
                    gui.drawHoveringText2(data, mx - gui.xStart, my - gui.yStart);
                    RenderHelper.enableGUIStandardItemLighting();
                } else if (isIn(mx, my, pos.x + gui.xStart + 19, pos.y + gui.yStart, 17, 17)) {
                    List<String> data = new ArrayList<>();
                    data.add(t.ignoreMeta ? "Ignore Metadata" : "Check Metadata");
                    gui.drawHoveringText2(data, mx - gui.xStart, my - gui.yStart);
                    RenderHelper.enableGUIStandardItemLighting();
                } else if (isIn(mx, my, pos.x + gui.xStart + 38, pos.y + gui.yStart, 17, 17)) {
                    List<String> data = new ArrayList<>();
                    data.add(t.ignoreNBT ? "Ignore NBT" : "Check NBT");
                    gui.drawHoveringText2(data, mx - gui.xStart, my - gui.yStart);
                    RenderHelper.enableGUIStandardItemLighting();
                } else if (isIn(mx, my, pos.x + gui.xStart + 57, pos.y + gui.yStart, 17, 17)) {
                    List<String> data = new ArrayList<>();
                    data.add(t.ignoreDict ? "Ignore Ore Dictionary" : "Check Ore Dictionary");
                    gui.drawHoveringText2(data, mx - gui.xStart, my - gui.yStart);
                    RenderHelper.enableGUIStandardItemLighting();
                }
            }
        }
    }
}
