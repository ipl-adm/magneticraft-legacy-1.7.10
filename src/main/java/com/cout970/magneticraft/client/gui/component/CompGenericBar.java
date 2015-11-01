package com.cout970.magneticraft.client.gui.component;

import com.cout970.magneticraft.client.gui.GuiBasic;
import com.cout970.magneticraft.util.RenderUtil;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class CompGenericBar implements IGuiComp {

    public ResourceLocation texture;
    public GuiPoint pos;
    public IBarProvider bar;

    public CompGenericBar(ResourceLocation tex, GuiPoint p, IBarProvider bar) {
        texture = tex;
        pos = p;
        this.bar = bar;
    }

    @Override
    public void render(int mx, int my, TileEntity tile, GuiBasic gui) {
        if (bar != null) {
            int scale = (int) (44 * bar.getLevel() / bar.getMaxLevel());
            gui.mc.renderEngine.bindTexture(texture);
            RenderUtil.drawTexturedModalRectScaled(gui.xStart + pos.x, gui.yStart + pos.y + (44 - scale), 0, 44 - scale, 6, scale, 12, 45);
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
        if (bar != null) {
            if (GuiBasic.isIn(mx, my, gui.xStart + pos.x, gui.yStart + pos.y, 6, 44)) {
                List<String> data = new ArrayList<>();
                data.add(bar.getMessage());
                gui.drawHoveringText2(data, mx - gui.xStart, my - gui.yStart);
                RenderHelper.enableGUIStandardItemLighting();
            }
        }
    }

}