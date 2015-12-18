package com.cout970.magneticraft.client.gui;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.client.gui.component.*;
import com.cout970.magneticraft.tileentity.TileBattery;
import com.cout970.magneticraft.util.RenderUtil;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GuiBattery extends GuiBasic {

    public GuiBattery(Container c, TileEntity tile) {
        super(c, tile);
    }

    @Override
    public void initComponents() {
        comp.add(new CompBackground(new ResourceLocation(Magneticraft.NAME.toLowerCase(Locale.US) + ":textures/gui/battery.png")));
        comp.add(new CompEnergyBar(new ResourceLocation(Magneticraft.NAME.toLowerCase(Locale.US) + ":textures/gui/energybar.png"), new GuiPoint(47, 16), ((TileBattery) tile).cond));
        comp.add(new CompBatteryBar(new ResourceLocation(Magneticraft.NAME.toLowerCase(Locale.US) + ":textures/gui/energybar.png"), new GuiPoint(69, 16)));
        comp.add(new CompButtonRedstoneControl(new GuiPoint(150, 8)));
    }

    public class CompBatteryBar implements IGuiComp {

        public ResourceLocation texture;
        public GuiPoint pos;

        public CompBatteryBar(ResourceLocation tex, GuiPoint p) {
            texture = tex;
            pos = p;
        }

        @Override
        public void render(int mx, int my, TileEntity tile, GuiBasic gui) {
            if (tile instanceof TileBattery) {
                TileBattery bat = (TileBattery) tile;
                IElectricConductor c = bat.cond;
                int scale = c.getStorage() * 50 / c.getMaxStorage();
                gui.mc.renderEngine.bindTexture(texture);
                RenderUtil.drawTexturedModalRectScaled(gui.xStart + pos.x, gui.yStart + pos.y + (50 - scale), 30, 50 - scale, 16, scale, 70, 50);

                if (c.getVoltage() > ElectricConstants.BATTERY_CHARGE && c.getStorage() < c.getMaxStorage() && bat.getChargeRate() > 0) {
                    gui.mc.renderEngine.bindTexture(RenderUtil.MISC_ICONS);
                    RenderUtil.drawTexturedModalRectScaled(gui.xStart + 57, gui.yStart + 21, 10, 0, 7, 6, 50, 20);
                }
                if (c.getVoltage() < ElectricConstants.BATTERY_DISCHARGE && c.getStorage() > 0 && bat.getDischargeRate() > 0) {
                    gui.mc.renderEngine.bindTexture(RenderUtil.MISC_ICONS);
                    RenderUtil.drawTexturedModalRectScaled(gui.xStart + 57, gui.yStart + 53, 10, 6, 7, 6, 50, 20);
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
            if (tile instanceof TileBattery) {
                IElectricConductor c = ((TileBattery) tile).cond;
                if (isIn(mx, my, gui.xStart + pos.x, gui.yStart + pos.y, 16, 50)) {
                    List<String> data = new ArrayList<>();
                    data.add(String.format("%.3fk" + Magneticraft.ENERGY_STORED_NAME, c.getStorage() / 1000f));
                    gui.drawHoveringText2(data, mx - gui.xStart, my - gui.yStart);
                    RenderHelper.enableGUIStandardItemLighting();
                }else if (isIn(mx, my, gui.xStart + 57, gui.yStart + 20, 8, 8)) {
                    List<String> data = new ArrayList<>();
                    data.add(String.format(EnumChatFormatting.DARK_GREEN+"+%.3fkW", ((TileBattery) tile).getChargeRate()/1000));
                    gui.drawHoveringText2(data, mx - gui.xStart, my - gui.yStart);
                    RenderHelper.enableGUIStandardItemLighting();
                }else if (isIn(mx, my, gui.xStart + 57, gui.yStart + 53, 8, 8)) {
                    List<String> data = new ArrayList<>();
                    data.add(String.format(EnumChatFormatting.DARK_RED+"-%.3fkW", ((TileBattery) tile).getDischargeRate()/1000));
                    gui.drawHoveringText2(data, mx - gui.xStart, my - gui.yStart);
                    RenderHelper.enableGUIStandardItemLighting();
                }
            }
        }

    }

}
