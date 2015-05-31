package com.cout970.magneticraft.client.tilerender;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import com.cout970.magneticraft.client.model.ModelElectricalPoleTier1;
import com.cout970.magneticraft.util.RenderUtil;

public class TileRenderElectricPolrTier1 extends TileEntitySpecialRenderer{
	
	private ModelElectricalPoleTier1 model;
	
	public TileRenderElectricPolrTier1(){
		model = new ModelElectricalPoleTier1();
	}

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y - 2.5F, (float) z + 0.5F);
		GL11.glColor4f(1, 1, 1, 1);
		GL11.glRotatef(180, 0, 0, 1);
		
		switch(te.getBlockMetadata()-6){
		case 0:
			GL11.glRotatef(90, 0, 1, 0);
			break;
		case 1:
			GL11.glRotatef(-45, 0, 1, 0);
			break;
		case 2: break;
		case 3:
			GL11.glRotatef(-135, 0, 1, 0);
			break;
		case 4:
			GL11.glRotatef(-90, 0, 1, 0);
			break;
		case 5:
			GL11.glRotatef(-45, 0, 1, 0);
			break;
		case 6: break;
		case 7:
			GL11.glRotatef(45, 0, 1, 0);
			break;
		}
		
		RenderUtil.bindTexture(ModelTextures.POLE_TIER1);
		model.renderStatic(0.0625f);
		GL11.glPopMatrix();
	}
}