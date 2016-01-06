package com.cout970.magneticraft.client.tilerender;

import com.cout970.magneticraft.client.model.ModelStirlingGenerator;
import com.cout970.magneticraft.tileentity.multiblock.controllers.TileStirlingGenerator;
import com.cout970.magneticraft.util.RenderUtil;
import com.cout970.magneticraft.util.multiblock.MB_Register;
import com.cout970.magneticraft.util.multiblock.Multiblock;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public class TileRenderStirling extends TileEntitySpecialRenderer {

    public ModelStirlingGenerator model = new ModelStirlingGenerator();

    @Override
    public void renderTileEntityAt(TileEntity t, double x, double y, double z, float frames) {
        TileStirlingGenerator tile = (TileStirlingGenerator) t;
        if (tile.getBlockMetadata() < 6) {
            if (tile.drawCounter > 0) {
                GL11.glColor4f(1, 1, 1, 1f);
                Multiblock mb = MB_Register.getMBbyID(MB_Register.ID_STIRLING);
                RenderUtil.renderMultiblock(x, y, z, tile, t, mb);
            }
        } else {
            glPushMatrix();
            glTranslated(x, y, z);
            glRotatef(180, 1, 0, 0);
            glRotatef(-90, 0, 1, 0);

            RenderUtil.applyRotation(tile.getDirection());

            if (tile.isWorking()) RenderUtil.bindTexture(ModelTextures.STIRLING_ON);
            else RenderUtil.bindTexture(ModelTextures.STIRLING_OFF);
            model.renderStatic(0.0625f);
            glPopMatrix();
        }
    }
}
