package com.cout970.magneticraft.client.tilerender;

import org.lwjgl.opengl.GL11;

import com.cout970.magneticraft.api.pressure.IPressureConductor;
import com.cout970.magneticraft.tileentity.TilePressureTank;
import com.cout970.magneticraft.util.CubeRenderer_Util;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;

public class TileRenderPressureTank extends TileEntitySpecialRenderer {

	public float k = 0.002f;

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale) {
		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
		renderFluid((TilePressureTank) te);
		GL11.glPopAttrib();
		GL11.glPopMatrix();
	}

	private void renderFluid(TilePressureTank te) {
		if (te.getPressureConductor() == null || te.getPressureConductor().length < 1)return;
		
		IPressureConductor cond = te.getPressureConductor()[0];
		if(cond.getFluid() == null)return;
		IIcon i = cond.getFluid().getIcon();
		if (i == null)return;
		
		GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
		bindTexture(TextureMap.locationBlocksTexture);
		double pressure = cond.getPressure();
		
		if (pressure > 0) {
			float visibility = (float) (pressure/(cond.getMaxPressure()/2));
			if(visibility > 1)visibility = 1;
			
			GL11.glColor4f(1, 1, 1, visibility);
			GL11.glTranslatef(-0.5F, -1.5F, -0.5F);
			GL11.glTranslatef(k, 0.01f, k);
			if (te.cubeRenderer == null) {
				te.cubeRenderer = new CubeRenderer_Util();
			}
			te.cubeRenderer.renderBox(i, 1f - k * 2, 0.98f, 1f - k * 2);
		}
	}
}
