package com.cout970.magneticraft.client.tilerender;

import codechicken.lib.vec.Vector3;
import com.cout970.magneticraft.client.model.ModelHeatCable;
import com.cout970.magneticraft.parts.heat.PartHeatCable;
import com.cout970.magneticraft.util.RenderUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class TileRenderHeatCable extends TileEntitySpecialRenderer {

    private ModelHeatCable model;

    public TileRenderHeatCable() {
        model = new ModelHeatCable();
    }

    @Override
    public void renderTileEntityAt(TileEntity t, double x, double y, double z, float frames) {
//		GL11.glPushMatrix();
//		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
//		GL11.glRotatef(180, 0, 0, 1);
//		TileHeatCable tile = (TileHeatCable) t;
//		float r = (float) Math.min(1, tile.heat.getTemperature()/1000);
//		Vector3 k = RenderUtil.getHeatColor(tile.heat.getTemperature());
//		GL11.glColor4d(k.x, k.y, k.z, 1);
//		RenderUtil.bindTexture(ModelTextures.HEAT_CABLE);
//		model.renderStatic(0.0625f);
//		model.renderDynamic(0.0625f, tile.conMask);
//		GL11.glPopMatrix();
    }

    public void render(PartHeatCable tile, Vector3 pos) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) pos.x + 0.5F, (float) pos.y + 1.5F, (float) pos.z + 0.5F);
        GL11.glRotatef(180, 0, 0, 1);
        Vector3 k = RenderUtil.getHeatColor(tile.heat.getTemperature());
        GL11.glColor4d(k.x, k.y, k.z, 1);
        RenderUtil.bindTexture(ModelTextures.HEAT_CABLE);
        model.renderStatic(0.0625f);
        model.renderDynamic(0.0625f, tile.conMask);
        GL11.glPopMatrix();
    }
}
