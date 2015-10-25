package com.cout970.magneticraft.client.gui.component;

import com.cout970.magneticraft.client.gui.GuiBasic;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompRadioList implements IGuiComp {
    List<CompButton> buttons;
    private int curButton = 0;

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

    public CompRadioList(CompButton... buttons) {
        this.buttons = new ArrayList<>(Arrays.asList(buttons));
    }

    public int getCurrentButton() {
        return curButton;
    }

    public void setCurrentButton(int curButton) {
        this.curButton = curButton;
    }
}
