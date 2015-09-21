package com.cout970.magneticraft.client.tilerender;

import com.cout970.magneticraft.client.model.ModelOilDistillery;
import com.cout970.magneticraft.tileentity.multiblock.controllers.TileOilDistillery;
import com.cout970.magneticraft.util.RenderUtil;
import com.cout970.magneticraft.util.multiblock.MB_Register;
import com.cout970.magneticraft.util.multiblock.Multiblock;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class TileRenderOilDistillery extends TileEntitySpecialRenderer {

    private ModelOilDistillery model;

    public TileRenderOilDistillery() {
        model = new ModelOilDistillery();
    }

    @Override
    public void renderTileEntityAt(TileEntity t, double x, double y, double z, float frames) {
        TileOilDistillery tile = (TileOilDistillery) t;
        if (tile.isActive()) {
            GL11.glPushMatrix();
            GL11.glColor4f(1, 1, 1, 1);
            bindTexture(ModelTextures.OIL_DISTILLERY);
            GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            switch (tile.getDirection()) {
                case EAST:
                    GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
                    break;
                case SOUTH:
                    GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
                    break;
                case NORTH:
                    break;
                case WEST:
                    GL11.glRotatef(-90F, 0.0F, 1.0F, 0.0F);
                    break;
                default:
                    break;
            }
            GL11.glTranslatef(-1, 0, 0);
            model.renderStatic(0.0625f);
            GL11.glPopMatrix();
        } else if (tile.drawCounter > 0) {
            Multiblock mb = MB_Register.getMBbyID(MB_Register.ID_OIL_DISTILLERY);
            RenderUtil.renderMultiblock(x, y, z, tile, t, mb);
        }
    }
}