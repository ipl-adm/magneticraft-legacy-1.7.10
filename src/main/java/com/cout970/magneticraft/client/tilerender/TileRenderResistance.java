package com.cout970.magneticraft.client.tilerender;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.client.model.ModelResistance;
import com.cout970.magneticraft.tileentity.TileResistance;
import com.cout970.magneticraft.util.RenderUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class TileRenderResistance extends TileEntitySpecialRenderer {

    private ModelResistance model;

    public TileRenderResistance() {
        model = new ModelResistance();
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y,
                                   double z, float scale) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glColor4f(1, 1, 1, 1);
        GL11.glRotatef(180, 0, 0, 1);
        TileResistance tile = (TileResistance) te;

        if (tile.getDirection() == MgDirection.NORTH) {
            GL11.glRotatef(180, 0, 1, 0);
        } else if (tile.getDirection() == MgDirection.WEST) {
            GL11.glRotatef(90, 0, 1, 0);
        } else if (tile.getDirection() == MgDirection.EAST) {
            GL11.glRotatef(-90, 0, 1, 0);
        }

        RenderUtil.bindTexture(ModelTextures.RESISTANCE);
        model.renderDynamic(0.0625f, 0);
        VecInt color = TileResistance.color_array[tile.line1];
        GL11.glColor3ub((byte) color.getX(), (byte) color.getY(), (byte) color.getZ());
        model.renderDynamic(0.0625f, 1);
        color = TileResistance.color_array[tile.line2];
        GL11.glColor3ub((byte) color.getX(), (byte) color.getY(), (byte) color.getZ());
        model.renderDynamic(0.0625f, 2);
        color = TileResistance.color_array[tile.line3];
        GL11.glColor3ub((byte) color.getX(), (byte) color.getY(), (byte) color.getZ());
        model.renderDynamic(0.0625f, 3);
        GL11.glPopMatrix();
    }
}
