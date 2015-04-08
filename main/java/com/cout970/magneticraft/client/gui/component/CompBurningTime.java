package com.cout970.magneticraft.client.gui.component;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import com.cout970.magneticraft.client.gui.GuiBasic;
import com.cout970.magneticraft.util.RenderUtil;

public class CompBurningTime implements IGuiComp{

	public ResourceLocation texture;
	public GuiPoint pos;
	
	public CompBurningTime(ResourceLocation tex, GuiPoint p){
		texture = tex;
		pos = p;
	}
	
	@Override
	public void render(int mx, int my, TileEntity tile, GuiBasic gui) {
		if(tile instanceof IBurningTime){
			IBurningTime c = ((IBurningTime) tile);
			if(c.getMaxProgres() <= 0)return;
			int scale = c.getProgres() * 13 / c.getMaxProgres();
			if(c.getProgres() > 0 && scale == 0)scale = 1;
			gui.mc.renderEngine.bindTexture(texture);
			RenderUtil.drawTexturedModalRectScaled(gui.xStart + pos.x, gui.yStart + pos.y + (13-scale), 0, 13-scale, 13, scale, 26, 13);
		}
	}

	@Override
	public void onClick(int mx, int my, int buttom, GuiBasic gui) {}

	@Override
	public boolean onKey(int n, char key, GuiBasic gui) {return false;}

	@Override
	public void renderTop(int mx, int my, TileEntity tile, GuiBasic gui) {}

}
