package com.cout970.magneticraft.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.client.gui.component.CompBackground;
import com.cout970.magneticraft.client.gui.component.CompButtonRedstoneControl;
import com.cout970.magneticraft.client.gui.component.CompEfficiencyBar;
import com.cout970.magneticraft.client.gui.component.CompEnergyBar;
import com.cout970.magneticraft.client.gui.component.CompProductionBar;
import com.cout970.magneticraft.client.gui.component.CompStorageBar;
import com.cout970.magneticraft.client.gui.component.GuiPoint;
import com.cout970.magneticraft.client.gui.component.IGuiComp;
import com.cout970.magneticraft.tileentity.TileWindTurbine;
import com.cout970.magneticraft.util.RenderUtil;

public class GuiWindMill extends GuiBasic{

	public GuiWindMill(Container c, TileEntity tile) {
		super(c, tile);
	}

	@Override
	public void initComponenets() {
		comp.add(new CompBackground(new ResourceLocation("magneticraft:textures/gui/windmill.png")));
		comp.add(new CompEnergyBar(new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/energybar.png"),new GuiPoint(23,16)));
		comp.add(new CompStorageBar(new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/energybar.png"),new GuiPoint(31,16)));
		comp.add(new CompWindBar(new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/windbar.png"),new GuiPoint(46,20)));
		comp.add(new CompEfficiencyBar(new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/efficiencybar.png"),new GuiPoint(55,20)));
		comp.add(new CompProductionBar(new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/productionbar.png"),new GuiPoint(64,20)));
		comp.add(new CompButtonRedstoneControl(new GuiPoint(150, 8)));
	}

	
	class CompWindBar implements IGuiComp{

		public ResourceLocation texture;
		public GuiPoint pos;
		
		public CompWindBar(ResourceLocation tex, GuiPoint p){
			texture = tex;
			pos = p;
		}

		@Override
		public void render(int mx, int my, TileEntity tile, GuiBasic gui) {
			if(tile instanceof TileWindTurbine){
				TileWindTurbine c = (TileWindTurbine) tile;
				int scale = c.getWindScaled(44);
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
			if(tile instanceof TileWindTurbine){
				TileWindTurbine c = (TileWindTurbine) tile;
				if(gui.isIn(mx, my, gui.xStart+pos.x, gui.yStart+pos.y, 6, 44)){
					List<String> data = new ArrayList<String>();
					data.add("Wind "+c.getWindScaled(1000)/10f+"%");
					gui.drawHoveringText2(data, mx-gui.xStart, my-gui.yStart);
					RenderHelper.enableGUIStandardItemLighting();
				}
			}
		}

	}
}
