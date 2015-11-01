package com.cout970.magneticraft.client.gui.component;

import com.cout970.magneticraft.client.gui.GuiBasic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class CompBackground implements IGuiComp {

    public ResourceLocation texture;

    public CompBackground(ResourceLocation tex) {
        texture = tex;
    }

    @Override
    public void render(int mx, int my, TileEntity tile, GuiBasic gui) {

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        gui.mc.renderEngine.bindTexture(texture);
        gui.drawTexturedModalRect(gui.xStart, gui.yStart, 0, 0, gui.xTam, gui.yTam);
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
