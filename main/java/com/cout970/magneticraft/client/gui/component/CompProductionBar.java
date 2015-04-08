package com.cout970.magneticraft.client.gui.component;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import com.cout970.magneticraft.client.gui.GuiBasic;
import com.cout970.magneticraft.util.RenderUtil;

public class CompProductionBar implements IGuiComp{

	public ResourceLocation texture;
	public GuiPoint pos;
	private String unidad = "W";

	public CompProductionBar(ResourceLocation tex, GuiPoint p){
		texture = tex;
		pos = p;
	}
	
	public CompProductionBar(String t,ResourceLocation tex, GuiPoint p){
		texture = tex;
		pos = p;
		unidad = t;
	}

	@Override
	public void render(int mx, int my, TileEntity tile, GuiBasic gui) {
		if(tile instanceof IProductor){
			IProductor c = (IProductor) tile;
			float prod = c.getProductionInTheLastSecond() <= 0 ? c.getProductionInTheLastTick()/c.getMaxProduction() : c.getProductionInTheLastSecond()/(c.getMaxProduction()*20);
			int scale = (int) (44*prod);
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
		if(tile instanceof IProductor){
			IProductor c = (IProductor) tile;
			if(gui.isIn(mx, my, gui.xStart+pos.x, gui.yStart+pos.y, 6, 44)){
				List<String> data = new ArrayList<String>();
				float prod = c.getProductionInTheLastSecond() <= 0 ? c.getProductionInTheLastTick() : c.getProductionInTheLastSecond()/20;
				data.add("Production "+((int)prod)+unidad);
				gui.drawHoveringText2(data, mx-gui.xStart, my-gui.yStart);
				RenderHelper.enableGUIStandardItemLighting();
			}
		}
	}
}