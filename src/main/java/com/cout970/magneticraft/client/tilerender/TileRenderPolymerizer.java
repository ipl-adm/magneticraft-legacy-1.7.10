package com.cout970.magneticraft.client.tilerender;

import com.cout970.magneticraft.client.model.ModelPolymerizer;
import com.cout970.magneticraft.tileentity.TileCopperTank;
import com.cout970.magneticraft.tileentity.multiblock.controllers.TilePolymerizer;
import com.cout970.magneticraft.util.CubeRenderer_Util;
import com.cout970.magneticraft.util.RenderUtil;
import com.cout970.magneticraft.util.multiblock.MB_Register;
import com.cout970.magneticraft.util.multiblock.Multiblock;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import org.lwjgl.opengl.GL11;

public class TileRenderPolymerizer extends TileEntitySpecialRenderer {

    private ModelPolymerizer model = new ModelPolymerizer();

    @Override
    public void renderTileEntityAt(TileEntity t, double x, double y, double z, float frames) {
        TilePolymerizer tile = (TilePolymerizer) t;
        if (!tile.isActive()) {
            if (tile.drawCounter > 0) {
                GL11.glColor4f(1, 1, 1, 1f);
                Multiblock mb = MB_Register.getMBbyID(MB_Register.ID_POLIMERIZER);
                RenderUtil.renderMultiblock(x, y, z, tile, t, mb);
            }
        } else {
            GL11.glPushMatrix();
            GL11.glTranslated(x, y, z);
            GL11.glRotatef(180, 0, 0, 1);
            GL11.glRotatef(90, 0, 1, 0);

            RenderUtil.applyRotation(tile.getDirection());

            RenderUtil.bindTexture(ModelTextures.POLYMERIZER);
            model.renderStatic(0.0625f);
            if (tile.input != null) {
                GL11.glTranslated(1, -1, 4);
                GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
                GL11.glEnable(GL11.GL_CULL_FACE);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                renderFluid((TileCopperTank) tile.input.getParent());
                GL11.glPopAttrib();
            }
            GL11.glPopMatrix();
        }
    }

    private void renderFluid(TileCopperTank te) {
        if (te.getTank() == null || te.getTank().getFluid() == null) return;
        float k = 0.002f;
        IIcon i = te.getTank().getFluid().getFluid().getIcon();
        if (i == null) return;
        float h = ((float) te.getTank().getFluidAmount()) / ((float) te.getTank().getCapacity());
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        bindTexture(TextureMap.locationBlocksTexture);

        if (h > 0) {
            if (h <= 0.01f) h = 0.02f;
            if (h >= 1) h = 0.99f;
            h *= 1.25;
            GL11.glTranslatef(-0.375F, -1.5F, -0.375F);
            GL11.glTranslatef(k, 0.01f, k);
            if (te.cubeRenderer == null) {
                te.cubeRenderer = new CubeRenderer_Util();
            }
            te.cubeRenderer.renderBox(i, 0.75f - k * 2, h - 0.01f, 0.75f - k * 2);
        }
    }
}
