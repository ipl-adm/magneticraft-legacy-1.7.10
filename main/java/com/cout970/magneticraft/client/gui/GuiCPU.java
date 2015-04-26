package com.cout970.magneticraft.client.gui;

import org.lwjgl.opengl.GL11;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.ManagerNetwork;
import com.cout970.magneticraft.client.gui.component.CompBackground;
import com.cout970.magneticraft.client.gui.component.IGuiComp;
import com.cout970.magneticraft.messages.MessageGuiClick;
import com.cout970.magneticraft.tileentity.TileCPU;
import com.cout970.magneticraft.util.Log;
import com.cout970.magneticraft.util.RenderUtil;

import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class GuiCPU extends GuiBasic{

	public GuiCPU(Container c, TileEntity tile) {
		super(c, tile);
		xSize = 248;
		xTam = xSize;
	}

	@Override
	public void initComponenets() {
		comp.add(new CompBackground(new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/cpu.png")));
		comp.add(new CompCPU(new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/cpu.png")));
	}
	
	public class CompCPU implements IGuiComp{

		public ResourceLocation texture;

		public CompCPU(ResourceLocation tex){
			texture = tex;
		}

		@Override
		public void render(int mx, int my, TileEntity tile, GuiBasic gui) {
			TileCPU t = (TileCPU) tile;
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			
			String s = null;
			if(t.getProcesor().isRunning()){
				for(int i = 0;i<4;i++){
					for(int j = 0;j<8;j++){
						s = fill(Integer.toHexString(t.getProcesor().getRegister(i*8+j)).toUpperCase());
						for(int k = 0;k<8;k++){
							gui.drawTexturedModalRect(gui.xStart+7 + i*59 + k*7, gui.yStart+50 + j * 14, getPos(s.charAt(k)), 166, 7, 11);
						}
					}
				}
				gui.drawTexturedModalRect(gui.xStart+231, gui.yStart+8 , 0, 177, 9, 9);
				gui.drawString(getFontRenderer(), "ON", gui.xStart+228, gui.yStart+25, RenderUtil.fromRGB(255, 255, 255));
			}else{
				gui.drawTexturedModalRect(gui.xStart+231, gui.yStart+8 , 9, 177, 9, 9);
				gui.drawString(getFontRenderer(), "OFF", gui.xStart+227, gui.yStart+25, RenderUtil.fromRGB(255, 255, 255));
			}
			gui.drawString(getFontRenderer(), "Start", gui.xStart+22, gui.yStart+7, RenderUtil.fromRGB(255, 255, 255));
			gui.drawString(getFontRenderer(), "Restart", gui.xStart+22, gui.yStart+21, RenderUtil.fromRGB(255, 255, 255));
			gui.drawString(getFontRenderer(), "Shutdown", gui.xStart+22, gui.yStart+35, RenderUtil.fromRGB(255, 255, 255));
		}

		private int getPos(char a) {
			switch(a){
			case '0': return 0;
			case '1': return 7;
			case '2': return 14;
			case '3': return 21;
			case '4': return 28;
			case '5': return 35;
			case '6': return 42;
			case '7': return 49;
			case '8': return 56;
			case '9': return 63;
			case 'A': return 70;
			case 'B': return 77;
			case 'C': return 84;
			case 'D': return 91;
			case 'E': return 98;
			case 'F': return 105;
			}
			return 0;
		}

		private String fill(String s) {
			while(s.length() < 8)s = "0"+s;
			return s;
		}

		@Override
		public void onClick(int mx, int my, int buttom, GuiBasic gui) {
			if(isIn(mx, my, gui.xStart+7, gui.yStart+7, 10, 10)){
				MessageGuiClick msg = new MessageGuiClick(tile, 0, 1);
				ManagerNetwork.INSTANCE.sendToServer(msg);
			}else if(isIn(mx, my, gui.xStart+7, gui.yStart+21, 10, 10)){
				MessageGuiClick msg = new MessageGuiClick(tile, 1, 1);
				ManagerNetwork.INSTANCE.sendToServer(msg);
			}else if(isIn(mx, my, gui.xStart+7, gui.yStart+35, 10, 10)){
				MessageGuiClick msg = new MessageGuiClick(tile, 2, 1);
				ManagerNetwork.INSTANCE.sendToServer(msg);
			}
		}

		@Override
		public boolean onKey(int n, char key, GuiBasic gui) {return false;}

		@Override
		public void renderTop(int mx, int my, TileEntity tile, GuiBasic gui) {}

	}

}
