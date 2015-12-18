package com.cout970.magneticraft.client.gui;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.client.gui.component.*;
import com.cout970.magneticraft.tileentity.TileElectricFurnace;
import com.cout970.magneticraft.util.RenderUtil;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class GuiElectricFurnace extends GuiBasic {

    public GuiElectricFurnace(Container c, TileEntity tile) {
        super(c, tile);
    }

    @Override
    public void initComponents() {
        comp.add(new CompBackground(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/electric_furnace.png")));
        comp.add(new CompEnergyBar(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/energybar.png"), new GuiPoint(23, 16), ((TileElectricFurnace) tile).cond));
        comp.add(new CompGenericBar(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/efficiencybar.png"), new GuiPoint(31, 20),((TileElectricFurnace) tile).getConsumptionBar()));
        comp.add(new CompProgressBar(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/progressbar1.png"), new GuiPoint(87, 31), ((TileElectricFurnace) tile).getProgressBar()));
        comp.add(new CompHeaterBar(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/heatcoil.png"), new GuiPoint(64, 38)));
        comp.add(new CompButtonRedstoneControl(new GuiPoint(150, 8)));
    }

    public class CompHeaterBar implements IGuiComp {

        public ResourceLocation texture;
        public GuiPoint pos;

        public CompHeaterBar(ResourceLocation tex, GuiPoint p) {
            texture = tex;
            pos = p;
        }

        @Override
        public void render(int mx, int my, TileEntity tile, GuiBasic gui) {
            if (tile instanceof TileElectricFurnace) {
                if (((TileElectricFurnace) tile).getConsumption() > 0) {
                    gui.mc.renderEngine.bindTexture(texture);
                    RenderUtil.drawTexturedModalRectScaled(gui.xStart + pos.x, gui.yStart + pos.y, 0, 0, 11, 11, 22, 11);
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
        }

    }

}
