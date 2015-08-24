package com.cout970.magneticraft.client.tilerender;

import com.cout970.magneticraft.client.model.ModelGrindingMill;
import com.cout970.magneticraft.tileentity.TileGrindingMill;
import com.cout970.magneticraft.util.RenderUtil;
import com.cout970.magneticraft.util.multiblock.MB_Register;
import com.cout970.magneticraft.util.multiblock.Multiblock;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public class TileRenderGrindingMill extends TileEntitySpecialRenderer {

    private ModelGrindingMill model;

    public TileRenderGrindingMill() {
        model = new ModelGrindingMill();
    }

    @Override
    public void renderTileEntityAt(TileEntity t, double x, double y, double z, float frames) {
        TileGrindingMill tile = (TileGrindingMill) t;
        if (tile.getBlockMetadata() < 6) {
            if (tile.drawCounter > 0) {
                GL11.glColor4f(1, 1, 1, 1f);
                Multiblock mb = MB_Register.getMBbyID(MB_Register.ID_GRINDING_MILL);
                RenderUtil.renderMultiblock(x, y, z, tile, t, mb);
            }
        } else {
            glPushMatrix();
            glTranslated(x, y, z);
            glRotatef(180, 1, 0, 0);
            glRotatef(90, 0, 1, 0);

            switch (tile.getDirection()) {
                case NORTH:
                    GL11.glRotatef(-90, 0, 1, 0);
                    GL11.glTranslated(-0.5, 0.5, -2.5);
                    break;
                case SOUTH:
                    GL11.glRotatef(90, 0, 1, 0);
                    GL11.glTranslated(-1.5, 0.5, -1.5);
                    break;
                case WEST:
                    GL11.glRotatef(180, 0, 1, 0);
                    GL11.glTranslated(-1.5, 0.5, -2.5);
                    break;
                case EAST:
                    GL11.glTranslated(-0.5, 0.5, -1.5);
                    break;
                default:
                    break;
            }
            if (tile.kinetic != null) {
                float f = (float) (tile.kinetic.getRotation() + (tile.kinetic.getSpeed() / 60) * tile.kinetic.getDelta());
                if (f > 1000) f %= 1000;
                tile.kinetic.setRotation(f);
            }
            RenderUtil.bindTexture(ModelTextures.GRINDING_MILL);
            model.renderStatic(0.0625f);
            model.renderDynamic(0.0625f, (float) Math.toRadians(tile.kinetic == null ? 0 : tile.kinetic.getRotation() * 0.36));
            glPopMatrix();
        }
    }
}
