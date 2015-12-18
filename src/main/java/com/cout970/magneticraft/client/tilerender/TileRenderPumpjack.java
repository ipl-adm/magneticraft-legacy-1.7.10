package com.cout970.magneticraft.client.tilerender;

import org.lwjgl.opengl.GL11;

import com.cout970.magneticraft.client.model.ModelPumpjack;
import com.cout970.magneticraft.tileentity.TilePumpJack;
import com.cout970.magneticraft.util.RenderUtil;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class TileRenderPumpjack extends TileEntitySpecialRenderer {

	private ModelPumpjack model;

    public TileRenderPumpjack() {
    	model = new ModelPumpjack();
    }

    @Override
    public void renderTileEntityAt(TileEntity t, double x, double y, double z, float partialTicks) {
        TilePumpJack tile = (TilePumpJack) t;

        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y - 0.5F, (float) z + 0.5F);
        GL11.glRotatef(180, 1, 0, 0);
        GL11.glTranslatef(0, -2, 0);
        
        switch(tile.getOrientation()){
        case SOUTH:
        	GL11.glRotatef(180, 0, 1, 0);
        	break;
        case EAST:
        	 GL11.glRotatef(90, 0, 1, 0);
        	 break;
        case WEST:
        	GL11.glRotatef(-90, 0, 1, 0);
        	break;
		default:
			break;
        }
        
        GL11.glRotatef(90, 0, 1, 0);
        GL11.glTranslated(-2, 0, 0);
        
        if (tile.isActive())
            tile.m += 0.25f * (tile.getDelta(partialTicks));
        if (tile.m > 1000) tile.m -= 1000;
        if (tile.m > 10000) tile.m = 0;

        RenderUtil.bindTexture(ModelTextures.PUMPJACK);
        model.render(0.0625f, tile.m, tile.getConnections());
        GL11.glPopMatrix();
    }
}