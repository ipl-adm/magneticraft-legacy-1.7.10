package com.cout970.magneticraft.client.gui;

import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.client.gui.component.CompEnergyBar;
import com.cout970.magneticraft.client.gui.component.CompStorageBar;
import com.cout970.magneticraft.client.gui.component.GuiPoint;
import com.cout970.magneticraft.client.gui.component.IGuiComp;
import com.cout970.magneticraft.tileentity.TileBasicGenerator;
import com.cout970.magneticraft.tileentity.TileCombustionEngine;
import com.cout970.magneticraft.tileentity.TileDroidRED;
import com.cout970.magneticraft.util.RenderUtil;

public class GuiDroidRED extends GuiBasic {

	public GuiDroidRED(Container c, TileEntity tile) {
		super(c, tile);
		xSize = 350;
		ySize = 320;
	}

	@Override
	public void initComponenets() {
		comp.add(new CompBackground(new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/droid_red.png")));
		comp.add(new CompEnergyBar(new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/energybar.png"),new GuiPoint(10,238), ((TileDroidRED)tile).cond));
		comp.add(new CompStorageBar(new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/energybar.png"),new GuiPoint(19,238), ((TileDroidRED)tile).cond));
	}
	
	public class CompBackground implements IGuiComp{

		public ResourceLocation texture;
		
		public CompBackground(ResourceLocation tex){
			texture = tex;
		}
		
		@Override
		public void render(int mx, int my, TileEntity tile, GuiBasic gui) {
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			gui.mc.renderEngine.bindTexture(texture);
			RenderUtil.drawTexturedModalRectScaled(gui.xStart, gui.yStart, 0, 0, 350, 320, 350, 350);
		}

		@Override
		public void onClick(int mx, int my, int buttom, GuiBasic gui) {}

		@Override
		public boolean onKey(int n, char key, GuiBasic gui) {return false;}

		@Override
		public void renderTop(int mx, int my, TileEntity tile, GuiBasic gui) {}

	}

}
