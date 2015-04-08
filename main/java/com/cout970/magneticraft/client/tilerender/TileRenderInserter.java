package com.cout970.magneticraft.client.tilerender;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.client.model.ModelInserter;
import com.cout970.magneticraft.tileentity.TileInserter;
import com.cout970.magneticraft.util.RenderUtil;

public class TileRenderInserter extends TileEntitySpecialRenderer{

	private ModelInserter model;
	
	public TileRenderInserter(){
		model = new ModelInserter();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity t, double x, double y, double z, float frames) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GL11.glRotatef(180, 0, 0, 1);
		TileInserter tile = (TileInserter) t;
		if(tile.getDir() == MgDirection.NORTH){
			GL11.glRotatef(180, 0, 1, 0);
		}else if(tile.getDir() == MgDirection.WEST){
			GL11.glRotatef(90, 0, 1, 0);
		}else if(tile.getDir() == MgDirection.EAST){
			GL11.glRotatef(-90, 0, 1, 0);
		}
		
		RenderUtil.bindTexture(ModelTextures.INSERTER);
		model.renderStatic(0.0625f);
		float ti = (tile.counter)*20f/tile.speed;
		float a,b,c = tile.getInv().getStackInSlot(0) == null ? 0: 50;
		a = (float) (25*Math.sin(Math.toRadians(ti*36/4-90)));
		b = (float) (125*Math.sin(Math.toRadians(ti*36/4f-90)));
		GL11.glRotatef(0, 0, 1, 0);
		model.renderDynamic(0.0625f, a, b, c);
		GL11.glPopMatrix();
	}
}
