package com.cout970.magneticraft.client.gui;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.prefab.BufferedConductor;
import com.cout970.magneticraft.client.gui.component.CompBackground;
import com.cout970.magneticraft.client.gui.component.CompProgressBar;
import com.cout970.magneticraft.client.gui.component.GuiPoint;
import com.cout970.magneticraft.client.gui.component.IGuiComp;
import com.cout970.magneticraft.tileentity.multiblock.controllers.TileGrinder;
import com.cout970.magneticraft.util.RenderUtil;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class GuiGrinder extends GuiBasic {

    public GuiGrinder(Container c, TileEntity tile) {
        super(c, tile);
    }

    @Override
    public void initComponents() {
        comp.add(new CompBackground(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/crusher.png")));
        comp.add(new CompEnergyBar(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/energybar.png"), new GuiPoint(23, 16)));
        comp.add(new CompStorageBar(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/energybar.png"), new GuiPoint(31, 16)));
        comp.add(new CompProgressBar(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/progressbar1.png"), new GuiPoint(75, 31), ((TileGrinder) tile).getProgressBar()));
    }

    public class CompEnergyBar implements IGuiComp {

        public ResourceLocation texture;
        public GuiPoint pos;

        public CompEnergyBar(ResourceLocation tex, GuiPoint p) {
            texture = tex;
            pos = p;
        }

        @Override
        public void render(int mx, int my, TileEntity tile, GuiBasic gui) {
            if (tile instanceof TileGrinder) {
                IElectricConductor c = ((TileGrinder) tile).cond;
                int scale = (int) (c.getVoltage() >= ElectricConstants.MAX_VOLTAGE ? 50 : 50 * (c.getVoltage() / ElectricConstants.MAX_VOLTAGE));
                gui.mc.renderEngine.bindTexture(texture);
                RenderUtil.drawTexturedModalRectScaled(gui.xStart + pos.x, gui.yStart + pos.y + (50 - scale), 25, 50 - scale, 5, scale, 70, 50);
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
            if (tile instanceof TileGrinder) {
                IElectricConductor c = ((TileGrinder) tile).cond;
                if (c == null) return;
                if (isIn(mx, my, gui.xStart + pos.x, gui.yStart + pos.y, 6, 44)) {
                    List<String> data = new ArrayList<>();
                    data.add(((int) c.getVoltage()) + "V");
                    gui.drawHoveringText2(data, mx - gui.xStart, my - gui.yStart);
                    RenderHelper.enableGUIStandardItemLighting();
                }
            }
        }

    }

    public class CompStorageBar implements IGuiComp {

        public ResourceLocation texture;
        public GuiPoint pos;

        public CompStorageBar(ResourceLocation tex, GuiPoint p) {
            texture = tex;
            pos = p;
        }

        @Override
        public void render(int mx, int my, TileEntity tile, GuiBasic gui) {
            if (tile instanceof TileGrinder) {
                IElectricConductor c = ((TileGrinder) tile).cond;
                if (c != null) {
                    int scale = ((BufferedConductor) c).storage * 50 / ((BufferedConductor) c).maxStorage;
                    gui.mc.renderEngine.bindTexture(texture);
                    RenderUtil.drawTexturedModalRectScaled(gui.xStart + pos.x, gui.yStart + pos.y + (50 - scale), 59, 50 - scale, 11, scale, 70, 50);
                }
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
            if (tile instanceof TileGrinder) {
                IElectricConductor c = ((TileGrinder) tile).cond;
                if (c == null) return;
                if (isIn(mx, my, gui.xStart + pos.x, gui.yStart + pos.y, 11, 44)) {
                    List<String> data = new ArrayList<>();
                    data.add(String.format("%.3fk" + Magneticraft.ENERGY_STORED_NAME, c.getStorage() / 1000f));
                    gui.drawHoveringText2(data, mx - gui.xStart, my - gui.yStart);
                    RenderHelper.enableGUIStandardItemLighting();
                }
            }
        }

    }

}