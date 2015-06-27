package com.cout970.magneticraft.client.gui.component;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import com.cout970.magneticraft.api.electricity.CompoundElectricCables;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.IElectricTile;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.client.gui.GuiBasic;
import com.cout970.magneticraft.util.RenderUtil;

public class CompEnergyBar implements IGuiComp{

	public ResourceLocation texture;
	public GuiPoint pos;
	
	public CompEnergyBar(ResourceLocation tex, GuiPoint p){
		texture = tex;
		pos = p;
	}

	@Override
	public void render(int mx, int my, TileEntity tile, GuiBasic gui) {
		if(tile instanceof IElectricTile){
			CompoundElectricCables j = ((IElectricTile) tile).getConds(VecInt.NULL_VECTOR,0);
			if(j == null)return;
			IElectricConductor c = j.getCond(0);
			if(c == null)return;
			int scale = (int) (c.getVoltage() >= ElectricConstants.MAX_VOLTAGE ? 50 : 50*(c.getVoltage()/ElectricConstants.MAX_VOLTAGE));
			gui.mc.renderEngine.bindTexture(texture);
			RenderUtil.drawTexturedModalRectScaled(gui.xStart+pos.x, gui.yStart+pos.y+(50-scale), 25, 50-scale, 5, scale, 70, 50);
		}
	}

	@Override
	public void onClick(int mx, int my, int buttom, GuiBasic gui) {}

	@Override
	public boolean onKey(int n, char key, GuiBasic gui) {return false;}

	@Override
	public void renderTop(int mx, int my, TileEntity tile, GuiBasic gui) {
		if(tile instanceof IElectricTile){
			CompoundElectricCables j = ((IElectricTile) tile).getConds(VecInt.NULL_VECTOR,0);
			if(j == null)return;
			IElectricConductor c = j.getCond(0);
			if(c == null)return;
			if(gui.isIn(mx, my, gui.xStart+pos.x, gui.yStart+pos.y, 6, 50)){
				List<String> data = new ArrayList<String>();
				data.add(((int)c.getVoltage())+"V");
				gui.drawHoveringText2(data, mx-gui.xStart, my-gui.yStart);
				RenderHelper.enableGUIStandardItemLighting();
			}
		}
	}

}
