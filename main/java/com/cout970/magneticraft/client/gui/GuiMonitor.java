package com.cout970.magneticraft.client.gui;

import org.lwjgl.opengl.GL11;

import scala.actors.threadpool.Arrays;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.ManagerNetwork;
import com.cout970.magneticraft.client.gui.component.CompBackground;
import com.cout970.magneticraft.client.gui.component.GuiPoint;
import com.cout970.magneticraft.client.gui.component.IGuiComp;
import com.cout970.magneticraft.messages.MessageUpdateMonitor;
import com.cout970.magneticraft.tileentity.TileMonitor;
import com.cout970.magneticraft.util.Log;
import com.cout970.magneticraft.util.RenderUtil;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class GuiMonitor extends GuiBasic{

	public GuiMonitor(Container c, TileEntity tile) {
		super(c, tile);
		xSize = 350;
		ySize = 230;
	}

	@Override
	public void initComponenets() {
		comp.add(new CompBackground(new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/monitor.png")));
		comp.add(new CompScreen());
	}
	
	public class CompScreen implements IGuiComp{

		private double zLevel;
		
		public CompScreen(){}
		
		@Override
		public void render(int mx, int my, TileEntity tile, GuiBasic gui) {
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			gui.mc.renderEngine.bindTexture(new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/monitor.png"));
			TileMonitor t = (TileMonitor) tile;
			for(int line = 0; line < 50; line++){
				for(int desp = 0;desp < 80; desp++){
					int character = t.text[line*80+desp] & 255;
					if(character != 32){
						drawDoubledRect(gui.xStart + 15 + desp * 4, gui.yStart + 15 + line * 4, 4, 4, 350 + (character & 15) * 8, (character >> 4) * 8, 8, 8);
					}
				}
			}
		}
		
		 public void drawDoubledRect(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8)
		    {
		        float var9 = 0.001953125F;
		        float var10 = 0.00390625F;
		        Tessellator var11 = Tessellator.instance;
		        var11.startDrawingQuads();
		        var11.addVertexWithUV((double)var1, (double)(var2 + var4), (double)this.zLevel, (double)((float)var5 * var9), (double)((float)(var6 + var8) * var10));
		        var11.addVertexWithUV((double)(var1 + var3), (double)(var2 + var4), (double)this.zLevel, (double)((float)(var5 + var7) * var9), (double)((float)(var6 + var8) * var10));
		        var11.addVertexWithUV((double)(var1 + var3), (double)var2, (double)this.zLevel, (double)((float)(var5 + var7) * var9), (double)((float)var6 * var10));
		        var11.addVertexWithUV((double)var1, (double)var2, (double)this.zLevel, (double)((float)var5 * var9), (double)((float)var6 * var10));
		        var11.draw();
		    }

		@Override
		public void onClick(int mx, int my, int buttom, GuiBasic gui) {}

		@Override
		public boolean onKey(int n, char key, GuiBasic gui) {
			if(n == 1) return false;
			if (n == 10) n = 13;
            int shift = 0;
            if (isShiftKeyDown()) shift |= 64;
            if (isCtrlKeyDown()) shift |= 32;
            switch (n)
            {
                case 199:
                    this.sendKey(132 | shift, gui);
                    break;
                case 200:
                    this.sendKey(128 | shift, gui);
                    break;
                case 201:
                case 202:
                case 204:
                case 206:
                case 209:
                default:
                    if (n > 0 && n <= 127)
                    {
                        this.sendKey(n, gui);
                    }
                    break;

                case 203:
                    this.sendKey(130 | shift, gui);
                    break;
                case 205:
                    this.sendKey(131 | shift, gui);
                    break;
                case 207:
                    this.sendKey(133 | shift, gui);
                    break;
                case 208:
                    this.sendKey(129 | shift, gui);
                    break;
                case 210:
                    this.sendKey(134 | shift, gui);
            }
			return true;
		}

		private void sendKey(int key, GuiBasic gui) {
			TileMonitor mon = (TileMonitor) gui.tile;
			mon.keyPresed(key);
			MessageUpdateMonitor msg = new MessageUpdateMonitor(mon);
			ManagerNetwork.INSTANCE.sendToServer(msg);
		}

		@Override
		public void renderTop(int mx, int my, TileEntity tile, GuiBasic gui) {}

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
			RenderUtil.drawTexturedModalRectScaled(gui.xStart, gui.yStart, 0, 0, 350, 230, 512, 256);
		}

		@Override
		public void onClick(int mx, int my, int buttom, GuiBasic gui) {}

		@Override
		public boolean onKey(int n, char key, GuiBasic gui) {return false;}

		@Override
		public void renderTop(int mx, int my, TileEntity tile, GuiBasic gui) {}

	}

}
