package com.cout970.magneticraft.client.gui.component;

import java.util.ArrayList;
import java.util.List;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.client.gui.GuiBasic;
import com.cout970.magneticraft.util.RenderUtil;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class CompStorageBar implements IGuiComp{
	
	public ResourceLocation texture;
	public GuiPoint pos;
	public IElectricConductor cond;
	
	public CompStorageBar(ResourceLocation tex, GuiPoint p, IElectricConductor cond){
		texture = tex;
		pos = p;
		this.cond = cond;
	}
	
	@Override
	public void render(int mx, int my, TileEntity tile, GuiBasic gui) {
		if(cond != null){
			int scale = cond.getMaxStorage() > 0 ? (int) (cond.getStorage() * 50 / cond.getMaxStorage()) : 0;
			gui.mc.renderEngine.bindTexture(texture);
			RenderUtil.drawTexturedModalRectScaled(gui.xStart+pos.x, gui.yStart+pos.y+(50-scale), 59, 50-scale, 11, scale, 70, 50);
		}
	}

	@Override
	public void onClick(int mx, int my, int buttom, GuiBasic gui) {}

	@Override
	public boolean onKey(int n, char key, GuiBasic gui) {return false;}

	@Override
	public void renderTop(int mx, int my, TileEntity tile, GuiBasic gui) {
		if(cond != null){
			if(gui.isIn(mx, my, gui.xStart+pos.x, gui.yStart+pos.y, 11, 50)){
				List<String> data = new ArrayList<String>();
				data.add(String.format("%.3fk"+Magneticraft.ENERGY_STORED_NAME, cond.getStorage()/1000f));
				gui.drawHoveringText2(data, mx-gui.xStart, my-gui.yStart);
				RenderHelper.enableGUIStandardItemLighting();
			}
		}
	}

}