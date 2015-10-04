package com.cout970.magneticraft.client.gui.component;

import com.cout970.magneticraft.client.gui.GuiBasic;
import com.cout970.magneticraft.util.RenderUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class CompProgressBar implements IGuiComp {

    public ResourceLocation texture;
    public GuiPoint pos;
    public IBarProvider bar;

    public CompProgressBar(ResourceLocation tex, GuiPoint p, IBarProvider bar) {
        texture = tex;
        pos = p;
        this.bar = bar;
    }

    @Override
    public void render(int mx, int my, TileEntity tile, GuiBasic gui) {
        if (bar != null) {
            int scale = (int) (bar.getLevel() * 22 / bar.getMaxLevel());
            scale = Math.min(scale, 21);
            gui.mc.renderEngine.bindTexture(texture);
            RenderUtil.drawTexturedModalRectScaled(gui.xStart + pos.x, gui.yStart + pos.y, 1, 0, scale, 16, 22 * 2, 16);
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
