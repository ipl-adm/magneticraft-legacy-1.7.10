package com.cout970.magneticraft.client.gui.component;

import com.cout970.magneticraft.client.gui.GuiBasic;
import com.cout970.magneticraft.util.RenderUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class CompBurningTime implements IGuiComp {

    public ResourceLocation texture;
    public GuiPoint pos;
    public IBarProvider bar;

    public CompBurningTime(ResourceLocation tex, GuiPoint p, IBarProvider bar) {
        texture = tex;
        pos = p;
        this.bar = bar;
    }

    @Override
    public void render(int mx, int my, TileEntity tile, GuiBasic gui) {
        if (bar != null) {
            if (bar.getMaxLevel() <= 0) return;
            int scale = (int) (bar.getLevel() * 13 / bar.getMaxLevel());
            if (bar.getLevel() > 0 && scale == 0) scale = 1;
            gui.mc.renderEngine.bindTexture(texture);
            RenderUtil.drawTexturedModalRectScaled(gui.xStart + pos.x, gui.yStart + pos.y + (13 - scale), 0, 13 - scale, 13, scale, 26, 13);
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
