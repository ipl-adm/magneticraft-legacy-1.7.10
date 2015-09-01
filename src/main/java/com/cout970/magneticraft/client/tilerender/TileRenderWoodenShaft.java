package com.cout970.magneticraft.client.tilerender;

import com.cout970.magneticraft.client.model.ModelShaft;
import com.cout970.magneticraft.tileentity.TileWoodenShaft;
import com.cout970.magneticraft.util.RenderUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class TileRenderWoodenShaft extends TileEntitySpecialRenderer {

    private ModelShaft model;

    public TileRenderWoodenShaft() {
        model = new ModelShaft();
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y,
                                   double z, float scale) {
        TileWoodenShaft tile = (TileWoodenShaft) te;
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glColor4f(1, 1, 1, 1);
        GL11.glRotatef(180, 0, 0, 1);
        switch (tile.getDirection()) {
            case NORTH:
            case SOUTH:
                GL11.glRotatef(90, 1, 0, 0);
                GL11.glTranslatef(0, -1, -1);
                break;
            case EAST:
            case WEST:
                GL11.glRotatef(90, 0, 0, 1);
                GL11.glTranslatef(1, -1, 0);
                break;
            default:
                break;
        }
        float f = (float) (tile.kinetic.getRotation() + (tile.kinetic.getSpeed() / 60) * tile.kinetic.getDelta());
        if (f > 1000) f %= 1000;
        tile.kinetic.setRotation(f);
        RenderUtil.bindTexture(ModelTextures.WOODEN_SHAFT);
        model.renderDynamic(0.0625f, (float) Math.toRadians(tile.kinetic.getRotation() * 0.36));
        GL11.glPopMatrix();
    }
}