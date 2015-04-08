package com.cout970.magneticraft.client.tilerender;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.client.model.ModelRefinery;
import com.cout970.magneticraft.client.model.ModelRefineryTank;
import com.cout970.magneticraft.tileentity.TileRefinery;

public class TileRenderRefinery extends TileEntitySpecialRenderer{

	private ModelRefinery model;
	private ModelRefineryTank model2;
	
	public TileRenderRefinery(){
		model = new ModelRefinery();
		model2 = new ModelRefineryTank();
	}

	@Override
	public void renderTileEntityAt(TileEntity t, double x, double y, double z, float frames) {
		TileRefinery tile = (TileRefinery) t;
		if(tile.isActive()){
			GL11.glPushMatrix();
			GL11.glColor4f(1, 1, 1, 1);
			bindTexture(ModelTextures.REFINERY);
			GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
			VecInt vec = tile.getDirectionMeta().opposite().getVecInt();
			GL11.glTranslatef(vec.getX(), vec.getY(), vec.getZ());
			GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(0,-1,0);
			this.model.render(0.0625F);
			GL11.glTranslatef(0,-2,0);
			this.model.render(0.0625F);
			GL11.glTranslatef(0,-1,0);
			this.model.render(0.0625F);
			GL11.glTranslatef(0,-2,0);
			this.model.render(0.0625F);
			GL11.glTranslatef(0,-1,0);
			this.model.render(0.0625F);
			GL11.glTranslatef(0,-2,0);
			this.model.render(0.0625F);
			bindTexture(ModelTextures.REFINERY_TANK);
			if(tile.getDirectionMeta() == MgDirection.NORTH){
			GL11.glRotatef(90, 0.0F, 1.0F, 0.0F);
			}else if(tile.getDirectionMeta() == MgDirection.SOUTH){
				GL11.glRotatef(-90, 0.0F, 1.0F, 0.0F);
			}else if(tile.getDirectionMeta() == MgDirection.EAST){
				GL11.glRotatef(180, 0.0F, 1.0F, 0.0F);
			}
			GL11.glTranslatef(0,6,0);
			this.model2.renderStatic(0.0625F);
			GL11.glTranslatef(0,-3,0);
			this.model2.renderStatic(0.0625F);
			GL11.glTranslatef(0,-3,0);
			this.model2.renderStatic(0.0625F);
			GL11.glPopMatrix();
		}
	}
}
