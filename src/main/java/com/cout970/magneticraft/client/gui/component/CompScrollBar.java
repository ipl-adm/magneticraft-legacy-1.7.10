package com.cout970.magneticraft.client.gui.component;

import com.cout970.magneticraft.client.gui.GuiBasic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class CompScrollBar implements IGuiComp {
    public GuiPoint posA, posB;
    public int curScroll, maxScroll, width, height;
    public double section;
    private boolean tracking;
    public static ResourceLocation slider = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");

    public CompScrollBar(GuiPoint a, GuiPoint b, int maxScroll) {
        posA = a;
        posB = b;
        width = b.x - a.x;
        height = b.y - a.y;
        curScroll = 0;
        this.maxScroll = maxScroll;
        tracking = false;
        recalculateSections();
    }

    public void recalculateSections() {
        section = (height - 15F) / maxScroll;
    }

    public int getScroll() {
        return (int) Math.round((curScroll + 0F) / section);
    }

    public void setTracking(boolean b) {
        tracking = b;
    }

    @Override
    public void render(int mx, int my, TileEntity tile, GuiBasic gui) {
        gui.mc.getTextureManager().bindTexture(slider);
        GL11.glColor4f(1F, 1F, 1F, 1F);
        gui.drawTexturedModalRect(gui.xStart + posA.x, gui.yStart + curScroll + posA.y, 232, 0, width, 15);
    }

    @Override
    public void onClick(int mx, int my, int button, GuiBasic gui) {
        if (button == 0) {
            if (((mx >= (gui.xStart + posA.x)) && (mx <= (gui.xStart + posB.x)) && (my >= (gui.yStart + posA.y)) && (my <= (gui.yStart + posB.y))) || tracking) {
                setTracking(true);
                curScroll = my - posA.y - gui.yStart - 8;
                applyScrollBounds();
            }
        }
    }

    @Override
    public boolean onKey(int n, char key, GuiBasic gui) {
        return false;
    }

    @Override
    public void renderTop(int mx, int my, TileEntity tile, GuiBasic gui) {
    }

    public void applyScrollBounds() {
        curScroll =  Math.min(Math.max(0, curScroll), height - 15);
    }

    public void onWheel(int direction) {
        if (tracking) {
            return;
        }
        curScroll -= direction * section;
        applyScrollBounds();
    }
}
