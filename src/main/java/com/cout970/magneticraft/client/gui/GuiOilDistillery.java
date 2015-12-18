package com.cout970.magneticraft.client.gui;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.client.gui.component.*;
import com.cout970.magneticraft.tileentity.multiblock.controllers.TileOilDistillery;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class GuiOilDistillery extends GuiBasic {

    public GuiOilDistillery(Container c, TileEntity tile) {
        super(c, tile);
    }

    @Override
    public void initComponents() {
        comp.add(new CompBackground(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/distillery.png")));
        comp.add(new CompFluidRender(((TileOilDistillery) tile).getInput(), new GuiPoint(63, 25), new GuiPoint(81, 64), new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/tank.png")));
        comp.add(new CompFluidRender(((TileOilDistillery) tile).getOutput(), new GuiPoint(96, 25), new GuiPoint(114, 64), new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/tank.png")));
        comp.add(new CompEnergyBar(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/energybar.png"), new GuiPoint(23, 16), ((TileOilDistillery) tile).own));
        comp.add(new CompGenericBar(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/efficiencybar.png"), new GuiPoint(32, 20), ((TileOilDistillery) tile).getEnergyBar()));
        comp.add(new CompOilDestillery());

    }


    private class CompOilDestillery implements IGuiComp{

        @Override
        public void render(int mx, int my, TileEntity tile, GuiBasic gui) {

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
            if (isIn(mx, my, gui.xStart + 101, gui.yStart + 14, 8, 8)) {
                List<String> data = new ArrayList<>();
                data.add(String.format(EnumChatFormatting.DARK_GREEN+"+%.2f mB/t", ((TileOilDistillery) tile).getProductionRate()));
                gui.drawHoveringText2(data, mx - gui.xStart, my - gui.yStart);
                RenderHelper.enableGUIStandardItemLighting();
            }else if (isIn(mx, my, gui.xStart + 68, gui.yStart + 14, 8, 8)) {
                List<String> data = new ArrayList<>();
                data.add(String.format(EnumChatFormatting.DARK_RED+"-%.2f mB/t", ((TileOilDistillery) tile).getConsumptionRate()));
                gui.drawHoveringText2(data, mx - gui.xStart, my - gui.yStart);
                RenderHelper.enableGUIStandardItemLighting();
            }
        }
    }
}
