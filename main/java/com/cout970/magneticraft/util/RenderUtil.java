package com.cout970.magneticraft.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import codechicken.lib.vec.Vector3;

import com.cout970.magneticraft.Magneticraft;

public class RenderUtil {

	public static double zLevel;
	public static ResourceLocation MISC_ICONS = new ResourceLocation(Magneticraft.NAME.toLowerCase(),"textures/gui/misc.png");
	
	public static void drawTexturedModalRectScaled(int x, int y, int u, int v, int w, int h, int mx, int my)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        f =  1f/mx;
        f1 = 1f/my;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)(x + 0), (double)(y + h), (double)zLevel, (double)((float)(u + 0) * f), (double)((float)(v + h) * f1));
        tessellator.addVertexWithUV((double)(x + w), (double)(y + h), (double)zLevel, (double)((float)(u + w) * f), (double)((float)(v + h) * f1));
        tessellator.addVertexWithUV((double)(x + w), (double)(y + 0), (double)zLevel, (double)((float)(u + w) * f), (double)((float)(v + 0) * f1));
        tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0), (double)zLevel, (double)((float)(u + 0) * f), (double)((float)(v + 0) * f1));
        tessellator.draw();
    }

	public static void bindTexture(ResourceLocation r) {
		Minecraft.getMinecraft().renderEngine.bindTexture(r);
	}
	
	public static int getTexture(boolean[] b) {
		int count = 0;
		for(boolean c : b)if(c){count++;}
		if(count != 1)return -1;
		if(b.length == 6)return b[0]? 0 : b[1]? 1 : b[2]? 5: b[3]? 3: b[4]?4: b[5]? 2  : -1;
		return b[0]? 4 : b[1]? 2 : b[2]? 1: b[3]? 3: -1;
	}

	public static int fromRGB(int r, int g, int b) {
		int color = 0;
		color += r*65536;
		color += g * 256;
		color += b;
		return color;
	}
	
	public static void drawString(String string, int x, int y, int par4, boolean centered) {
    	FontRenderer f = Minecraft.getMinecraft().fontRenderer;
    	if(centered){
    		f.drawString(string, x-f.getStringWidth(string)/2, y-f.getStringWidth("1")/2, par4);
    	}else{
    		f.drawString(string, x, y, par4);
    	}
	}

	public static int getTexture(byte b) {
		byte count = 0;
		for(byte c = 0; c<6 ;c++)
			if((b & (1 << c)) > 0){count++;}
		if(count != 1)return -1;
		if((b & 1) > 0)return 0;
		if((b & 2) > 0)return 1;
		if((b & 4) > 0)return 5;
		if((b & 8) > 0)return 3;
		if((b & 16) > 0)return 4;
		if((b & 32) > 0)return 2;
		return -1;
	}

	public static Vector3 getHeatColor(double t) {
		double r = Math.min(0.95, t/1000);
		return new Vector3(1, 1-r, 1-r);
	}
}
