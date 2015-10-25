package com.cout970.magneticraft.client.gui;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.client.gui.component.CompScreen;
import com.cout970.magneticraft.client.gui.component.IGuiComp;
import com.cout970.magneticraft.tileentity.TileTextMonitor;
import com.cout970.magneticraft.util.RenderUtil;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiTextMonitor extends GuiBasic {

    public GuiTextMonitor(Container c, TileEntity tile) {
        super(c, tile);
        xSize = 350;
        ySize = 230;
    }

    @Override
    public void initComponents() {
        comp.add(new CompBackground(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/monitor.png")));
        comp.add(new CompScreen(((TileTextMonitor) tile).monitor));
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
            RenderUtil.drawTexturedModalRectScaled(gui.xStart, gui.yStart, 0, 0, 350, 230, 512, 256);
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
