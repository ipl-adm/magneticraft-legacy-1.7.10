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
	public IHeatConductor cond;
	
	public CompHeatBar(ResourceLocation tex, GuiPoint p, IHeatConductor cond){
		texture = tex;
		pos = p;
		this.cond = cond;
	}

	@Override
	public void render(int mx, int my, TileEntity tile, GuiBasic gui) {
		if(cond == null)return;
		int scale = 0;
		if(cond.getMaxTemp() > 0)
			scale = (int) (cond.getTemperature() * 44 / cond.getMaxTemp());
		if(scale > 44)scale = 44;
		gui.mc.renderEngine.bindTexture(texture);
		RenderUtil.drawTexturedModalRectScaled(gui.xStart+pos.x, gui.yStart+pos.y+(44-scale), 0, 44-scale, 6, scale, 12, 45);
	}

	@Override
	public void onClick(int mx, int my, int buttom, GuiBasic gui) {}

	@Override
	public boolean onKey(int n, char key, GuiBasic gui) {return false;}

	@Override
	public void renderTop(int mx, int my, TileEntity tile, GuiBasic gui) {
		if(cond == null)return;
		if(gui.isIn(mx, my, gui.xStart+pos.x, gui.yStart+pos.y, 6, 44)){
			List<String> data = new ArrayList<String>();
			data.add((int)cond.getTemperature()+"C");
			gui.drawHoveringText2(data, mx-gui.xStart, my-gui.yStart);
			RenderHelper.enableGUIStandardItemLighting();
		}
	}

}
