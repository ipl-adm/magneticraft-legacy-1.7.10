package com.cout970.magneticraft.client.tilerender;

import com.cout970.magneticraft.client.model.ModelCombustionEngine;
import com.cout970.magneticraft.util.RenderUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class TileRenderCombustionEngine extends TileEntitySpecialRenderer {

    private ModelCombustionEngine model;

    public TileRenderCombustionEngine() {
        model = new ModelCombustionEngine();
    }

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float frames) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glRotatef(180, 0, 0, 1);

        if (tile.getBlockMetadata() % 6 == 3) {
            GL11.glRotatef(180, 0, 1, 0);
        } else if (tile.getBlockMetadata() % 6 == 4) {
            GL11.glRotatef(-90, 0, 1, 0);
        } else if (tile.getBlockMetadata() % 6 == 5) {
            GL11.glRotatef(90, 0, 1, 0);
        }

        RenderUtil.bindTexture(ModelTextures.COMBUSTION_ENGINE);
        model.renderStatic(0.0625f);
        GL11.glPopMatrix();
    }
}
