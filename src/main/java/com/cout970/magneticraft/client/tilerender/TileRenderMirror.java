package com.cout970.magneticraft.client.tilerender;

import com.cout970.magneticraft.client.model.ModelMirror;
import com.cout970.magneticraft.tileentity.TileMirror;
import com.cout970.magneticraft.util.RenderUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class TileRenderMirror extends TileEntitySpecialRenderer {

    private ModelMirror model;

    public TileRenderMirror() {
        model = new ModelMirror();
    }

    @Override
    public void renderTileEntityAt(TileEntity t, double x, double y, double z, float frames) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glRotatef(180, 0, 0, 1);
        TileMirror tile = (TileMirror) t;
        RenderUtil.bindTexture(ModelTextures.MIRROR);
        model.renderStatic(0.0625f);
        GL11.glRotatef((float) Math.toDegrees(tile.rotation), 0, 1, 0);
        model.renderDynamic(0.0625f, tile.angle);
        GL11.glPopMatrix();
    }
}
