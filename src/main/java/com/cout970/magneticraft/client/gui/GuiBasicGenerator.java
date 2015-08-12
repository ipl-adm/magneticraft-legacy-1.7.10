package com.cout970.magneticraft.client.gui;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.client.gui.component.CompBackground;
import com.cout970.magneticraft.client.gui.component.CompBurningTime;
import com.cout970.magneticraft.client.gui.component.CompButtonRedstoneControl;
import com.cout970.magneticraft.client.gui.component.CompEnergyBar;
import com.cout970.magneticraft.client.gui.component.CompFluidRender;
import com.cout970.magneticraft.client.gui.component.CompHeatBar;
import com.cout970.magneticraft.client.gui.component.CompStorageBar;
import com.cout970.magneticraft.client.gui.component.GuiPoint;
import com.cout970.magneticraft.client.gui.component.IGuiComp;
import com.cout970.magneticraft.tileentity.TileBasicGenerator;
import com.cout970.magneticraft.util.RenderUtil;

import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class GuiBasicGenerator extends GuiBasic{

	public GuiBasicGenerator(Container c, TileEntity tile) {
		super(c, tile);
	}

	@Override
	public void initComponenets() {
		comp.add(new CompBackground(new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/basic_generator.png")));
		comp.add(new CompEnergyBar(new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/energybar.png"),new GuiPoint(15,19), ((TileBasicGenerator)tile).cond));
		comp.add(new CompStorageBar(new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/energybar.png"),new GuiPoint(23,19), ((TileBasicGenerator)tile).cond));
		comp.add(new CompBurningTime(new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/fire.png"),new GuiPoint(41, 32), ((TileBasicGenerator)tile).getBurningTimeBar()));
		comp.add(new CompHeatBar(new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/heatbar.png"), new GuiPoint(60,23), ((TileBasicGenerator)tile).heat));
		comp.add(new CompInfoDisplay(new GuiPoint(124, 16)));
		comp.add(new CompFluidRender(((TileBasicGenerator)tile).water, new GuiPoint(72,28), new GuiPoint(90, 67),new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/tank.png")));
		comp.add(new CompFluidRender(((TileBasicGenerator)tile).steam, new GuiPoint(97,28), new GuiPoint(115, 67),new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/tank.png")));
		comp.add(new CompButtonRedstoneControl(new GuiPoint(39, 8)));
	}
	
	public class CompInfoDisplay implements IGuiComp{
		
		public GuiPoint pos;
		
		public CompInfoDisplay(GuiPoint p){
			pos = p;
		}

		@Override
		public void render(int mx, int my, TileEntity tile, GuiBasic gui) {
			if(tile instanceof TileBasicGenerator){
				TileBasicGenerator g = (TileBasicGenerator) tile;
				String s = "Steam";
				gui.drawString(fontRendererObj, s, gui.xStart + pos.x, gui.yStart + pos.y, RenderUtil.fromRGB(255,255,255));
				s = String.format("%.1f",g.steamProductionM)+"mB/t";
				gui.drawString(fontRendererObj, s, gui.xStart + pos.x, gui.yStart + pos.y+12, RenderUtil.fromRGB(255,255,255));
				s = "Energy";
				gui.drawString(fontRendererObj, s, gui.xStart + pos.x, gui.yStart + pos.y+26, RenderUtil.fromRGB(255,255,255));
				s = (int)(g.electricProductionM)+"W";
				gui.drawString(fontRendererObj, s, gui.xStart + pos.x, gui.yStart + pos.y+38, RenderUtil.fromRGB(255,255,255));
			}
		}

		@Override
		public void onClick(int mx, int my, int buttom, GuiBasic gui) {}

		@Override
		public boolean onKey(int n, char key, GuiBasic gui) {return false;}

		@Override
		public void renderTop(int mx, int my, TileEntity tile, GuiBasic gui) {}
	}

}
