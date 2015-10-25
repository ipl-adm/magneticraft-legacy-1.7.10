package com.cout970.magneticraft.client.gui;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.ManagerNetwork;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.client.gui.component.CompBackground;
import com.cout970.magneticraft.client.gui.component.GuiPoint;
import com.cout970.magneticraft.client.gui.component.IGuiComp;
import com.cout970.magneticraft.messages.MessageGuiClick;
import com.cout970.magneticraft.tileentity.TileResistance;
import com.cout970.magneticraft.util.RenderUtil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiResistance extends GuiBasic {

    public GuiResistance(Container c, TileEntity tile) {
        super(c, tile);
    }

    @Override
    public void initComponents() {
        comp.add(new CompBackground(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/resistance.png")));
//		comp.add(new CompEnergyBar(new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/energybar.png"),new GuiPoint(11,16)));
        comp.add(new CompResistance(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/resistance.png"), new GuiPoint(25, 60)));
    }

    public class CompResistance implements IGuiComp {

        public ResourceLocation texture;
        public GuiPoint pos;

        public CompResistance(ResourceLocation res, GuiPoint gui) {
            pos = gui;
            texture = res;
        }

        @Override
        public void render(int mx, int my, TileEntity tile, GuiBasic gui) {
            TileResistance t = (TileResistance) tile;
            GL11.glColor4f(1, 1, 1, 1);
            gui.mc.renderEngine.bindTexture(texture);
            if (GuiScreen.isCtrlKeyDown()) {
                gui.drawTexturedModalRect(gui.xStart + 25, gui.yStart + 60, 0, 166, 141, 14);
            }
            if (t.select != -1)
                gui.drawTexturedModalRect(gui.xStart + 62 + t.select * 15, gui.yStart + 36, 179, 0, 5, 19);

            VecInt color = TileResistance.color_array[t.line1];
            GL11.glColor3ub((byte) color.getX(), (byte) color.getY(), (byte) color.getZ());
            gui.drawTexturedModalRect(gui.xStart + 63, gui.yStart + 37, 176, 0, 3, 17);

            color = TileResistance.color_array[t.line2];
            GL11.glColor3ub((byte) color.getX(), (byte) color.getY(), (byte) color.getZ());
            gui.drawTexturedModalRect(gui.xStart + 78, gui.yStart + 37, 176, 0, 3, 17);

            color = TileResistance.color_array[t.line3];
            GL11.glColor3ub((byte) color.getX(), (byte) color.getY(), (byte) color.getZ());
            gui.drawTexturedModalRect(gui.xStart + 93, gui.yStart + 37, 176, 0, 3, 17);

            GL11.glColor4f(1, 1, 1, 1);
            String str = "R: " + (long) t.getResistance() + '\u03a9';
            String str2 = String.format("I: %.3fA", Math.abs(t.I));
            String str3 = String.format("V: %.3fV", Math.abs(t.cond1.getVoltage() - t.cond2.getVoltage()));
            String str4 = String.format("W: %.3fKW", (Math.abs(Math.min(t.cond1.getVoltage(), t.cond2.getVoltage())) * Math.abs(t.I)) / 1000d);
            gui.drawString(getFontRenderer(), str3, gui.xStart + 10, gui.yStart + 10, RenderUtil.fromRGB(255, 255, 255));
            gui.drawString(getFontRenderer(), str2, gui.xStart + 10, gui.yStart + 20, RenderUtil.fromRGB(255, 255, 255));
            gui.drawString(getFontRenderer(), str, gui.xStart + 100, gui.yStart + 10, RenderUtil.fromRGB(255, 255, 255));
            gui.drawString(getFontRenderer(), str4, gui.xStart + 100, gui.yStart + 20, RenderUtil.fromRGB(255, 255, 255));
        }

        @Override
        public void onClick(int mx, int my, int button, GuiBasic gui) {
            boolean clear = true;
            for (int i = 0; i < 10; i++) {
                if (isIn(mx, my, gui.xStart + pos.x + 14 * i, gui.yStart + pos.y, 14, 14)) {
                    MessageGuiClick msg = new MessageGuiClick(tile, ((TileResistance) tile).select, i);
                    ManagerNetwork.INSTANCE.sendToServer(msg);
                    clear = false;
                    break;
                }
            }
            for (int i = 0; i < 3; i++) {
                if (isIn(mx, my, gui.xStart + 62 + 15 * i, gui.yStart + 36, 5, 19)) {
                    MessageGuiClick msg = new MessageGuiClick(tile, -2, i);
                    ManagerNetwork.INSTANCE.sendToServer(msg);
                    clear = false;
                    break;
                }
            }
            if (clear) {
                MessageGuiClick msg = new MessageGuiClick(tile, -2, -1);
                ManagerNetwork.INSTANCE.sendToServer(msg);
            }
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
