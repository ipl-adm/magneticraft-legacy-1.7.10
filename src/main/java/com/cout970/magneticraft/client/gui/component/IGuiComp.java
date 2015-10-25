package com.cout970.magneticraft.client.gui.component;

import com.cout970.magneticraft.client.gui.GuiBasic;
import net.minecraft.tileentity.TileEntity;

public interface IGuiComp {

    void render(int mx, int my, TileEntity tile, GuiBasic gui);

    void onClick(int mx, int my, int button, GuiBasic gui);

    boolean onKey(int n, char key, GuiBasic gui);

    void renderTop(int mx, int my, TileEntity tile, GuiBasic gui);
}
