package com.cout970.magneticraft.client.tilerender;

import com.cout970.magneticraft.client.model.ModelGrinder;
import com.cout970.magneticraft.tileentity.multiblock.controllers.TileGrinder;
import com.cout970.magneticraft.util.RenderUtil;
import com.cout970.magneticraft.util.multiblock.MB_Register;
import com.cout970.magneticraft.util.multiblock.Multiblock;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public class TileRenderGrinder extends TileEntitySpecialRenderer {

    private ModelGrinder model;

    public TileRenderGrinder() {
        model = new ModelGrinder();
    }

    @Override
    public void renderTileEntityAt(TileEntity t, double x, double y, double z, float frames) {
        TileGrinder tile = (TileGrinder) t;
        if (tile.getBlockMetadata() < 6) {
            if (tile.drawCounter > 0) {
                GL11.glColor4f(1, 1, 1, 1f);
                Multiblock mb = MB_Register.getMBbyID(MB_Register.ID_GRINDER);
                RenderUtil.renderMultiblock(x, y, z, tile, t, mb);
            }
        } else {
            glPushMatrix();
            glTranslated(x, y, z);
            glRotatef(180, 1, 0, 0);
            glRotatef(-90, 0, 1, 0);
            RenderUtil.applyRotation(tile.getDirection());

            if (tile.isWorking()) {
                tile.rotation += (tile.getDelta() / 1E6) * 0.5;
                if (tile.rotation > 1000) tile.rotation %= 1000;
            } else {
                if (tile.rotation < 1000) {
                    tile.rotation += (tile.getDelta() / 1E6) * 0.5;
                    if (tile.rotation > 1000) {
                        tile.rotation = 1000;
                    }
                }
            }
            RenderUtil.bindTexture(ModelTextures.GRINDER);
            model.renderStatic(0.0625f);
            model.renderDynamic(0.0625f, (float) (tile.rotation * Math.PI * 2 / 1000f));
            glPopMatrix();
        }
    }
}
