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
import com.cout970.magneticraft.client.gui.component.CompEnergyBarMediumVoltage;
import com.cout970.magneticraft.client.gui.component.CompEnergyConsumption;
import com.cout970.magneticraft.client.gui.component.CompGenericBar;
import com.cout970.magneticraft.client.gui.component.GuiPoint;
import com.cout970.magneticraft.client.gui.component.IGuiComp;
import com.cout970.magneticraft.tileentity.TileMiner;
import com.cout970.magneticraft.tileentity.TileMiner.WorkState;
import com.cout970.magneticraft.util.RenderUtil;

public class GuiMiner extends GuiBasic{

	public GuiMiner(Container c, TileEntity tile) {
		super(c, tile);
	}

	@Override
	public void initComponenets() {
		comp.add(new CompBackground(new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/miner.png")));
		comp.add(new CompEnergyBarMediumVoltage(new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/energybar2.png"),new GuiPoint(23,16)));
		comp.add(new CompEnergyConsumption(new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/consumptionbar.png"),new GuiPoint(32,19)));
		comp.add(new CompGenericBar(new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/efficiencybar.png"),new GuiPoint(41,19)));
		comp.add(new CompMiningBar(new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/windbar.png"),new GuiPoint(50,19)));
		comp.add(new CompButtonRedstoneControl(new GuiPoint(150, 8)));
	}
	
	
	public class CompMiningBar implements IGuiComp{

		public ResourceLocation texture;
		public GuiPoint pos;

		public CompMiningBar(ResourceLocation tex, GuiPoint p){
			texture = tex;
			pos = p;
		}

		@Override
		public void render(int mx, int my, TileEntity tile, GuiBasic gui) {
			if(tile instanceof TileMiner){
				TileMiner c = (TileMiner) tile;
				float prod = Math.max(0, c.coolDown/TileMiner.MINING_COST_PER_BLOCK);
				if(c.state != WorkState.WORKING)prod = 1;
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
			if(tile instanceof TileMiner){
				TileMiner c = (TileMiner) tile;
				if(gui.isIn(mx, my, gui.xStart+pos.x, gui.yStart+pos.y, 6, 44)){
					List<String> data = new ArrayList<String>();
					float prod = Math.max(0, c.coolDown*100/TileMiner.MINING_COST_PER_BLOCK);
					if(c.state != WorkState.WORKING)prod = 0;
					data.add("Mining "+((int)prod)+"%");
					gui.drawHoveringText2(data, mx-gui.xStart, my-gui.yStart);
					RenderHelper.enableGUIStandardItemLighting();
				}
			}
		}
	}

}
