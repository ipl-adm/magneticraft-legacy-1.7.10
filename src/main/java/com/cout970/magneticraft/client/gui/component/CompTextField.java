package com.cout970.magneticraft.client.gui.component;

import com.cout970.magneticraft.client.gui.GuiBasic;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.tileentity.TileEntity;

public class CompTextField extends GuiTextField implements IGuiComp {
    private int relX, relY, realWidth;

    public CompTextField(FontRenderer fontRenderer, int x, int y, int width, int height) {
        super(fontRenderer, x, y, width - fontRenderer.getCharWidth('_'), height);
        realWidth = width;
        relX = x;
        relY = y;
        setEnableBackgroundDrawing(false);
    }

    @Override
    public void render(int mx, int my, TileEntity tile, GuiBasic gui) {
        xPosition = gui.xStart + relX;
        yPosition = gui.yStart + relY;
        drawTextBox();
    }

    @Override
    public void onClick(int mx, int my, int button, GuiBasic gui) {
        if (button == 0) {
            mouseClicked(mx, my, button);
        }
        if (button == 1 && GuiBasic.isIn(mx, my, xPosition, yPosition, realWidth, height)) {
            setText("");
        }
    }

    @Override
    public boolean onKey(int n, char key, GuiBasic gui) {
        return textboxKeyTyped(key, n);
    }

    @Override
    public void renderTop(int mx, int my, TileEntity tile, GuiBasic gui) {

    }
}
