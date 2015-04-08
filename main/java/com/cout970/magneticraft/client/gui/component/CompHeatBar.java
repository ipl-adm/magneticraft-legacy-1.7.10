package com.cout970.magneticraft.client.gui.component;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import com.cout970.magneticraft.api.heat.IHeatConductor;
import com.cout970.magneticraft.api.heat.IHeatTile;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.client.gui.GuiBasic;
import com.cout970.magneticraft.util.RenderUtil;

public class CompHeatBar implements IGuiComp{

	public ResourceLocation texture;
	public GuiPoint pos;
	
	public CompHeatBar(ResourceLocation tex, GuiPoint p){
		texture = tex;
		pos = p;
	}

	@Override
	public void render(int mx, int my, TileEntity tile, GuiBasic gui) {
		if(tile instanceof IHeatTile){
			IHeatConductor c = ((IHeatTile) tile).getHeatCond(VecInt.NULL_VECTOR);
			if(c == null)return;
			int scale = 0;
			if(c.getMaxTemp() > 0)
			scale = (int) (c.getTemperature() * 44 / c.getMaxTemp());
			if(scale > 44)scale = 44;
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
		if(tile instanceof IHeatTile){
			IHeatConductor c = ((IHeatTile) tile).getHeatCond(VecInt.NULL_VECTOR);
			if(c == null)return;
			if(gui.isIn(mx, my, gui.xStart+pos.x, gui.yStart+pos.y, 6, 44)){
				List<String> data = new ArrayList<String>();
				data.add((int)c.getTemperature()+"C");
				gui.drawHoveringText2(data, mx-gui.xStart, my-gui.yStart);
				RenderHelper.enableGUIStandardItemLighting();
			}
		}
	}

}
