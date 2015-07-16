package com.cout970.magneticraft.client.tilerender;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import com.cout970.magneticraft.client.model.ModelRefinery;
import com.cout970.magneticraft.client.model.ModelRefineryTank;
import com.cout970.magneticraft.client.model.ModelSifter;
import com.cout970.magneticraft.tileentity.TileRefinery;
import com.cout970.magneticraft.tileentity.TileRefineryTank;
import com.cout970.magneticraft.tileentity.TileSifter;
import com.cout970.magneticraft.util.RenderUtil;
import com.cout970.magneticraft.util.multiblock.MB_Register;
import com.cout970.magneticraft.util.multiblock.Multiblock;

public class TileRenderSifter extends TileEntitySpecialRenderer{

	private ModelSifter model;
	
	public TileRenderSifter(){
		model = new ModelSifter();
	}

	@Override
	public void renderTileEntityAt(TileEntity t, double x, double y, double z, float frames) {
		TileSifter tile = (TileSifter) t;
		if(tile.isActive()){
			GL11.glPushMatrix();
			GL11.glColor4f(1, 1, 1, 1);
			bindTexture(ModelTextures.SIFTER);
			GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
			GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
			switch(tile.getDirection()){
			case EAST:
				GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
				break;
			case SOUTH:
				GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
				break;
			case NORTH: break;
			case WEST:
				GL11.glRotatef(-90F, 0.0F, 1.0F, 0.0F);
				break;
				default: break;
			}
			model.renderStatic(0.0625f);
			GL11.glPopMatrix();
		}else if(tile.drawCounter > 0){
			Multiblock mb = MB_Register.getMBbyID(MB_Register.ID_SIFTER);
			RenderUtil.renderMultiblock(x,y,z,tile,t,mb);
		}
	}
}
