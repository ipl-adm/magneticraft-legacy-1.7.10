package com.cout970.magneticraft.client.gui;

import com.cout970.magneticraft.client.gui.component.IGuiComp;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public abstract class GuiBasic extends GuiContainer {

    public List<IGuiComp> comp = new ArrayList<>();
    public TileEntity tile;
    public int xStart;
    public int yStart;
    public int xTam, yTam;

    public GuiBasic(Container c, TileEntity tile) {
        super(c);
        this.tile = tile;
        xTam = xSize;
        yTam = ySize;
        initComponents();
    }

    public abstract void initComponents();

    protected void drawCenteredStringWithoutShadow(FontRenderer fontRenderer, String string, int x, int y, int color) {
        fontRenderer.drawString(string, x - fontRenderer.getStringWidth(string) / 2, y, color);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float fps, int mx, int my) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        xStart = (width - xSize) / 2;
        yStart = (height - ySize) / 2;
        for (IGuiComp c : comp) {
            c.render(mx, my, tile, this);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        for (IGuiComp c : comp) {
            c.renderTop(x, y, tile, this);
        }
    }

    /**
     * b == 0 => left
     * b == 1 => right
     */
    @Override
    protected void mouseClicked(int x, int y, int b) {
        super.mouseClicked(x, y, b);
        for (IGuiComp c : comp) {
            c.onClick(x, y, b, this);
        }
    }

    public static boolean isIn(int mx, int my, int x, int y, int w, int h) {
        if (mx > x && mx < x + w) {
            if (my > y && my < y + h) {
                return true;
            }
        }
        return false;
    }

    protected void keyTyped(char letter, int num) {
        boolean block = false;
        for (IGuiComp c : comp) {
            if (c.onKey(num, letter, this)) block = true;
        }
        if (!block)
            super.keyTyped(letter, num);
    }

    public FontRenderer getFontRenderer() {
        return this.fontRendererObj;
    }

    public void drawHoveringText2(List<String> data, int x, int y) {
        this.drawHoveringText(data, x, y, fontRendererObj);
    }
}
