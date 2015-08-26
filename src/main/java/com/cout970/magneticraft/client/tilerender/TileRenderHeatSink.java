package com.cout970.magneticraft.client.tilerender;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.client.model.ModelHeatSink;
import com.cout970.magneticraft.tileentity.TileHeatSink;
import com.cout970.magneticraft.util.RenderUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class TileRenderHeatSink extends TileEntitySpecialRenderer {

    private ModelHeatSink model;

    public TileRenderHeatSink() {
        model = new ModelHeatSink();
    }

    @Override
    public void renderTileEntityAt(TileEntity t, double x, double y, double z, float frames) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glRotatef(180, 0, 0, 1);
        TileHeatSink tile = (TileHeatSink) t;

        if (tile.getDirection() == MgDirection.NORTH) {
            GL11.glRotatef(-90, 1, 0, 0);
            GL11.glTranslatef(0, -1, 1);
        } else if (tile.getDirection() == MgDirection.WEST) {
            GL11.glRotatef(-90, 0, 0, 1);
            GL11.glTranslatef(-1, -1, 0);
        } else if (tile.getDirection() == MgDirection.EAST) {
            GL11.glRotatef(90, 0, 0, 1);
            GL11.glTranslatef(1, -1, 0);
        } else if (tile.getDirection() == MgDirection.UP) {
            GL11.glRotatef(180, 1, 0, 0);
            GL11.glTranslatef(0, -2, 0);
        } else if (tile.getDirection() == MgDirection.SOUTH) {
            GL11.glRotatef(90, 1, 0, 0);
            GL11.glTranslatef(0, -1, -1);
        }

        RenderUtil.bindTexture(ModelTextures.HEAT_SINK);
        model.renderStatic(0.0625f);
        GL11.glPopMatrix();
    }
}
