package com.cout970.magneticraft.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.api.electricity.BufferedConductor;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.client.gui.component.CompBackground;
import com.cout970.magneticraft.client.gui.component.CompProgresBar;
import com.cout970.magneticraft.client.gui.component.GuiPoint;
import com.cout970.magneticraft.client.gui.component.IGuiComp;
import com.cout970.magneticraft.tileentity.TileCrusher;
import com.cout970.magneticraft.util.RenderUtil;

public class GuiCrusher extends GuiBasic{

	public GuiCrusher(Container c, TileEntity tile) {
		super(c, tile);
	}

	@Override
	public void initComponenets() {
		comp.add(new CompBackground(new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/crusher.png")));
		comp.add(new CompEnergyBar(new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/energybar.png"),new GuiPoint(23,16)));
		comp.add(new CompStorageBar(new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/energybar.png"),new GuiPoint(31,16)));
		comp.add(new CompProgresBar(new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/progresbar1.png"), new GuiPoint(75, 31)));
	}
	
	public class CompEnergyBar implements IGuiComp{

		public ResourceLocation texture;
		public GuiPoint pos;
		
		public CompEnergyBar(ResourceLocation tex, GuiPoint p){
			texture = tex;
			pos = p;
		}

		@Override
		public void render(int mx, int my, TileEntity tile, GuiBasic gui) {
			if(tile instanceof TileCrusher){
				IElectricConductor c = ((TileCrusher) tile).cond;
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
			if(tile instanceof TileCrusher){
				IElectricConductor c = ((TileCrusher) tile).cond;
				if(c == null)return;
				if(gui.isIn(mx, my, gui.xStart+pos.x, gui.yStart+pos.y, 6, 44)){
					List<String> data = new ArrayList<String>();
					data.add(((int)c.getVoltage())+"V");
					gui.drawHoveringText2(data, mx-gui.xStart, my-gui.yStart);
					RenderHelper.enableGUIStandardItemLighting();
				}
			}
		}

	}
	
	public class CompStorageBar implements IGuiComp{
		
		public ResourceLocation texture;
		public GuiPoint pos;
		
		public CompStorageBar(ResourceLocation tex, GuiPoint p){
			texture = tex;
			pos = p;
		}
		
		@Override
		public void render(int mx, int my, TileEntity tile, GuiBasic gui) {
			if(tile instanceof TileCrusher){
				IElectricConductor c = ((TileCrusher) tile).cond;
				if(c instanceof BufferedConductor){
					int scale = (int) (((BufferedConductor) c).storage * 50 / ((BufferedConductor) c).maxStorage);
					gui.mc.renderEngine.bindTexture(texture);
					RenderUtil.drawTexturedModalRectScaled(gui.xStart+pos.x, gui.yStart+pos.y+(50-scale), 59, 50-scale, 11, scale, 70, 50);
				}
			}
		}

		@Override
		public void onClick(int mx, int my, int buttom, GuiBasic gui) {}

		@Override
		public boolean onKey(int n, char key, GuiBasic gui) {return false;}

		@Override
		public void renderTop(int mx, int my, TileEntity tile, GuiBasic gui) {
			if(tile instanceof TileCrusher){
				IElectricConductor c = ((TileCrusher) tile).cond;
				if(c == null)return;
				if(gui.isIn(mx, my, gui.xStart+pos.x, gui.yStart+pos.y, 11, 44)){
					List<String> data = new ArrayList<String>();
					data.add(c.getStorage()+Magneticraft.ENERGY_STORED_NAME);
					gui.drawHoveringText2(data, mx-gui.xStart, my-gui.yStart);
					RenderHelper.enableGUIStandardItemLighting();
				}
			}
		}

	}

}
