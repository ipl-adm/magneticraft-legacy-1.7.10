package com.cout970.magneticraft.client.tilerender;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import com.cout970.magneticraft.client.model.ModelSteamEngine;
import com.cout970.magneticraft.tileentity.TileSteamEngine;
import com.cout970.magneticraft.util.RenderUtil;

public class TileRenderSteamEngine extends TileEntitySpecialRenderer{
	
	private ModelSteamEngine model;
	
	public TileRenderSteamEngine(){
		model = new ModelSteamEngine();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity t, double x, double y, double z, float frames) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GL11.glRotatef(180, 0, 0, 1);
		TileSteamEngine tile = (TileSteamEngine) t;
		
		if(tile.getBlockMetadata()%6 == 3){
			GL11.glRotatef(180, 0, 1, 0);
		}else if(tile.getBlockMetadata()%6 == 4){
			GL11.glRotatef(-90, 0, 1, 0);
		}else if(tile.getBlockMetadata()%6 == 5){
			GL11.glRotatef(90, 0, 1, 0);
		}
		
		RenderUtil.bindTexture(ModelTextures.STEAM_ENGINE);
		model.renderStatic(0.0625f);
		if(tile.isActive()) tile.animation += tile.getDelta()*0.5f;
		if(tile.animation > 1000)tile.animation -= 1000;
		if(tile.animation > 10000)tile.animation = 0;
		model.renderDynamic(0.0625f,tile.animation);
		GL11.glPopMatrix();
	}
}