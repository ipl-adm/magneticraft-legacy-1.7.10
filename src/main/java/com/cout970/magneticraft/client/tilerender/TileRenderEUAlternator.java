package com.cout970.magneticraft.client.tilerender;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.client.model.ModelAlternator;
import com.cout970.magneticraft.tileentity.TileEUAlternator;
import com.cout970.magneticraft.util.RenderUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class TileRenderEUAlternator extends TileEntitySpecialRenderer {

    private ModelAlternator model;

    public TileRenderEUAlternator() {
        model = new ModelAlternator();
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y,
                                   double z, float scale) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glColor4f(1, 1, 1, 1);
        GL11.glRotatef(180, 0, 0, 1);
        TileEUAlternator tile = (TileEUAlternator) te;

        if (tile.getDirection() == MgDirection.NORTH) {
            GL11.glRotatef(180, 0, 1, 0);
        } else if (tile.getDirection() == MgDirection.WEST) {
            GL11.glRotatef(90, 0, 1, 0);
        } else if (tile.getDirection() == MgDirection.EAST) {
            GL11.glRotatef(-90, 0, 1, 0);
        }

        RenderUtil.bindTexture(ModelTextures.EU_ALTERNATOR);
        model.renderStatic(0.0625f);
        GL11.glPopMatrix();
    }
}
