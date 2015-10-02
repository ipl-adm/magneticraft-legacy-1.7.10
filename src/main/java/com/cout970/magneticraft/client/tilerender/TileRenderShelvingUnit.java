package com.cout970.magneticraft.client.tilerender;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.client.model.ModelShelvingUnit;
import com.cout970.magneticraft.tileentity.shelf.TileShelvingUnit;
import com.cout970.magneticraft.util.RenderUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class TileRenderShelvingUnit extends TileEntitySpecialRenderer {
    private ModelShelvingUnit model;

    public TileRenderShelvingUnit() {
        model = new ModelShelvingUnit();
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y,
                                   double z, float scale) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glColor4f(1, 1, 1, 1);
        GL11.glRotatef(180, 0, 0, 1);
        TileShelvingUnit tile = (TileShelvingUnit) te;

        if (tile.getDirection() == MgDirection.NORTH) {
            GL11.glRotatef(180, 0, 1, 0);
        } else if (tile.getDirection() == MgDirection.WEST) {
            GL11.glRotatef(90, 0, 1, 0);
        } else if (tile.getDirection() == MgDirection.EAST) {
            GL11.glRotatef(-90, 0, 1, 0);
        }

        RenderUtil.bindTexture(ModelTextures.SHELVING_UNIT);
        model.renderWithCrates(0.0625f, tile.getCrateCount());
        GL11.glPopMatrix();
    }
}
