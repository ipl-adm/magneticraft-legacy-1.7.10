package com.cout970.magneticraft.client.tilerender;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.client.model.ModelSprinkler;
import com.cout970.magneticraft.tileentity.TileSprinkler;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class TileRenderSprinkler extends TileEntitySpecialRenderer {
    private ModelSprinkler model;

    public TileRenderSprinkler() {
        model = new ModelSprinkler();
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partTicks) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5f, (float) y - 0.5f, (float) z + 0.5f);
        GL11.glColor4f(1, 1, 1, 1);
        TileSprinkler tile = (TileSprinkler) te;

        if (tile.getDir() == MgDirection.UP) {
            GL11.glRotatef(180, 0, 0, 1);
            GL11.glTranslatef(0, -2, 0);
        }

        bindTexture(ModelTextures.SPRINKLER);
        model.renderStatic();
        float rotation = (float) (tile.rotate(te.getWorldObj().getTotalWorldTime() + partTicks) / 500f * Math.PI);
        if (te.getBlockMetadata() == 1) {
            rotation = -rotation;
        }
        model.renderDynamic(rotation);
        GL11.glPopMatrix();
    }
}
