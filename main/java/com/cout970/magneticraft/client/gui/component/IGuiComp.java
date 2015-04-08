package com.cout970.magneticraft.client.gui.component;

import net.minecraft.tileentity.TileEntity;

import com.cout970.magneticraft.client.gui.GuiBasic;

public interface IGuiComp {

	public void render(int mx, int my, TileEntity tile, GuiBasic gui);
	
	public void onClick(int mx, int my, int buttom, GuiBasic gui);
	
	public boolean onKey(int n, char key, GuiBasic gui);
	
	public void renderTop(int mx, int my, TileEntity tile, GuiBasic gui);
}
