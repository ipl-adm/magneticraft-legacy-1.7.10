package com.cout970.magneticraft.client.gui.component;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import com.cout970.magneticraft.client.gui.GuiBasic;
import com.cout970.magneticraft.util.RenderUtil;

public class CompProgresBar implements IGuiComp{

	public ResourceLocation texture;
	public GuiPoint pos;
	
	public CompProgresBar(ResourceLocation tex, GuiPoint p){
		texture = tex;
		pos = p;
	}
	
	@Override
	public void render(int mx, int my, TileEntity tile, GuiBasic gui) {
		if(tile instanceof IBurningTime){
			IBurningTime c = ((IBurningTime) tile);
			int scale = c.getProgres() * 22 / c.getMaxProgres();
			scale = Math.min(scale, 21);
			gui.mc.renderEngine.bindTexture(texture);
			RenderUtil.drawTexturedModalRectScaled(gui.xStart + pos.x, gui.yStart + pos.y, 1, 0, scale, 16, 22*2, 16);
		}
	}

	@Override
	public void onClick(int mx, int my, int buttom, GuiBasic gui) {}

	@Override
	public boolean onKey(int n, char key, GuiBasic gui) {return false;}

	@Override
	public void renderTop(int mx, int my, TileEntity tile, GuiBasic gui) {}

}
