package com.cout970.magneticraft.client.gui;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.ManagerNetwork;
import com.cout970.magneticraft.client.gui.component.CompBackground;
import com.cout970.magneticraft.client.gui.component.CompButtonRedstoneControl;
import com.cout970.magneticraft.client.gui.component.GuiPoint;
import com.cout970.magneticraft.client.gui.component.IGuiComp;
import com.cout970.magneticraft.messages.MessageGuiClick;
import com.cout970.magneticraft.tileentity.TileInserter;
import com.cout970.magneticraft.util.RenderUtil;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class GuiInserter extends GuiBasic {

    public GuiInserter(Container c, TileEntity tile) {
        super(c, tile);
    }

    @Override
    public void initComponents() {
        comp.add(new CompBackground(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/inserter.png")));
        comp.add(new CompButtonRedstoneControl(new GuiPoint(150, 52)));
        comp.add(new CompInserterGui(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/inserter.png"), new GuiPoint(74, 53)));
    }

    public class CompInserterGui implements IGuiComp {

        public GuiPoint pos;
        public ResourceLocation texture;

        public CompInserterGui(ResourceLocation resourceLocation, GuiPoint guiPoint) {
            texture = resourceLocation;
            pos = guiPoint;
        }

        @Override
        public void render(int mx, int my, TileEntity tile, GuiBasic gui) {
            if (tile instanceof TileInserter) {
                TileInserter t = (TileInserter) tile;
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
            if (gui.tile instanceof TileInserter) {
                if (isIn(mx, my, pos.x + gui.xStart, pos.y + gui.yStart, 17, 17)) {
                    ManagerNetwork.INSTANCE.sendToServer(new MessageGuiClick(gui.tile, 0, ((TileInserter) gui.tile).whiteList ? 0 : 1));
                } else if (isIn(mx, my, pos.x + gui.xStart + 19, pos.y + gui.yStart, 17, 17)) {
                    ManagerNetwork.INSTANCE.sendToServer(new MessageGuiClick(gui.tile, 1, ((TileInserter) gui.tile).ignoreMeta ? 0 : 1));
                } else if (isIn(mx, my, pos.x + gui.xStart + 38, pos.y + gui.yStart, 17, 17)) {
                    ManagerNetwork.INSTANCE.sendToServer(new MessageGuiClick(gui.tile, 2, ((TileInserter) gui.tile).ignoreNBT ? 0 : 1));
                } else if (isIn(mx, my, pos.x + gui.xStart + 57, pos.y + gui.yStart, 17, 17)) {
                    ManagerNetwork.INSTANCE.sendToServer(new MessageGuiClick(gui.tile, 3, ((TileInserter) gui.tile).ignoreDict ? 0 : 1));
                }
            }
        }

        @Override
        public boolean onKey(int n, char key, GuiBasic gui) {
            return false;
        }

        @Override
        public void renderTop(int mx, int my, TileEntity tile, GuiBasic gui) {
            if (gui.tile instanceof TileInserter) {
                TileInserter t = (TileInserter) gui.tile;
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