package com.cout970.magneticraft.client.gui;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.ManagerNetwork;
import com.cout970.magneticraft.client.gui.component.*;
import com.cout970.magneticraft.messages.MessageGuiClick;
import com.cout970.magneticraft.tileentity.TileDroidRED;
import com.cout970.magneticraft.util.RenderUtil;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiDroidRED extends GuiBasic {

    public GuiDroidRED(Container c, TileEntity tile) {
        super(c, tile);
        xSize = 350;
        ySize = 320;
    }

    @Override
    public void initComponents() {
        comp.add(new CompBackground(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/droid_red.png")));
        comp.add(new CompEnergyBar(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/energybar.png"), new GuiPoint(10, 238), ((TileDroidRED) tile).cond));
        comp.add(new CompStorageBar(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/energybar.png"), new GuiPoint(19, 238), ((TileDroidRED) tile).cond));
        comp.add(new CompScreen(((TileDroidRED) tile).monitor));
        comp.add(new CompDroid(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/droid_red.png")));
    }

    public class CompBackground implements IGuiComp {

        public ResourceLocation texture;

        public CompBackground(ResourceLocation tex) {
            texture = tex;
        }

        @Override
        public void render(int mx, int my, TileEntity tile, GuiBasic gui) {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            gui.mc.renderEngine.bindTexture(texture);
            RenderUtil.drawTexturedModalRectScaled(gui.xStart, gui.yStart, 0, 0, 350, 320, 350, 350);
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

    public class CompDroid implements IGuiComp {

        public ResourceLocation texture;

        public CompDroid(ResourceLocation tex) {
            texture = tex;
        }

        @Override
        public void render(int mx, int my, TileEntity tile, GuiBasic gui) {
            TileDroidRED t = (TileDroidRED) tile;
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

            if (t.isRunning()) {
                gui.drawTexturedModalRect(gui.xStart + 60, gui.yStart + 61 + 235, 0, 177, 9, 9);
            } else {
                gui.drawTexturedModalRect(gui.xStart + 60, gui.yStart + 61 + 235, 9, 177, 9, 9);
            }

            gui.drawString(getFontRenderer(), "Start", gui.xStart + 50, gui.yStart + 7 + 235, RenderUtil.fromRGB(255, 255, 255));
            gui.drawString(getFontRenderer(), "Restart", gui.xStart + 50, gui.yStart + 21 + 237, RenderUtil.fromRGB(255, 255, 255));
            gui.drawString(getFontRenderer(), "Shutdown", gui.xStart + 50, gui.yStart + 35 + 240, RenderUtil.fromRGB(255, 255, 255));
        }

        @Override
        public void onClick(int mx, int my, int button, GuiBasic gui) {
            if (isIn(mx, my, gui.xStart + 40, gui.yStart + 235, 10, 10)) {
                MessageGuiClick msg = new MessageGuiClick(tile, 0, 1);
                ManagerNetwork.INSTANCE.sendToServer(msg);
            } else if (isIn(mx, my, gui.xStart + 40, gui.yStart + 237, 10, 10)) {
                MessageGuiClick msg = new MessageGuiClick(tile, 1, 1);
                ManagerNetwork.INSTANCE.sendToServer(msg);
            } else if (isIn(mx, my, gui.xStart + 40, gui.yStart + 240, 10, 10)) {
                MessageGuiClick msg = new MessageGuiClick(tile, 2, 1);
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
