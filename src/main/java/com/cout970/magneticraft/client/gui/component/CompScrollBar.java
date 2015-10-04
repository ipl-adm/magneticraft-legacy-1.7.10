package com.cout970.magneticraft.client.gui.component;

import com.cout970.magneticraft.client.gui.GuiBasic;
import net.minecraft.tileentity.TileEntity;

public class CompScrollBar implements IGuiComp {
    private int xSize, ySize, curScroll, maxScroll;

    public CompScrollBar(int x, int y, int maxScroll) {
        xSize = x;
        ySize = y;
        curScroll = 0;
        this.maxScroll = maxScroll;
    }

    @Override
    public void render(int mx, int my, TileEntity tile, GuiBasic gui) {

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

    public int getXSize() {
        return xSize;
    }

    public void setXSize(int xSize) {
        this.xSize = xSize;
    }

    public int getYSize() {
        return ySize;
    }

    public void setYSize(int ySize) {
        this.ySize = ySize;
    }

    public int getCurrentScroll() {
        return curScroll;
    }

    public void setCurrentScroll(int curScroll) {
        this.curScroll = curScroll;
    }

    public int getMaxScroll() {
        return maxScroll;
    }

    public void setMaxScroll(int maxScroll) {
        this.maxScroll = maxScroll;
    }
}
