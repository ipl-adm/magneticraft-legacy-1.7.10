package com.cout970.magneticraft.client.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.ManagerNetwork;
import com.cout970.magneticraft.client.gui.component.CompBackground;
import com.cout970.magneticraft.client.gui.component.CompButtonRedstoneControl;
import com.cout970.magneticraft.client.gui.component.CompEnergyBarMediumVoltage;
import com.cout970.magneticraft.client.gui.component.CompEnergyConsumption;
import com.cout970.magneticraft.client.gui.component.CompGenericBar;
import com.cout970.magneticraft.client.gui.component.GuiPoint;
import com.cout970.magneticraft.client.gui.component.IGuiComp;
import com.cout970.magneticraft.messages.MessageGuiClick;
import com.cout970.magneticraft.tileentity.TileMiner;
import com.cout970.magneticraft.tileentity.TileMiner.WorkState;
import com.cout970.magneticraft.util.Log;
import com.cout970.magneticraft.util.RenderUtil;

public class GuiMiner extends GuiBasic{

	public GuiMiner(Container c, TileEntity tile) {
		super(c, tile);
	}

	@Override
	public void initComponenets() {
		comp.add(new CompBackground(new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/miner.png")));
		comp.add(new CompEnergyBarMediumVoltage(new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/energybar2.png"),new GuiPoint(23,16),((TileMiner)tile).capacity));
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
				float prod = c.hole/((float)c.dim*c.dim);
				int scale = (int) (44*prod);
				gui.mc.renderEngine.bindTexture(texture);
				RenderUtil.drawTexturedModalRectScaled(gui.xStart+pos.x, gui.yStart+pos.y+(44-scale), 0, 44-scale, 6, scale, 12, 45);
				gui.drawString(getFontRenderer(), c.dim+"x"+c.dim, gui.xStart+60, gui.yStart+40, RenderUtil.fromRGB(255, 255, 255));

				for(int i = 0; i < c.counter-1; i++){
					drawLine(gui.xStart+90+i, gui.yStart+70-c.graf[i]*60, gui.xStart+91+i, gui.yStart+70-c.graf[i+1]*60, RenderUtil.fromRGB(255, 255, 255));//-c.graf[i+1]*100
				}
			}
		}

		private void drawLine(int i, float j, int x, float y, int color) {
			Tessellator t = Tessellator.instance;
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			GL11.glShadeModel(GL11.GL_SMOOTH);
			GL11.glColor3ub((byte)color, (byte)(color >> 8), (byte)(color >> 16));
			GL11.glLineWidth(2);
			t.startDrawing(GL11.GL_LINES);
			t.addVertex(i, j, 0);
			t.addVertex(x, y, 0);
			t.addVertex(i, j, 0);
			t.addVertex(x, y, 0);
			t.draw();
			GL11.glShadeModel(GL11.GL_FLAT);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
		}

		@Override
		public void onClick(int mx, int my, int buttom, GuiBasic gui) {
			if(gui.isIn(mx, my, gui.xStart+61, gui.yStart+56, 9, 9)){
				MessageGuiClick msg = new MessageGuiClick(gui.tile, 0, isShiftKeyDown() ? 10 : 1);
				ManagerNetwork.INSTANCE.sendToServer(msg);
			}else if(gui.isIn(mx, my, gui.xStart+72, gui.yStart+56, 9, 9)){
				MessageGuiClick msg = new MessageGuiClick(gui.tile, 1, isShiftKeyDown() ? 10 : 1);
				ManagerNetwork.INSTANCE.sendToServer(msg);
			}
		}

		@Override
		public boolean onKey(int n, char key, GuiBasic gui) {return false;}

		@Override
		public void renderTop(int mx, int my, TileEntity tile, GuiBasic gui) {
			if(tile instanceof TileMiner){
				TileMiner c = (TileMiner) tile;
				if(gui.isIn(mx, my, gui.xStart+pos.x, gui.yStart+pos.y, 6, 44)){
					List<String> data = new ArrayList<String>();
					float prod = c.hole*100/(c.dim*c.dim);
					data.add("Mined "+((int)prod)+"%");
					gui.drawHoveringText2(data, mx-gui.xStart, my-gui.yStart);
					RenderHelper.enableGUIStandardItemLighting();
				}
			}
		}
	}

}
