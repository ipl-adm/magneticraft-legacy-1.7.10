package com.cout970.magneticraft.client.gui.component;

import java.util.ArrayList;
import java.util.List;

import com.cout970.magneticraft.client.gui.GuiBasic;
import com.cout970.magneticraft.util.RenderUtil;

import cofh.api.energy.IEnergyHandler;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class CompRFBar implements IGuiComp{

	public ResourceLocation texture;
	public GuiPoint pos;
	
	public CompRFBar(ResourceLocation tex, GuiPoint p){
		texture = tex;
		pos = p;
	}

	@Override
	public void render(int mx, int my, TileEntity tile, GuiBasic gui) {
		if(tile instanceof IEnergyHandler){
			IEnergyHandler c = (IEnergyHandler) tile;
			int scale = (int) (c.getEnergyStored(null)*41/c.getMaxEnergyStored(null));
			gui.mc.renderEngine.bindTexture(texture);
			RenderUtil.drawTexturedModalRectScaled(gui.xStart+pos.x, gui.yStart+pos.y+(41-scale), 1, 41-scale, 12, scale, 28, 42);
		}
	}

	@Override
	public void onClick(int mx, int my, int buttom, GuiBasic gui) {}

	@Override
	public boolean onKey(int n, char key, GuiBasic gui) {return false;}

	@Override
	public void renderTop(int mx, int my, TileEntity tile, GuiBasic gui) {
		if(tile instanceof IEnergyHandler){
			IEnergyHandler c = (IEnergyHandler) tile;
			if(gui.isIn(mx, my, gui.xStart+pos.x, gui.yStart+pos.y, 12, 42)){
				List<String> data = new ArrayList<String>();
				data.add(((int)c.getEnergyStored(null))+"RF");
				gui.drawHoveringText2(data, mx-gui.xStart, my-gui.yStart);
				RenderHelper.enableGUIStandardItemLighting();
			}
		}
	}

}
