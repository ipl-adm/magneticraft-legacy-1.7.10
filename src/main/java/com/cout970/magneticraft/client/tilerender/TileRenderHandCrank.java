package com.cout970.magneticraft.client.tilerender;

import com.cout970.magneticraft.client.model.ModelHandCrank;
import com.cout970.magneticraft.tileentity.TileHandCrankGenerator;
import com.cout970.magneticraft.util.RenderUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class TileRenderHandCrank extends TileEntitySpecialRenderer {

    private ModelHandCrank model;

    public TileRenderHandCrank() {
        model = new ModelHandCrank();
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y,
                                   double z, float scale) {
        TileHandCrankGenerator tile = (TileHandCrankGenerator) te;
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glColor4f(1, 1, 1, 1);
        GL11.glRotatef(180, 0, 0, 1);
        int sign = 1;
        switch (tile.getDirection()) {
            case NORTH:
                GL11.glRotatef(-90, 1, 0, 0);
                GL11.glTranslatef(0, -1, 1);
                sign = -1;
                break;
            case SOUTH:
                GL11.glRotatef(90, 1, 0, 0);
                GL11.glTranslatef(0, -1, -1);
                break;
            case EAST:
                GL11.glRotatef(90, 0, 0, 1);
                GL11.glTranslatef(1, -1, 0);
                break;
            case WEST:
                GL11.glRotatef(-90, 0, 0, 1);
                GL11.glTranslatef(-1, -1, 0);
                sign = -1;
                break;
            case UP:
                GL11.glRotatef(180, 0, 0, 1);
                GL11.glTranslatef(0, -2, 0);
                sign = -1;
                break;
            default:
                break;
        }

        float f = (float) (tile.kinetic.getRotation() + (tile.kinetic.getSpeed() / 60) * tile.kinetic.getDelta());
        if (f > 1000) f %= 1000;
        tile.kinetic.setRotation(f);
        RenderUtil.bindTexture(ModelTextures.HAND_CRANK);
        model.renderDynamic(0.0625f, (float) Math.toRadians(sign * tile.kinetic.getRotation() * 0.36));
        GL11.glPopMatrix();
    }
}