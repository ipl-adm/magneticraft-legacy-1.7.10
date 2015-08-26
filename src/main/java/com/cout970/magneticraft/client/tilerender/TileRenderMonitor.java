package com.cout970.magneticraft.client.tilerender;

import com.cout970.magneticraft.client.model.ModelMonitor;
import com.cout970.magneticraft.util.RenderUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class TileRenderMonitor extends TileEntitySpecialRenderer {

    private ModelMonitor model;

    public TileRenderMonitor() {
        model = new ModelMonitor();
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y,
                                   double z, float scale) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glColor4f(1, 1, 1, 1);
        GL11.glRotatef(180, 0, 0, 1);
        GL11.glRotatef(-90, 0, 1, 0);
        if (te.getBlockMetadata() == 2) {
            GL11.glRotatef(90, 0, 1, 0);
        } else if (te.getBlockMetadata() == 3) {
            GL11.glRotatef(-90, 0, 1, 0);
        } else if (te.getBlockMetadata() == 5) {
            GL11.glRotatef(180, 0, 1, 0);
        }
        RenderUtil.bindTexture(ModelTextures.MONITOR);
        model.renderStatic(0.0625f);
        GL11.glPopMatrix();
    }
}