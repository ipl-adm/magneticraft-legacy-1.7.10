package com.cout970.magneticraft.client.tilerender;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import com.cout970.magneticraft.client.model.ModelReactorControl;
import com.cout970.magneticraft.tileentity.TileReactorControl;
import com.cout970.magneticraft.util.RenderUtil;

public class TileRenderReactorControl extends TileEntitySpecialRenderer{
	
	private ModelReactorControl model;
	
	public TileRenderReactorControl(){
		model = new ModelReactorControl();
	}

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y,
			double z, float scale) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y - 0.5F, (float) z + 0.5F);
		GL11.glColor4f(1, 1, 1, 1);
//		GL11.glRotatef(180, 0, 0, 1);
		RenderUtil.bindTexture(ModelTextures.REACTOR_CONTROL);
		model.renderStatic(0.0625f);
		if(((TileReactorControl)te).is_activate){
			GL11.glTranslatef(0, 1, 0);
			model.renderDynamic(0.0625f);
		}
		GL11.glPopMatrix();
	}
}