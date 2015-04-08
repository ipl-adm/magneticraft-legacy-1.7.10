package com.cout970.magneticraft.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.api.heat.IHeatConductor;
import com.cout970.magneticraft.client.gui.component.CompBackground;
import com.cout970.magneticraft.client.gui.component.CompFluidRender;
import com.cout970.magneticraft.client.gui.component.CompGenericBar;
import com.cout970.magneticraft.client.gui.component.GuiPoint;
import com.cout970.magneticraft.client.gui.component.IGuiComp;
import com.cout970.magneticraft.tileentity.TileRefinery;
import com.cout970.magneticraft.util.RenderUtil;

public class GuiRefinery extends GuiBasic{

	public GuiRefinery(Container c, TileEntity tile) {
		super(c, tile);
	}

	@Override
	public void initComponenets() {
		comp.add(new CompBackground(new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/refinery.png")));
		comp.add(new CompHeaterBar(new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/heatbar.png"), new GuiPoint(20, 20)));
		comp.add(new CompGenericBar(new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/efficiencybar.png"),new GuiPoint(29, 20)));
		comp.add(new CompFluidRender(((TileRefinery)tile).input, new GuiPoint(41,25), new GuiPoint(59, 64),new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/tank.png")));
		comp.add(new CompFluidRender(((TileRefinery)tile).output0, new GuiPoint(74,25), new GuiPoint(92, 64),new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/tank.png")));
		comp.add(new CompFluidRender(((TileRefinery)tile).output1, new GuiPoint(98,25), new GuiPoint(116, 64),new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/tank.png")));
		comp.add(new CompFluidRender(((TileRefinery)tile).output2, new GuiPoint(122,25), new GuiPoint(140, 64),new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/tank.png")));
	}
	
	public class CompHeaterBar implements IGuiComp{

		public ResourceLocation texture;
		public GuiPoint pos;
		
		public CompHeaterBar(ResourceLocation tex, GuiPoint p){
			texture = tex;
			pos = p;
		}

		@Override
		public void render(int mx, int my, TileEntity tile, GuiBasic gui) {
			if(tile instanceof TileRefinery){
				TileRefinery ref = (TileRefinery) tile;
				IHeatConductor c = ref.heater;
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
			if(tile instanceof TileRefinery){
				TileRefinery ref = (TileRefinery) tile;
				IHeatConductor c = ref.heater;
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
}
