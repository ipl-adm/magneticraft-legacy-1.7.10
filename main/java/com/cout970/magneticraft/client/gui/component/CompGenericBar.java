package com.cout970.magneticraft.client.gui.component;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import com.cout970.magneticraft.client.gui.GuiBasic;
import com.cout970.magneticraft.util.RenderUtil;

public class CompGenericBar implements IGuiComp{

	public ResourceLocation texture;
	public GuiPoint pos;

	public CompGenericBar(ResourceLocation tex, GuiPoint p){
		texture = tex;
		pos = p;
	}

	@Override
	public void render(int mx, int my, TileEntity tile, GuiBasic gui) {
		if(tile instanceof IBarProvider){
			IBarProvider c = (IBarProvider) tile;
			int scale = (int) (44*c.getLevel());
			gui.mc.renderEngine.bindTexture(texture);
			RenderUtil.drawTexturedModalRectScaled(gui.xStart+pos.x, gui.yStart+pos.y+(44-scale), 0, 44-scale, 6, scale, 12, 45);
		}
	}

	@Override
	public void onClick(int mx, int my, int buttom, GuiBasic gui) {}

	@Override
	public boolean onKey(int n, char key, GuiBasic gui) {return false;}

	@Override
	public void renderTop(int mx, int my, TileEntity tile, GuiBasic gui) {
		if(tile instanceof IBarProvider){
			IBarProvider c = (IBarProvider) tile;
			if(gui.isIn(mx, my, gui.xStart+pos.x, gui.yStart+pos.y, 6, 44)){
				List<String> data = new ArrayList<String>();
				data.add(c.getMessage());
				gui.drawHoveringText2(data, mx-gui.xStart, my-gui.yStart);
				RenderHelper.enableGUIStandardItemLighting();
			}
		}
	}

}