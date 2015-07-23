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
import com.cout970.magneticraft.client.gui.component.CompHeatBar;
import com.cout970.magneticraft.client.gui.component.GuiPoint;
import com.cout970.magneticraft.client.gui.component.IGuiComp;
import com.cout970.magneticraft.tileentity.TileGeothermalPump;
import com.cout970.magneticraft.util.RenderUtil;

public class GuiGeothermalPump extends GuiBasic{

	public GuiGeothermalPump(Container c, TileEntity tile) {
		super(c, tile);
	}

	@Override
	public void initComponenets() {
		comp.add(new CompBackground(new ResourceLocation("magneticraft:textures/gui/geothermal.png")));
		comp.add(new CompHeatBar(new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/heatbar.png"), new GuiPoint(107, 20), ((TileGeothermalPump)tile).heat));
		comp.add(new CompLavaStorage(new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/lavabar.png"), new GuiPoint(98, 20)));
		comp.add(new CompButtonRedstoneControl(new GuiPoint(150, 8)));
	}
	
	public class CompLavaStorage implements IGuiComp{

		public ResourceLocation texture;
		public GuiPoint pos;

		public CompLavaStorage(ResourceLocation tex, GuiPoint p){
			texture = tex;
			pos = p;
		}

		@Override
		public void render(int mx, int my, TileEntity tile, GuiBasic gui) {
			if(tile instanceof TileGeothermalPump){
				TileGeothermalPump c = (TileGeothermalPump) tile;
				float prod = c.buffer/20000f;
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
			if(tile instanceof TileGeothermalPump){
				TileGeothermalPump c = (TileGeothermalPump) tile;
				if(gui.isIn(mx, my, gui.xStart+pos.x, gui.yStart+pos.y, 6, 44)){
					List<String> data = new ArrayList<String>();
					float prod = c.buffer/20000f;
					data.add("Buffer "+(int)(prod*1000)+" mB");
					gui.drawHoveringText2(data, mx-gui.xStart, my-gui.yStart);
					RenderHelper.enableGUIStandardItemLighting();
				}
			}
		}
	}
}
