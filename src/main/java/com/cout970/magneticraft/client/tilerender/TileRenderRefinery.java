package com.cout970.magneticraft.client.tilerender;

import com.cout970.magneticraft.client.model.ModelRefinery;
import com.cout970.magneticraft.tileentity.multiblock.controllers.TileRefinery;
import com.cout970.magneticraft.tileentity.TileRefineryTank;
import com.cout970.magneticraft.util.CubeRenderer_Util;
import com.cout970.magneticraft.util.RenderUtil;
import com.cout970.magneticraft.util.multiblock.MB_Register;
import com.cout970.magneticraft.util.multiblock.Multiblock;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import org.lwjgl.opengl.GL11;

public class TileRenderRefinery extends TileEntitySpecialRenderer {

    private ModelRefinery model;

    public TileRenderRefinery() {
        model = new ModelRefinery();
    }

    @Override
    public void renderTileEntityAt(TileEntity t, double x, double y, double z, float frames) {
        TileRefinery tile = (TileRefinery) t;
        if (tile.isActive()) {
            GL11.glPushMatrix();
            GL11.glColor4f(1, 1, 1, 1);
            bindTexture(ModelTextures.REFINERY);
            GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            switch (tile.getDirectionMeta()) {
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
            GL11.glTranslatef(1, -1, 2);
            if (tile.input != null) renderFluid((TileRefineryTank) tile.input.getParent());
            GL11.glPopMatrix();
        } else if (tile.drawCounter > 0) {
            Multiblock mb = MB_Register.getMBbyID(MB_Register.ID_REFINERY);
            RenderUtil.renderMultiblock(x, y, z, tile, t, mb);
        }
    }

    private void renderFluid(TileRefineryTank te) {
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
            if (te.CubeRenderer == null) {
                te.CubeRenderer = new CubeRenderer_Util();
            }
            te.CubeRenderer.renderBox(i, 0.75f - k * 2, h - 0.01f, 0.75f - k * 2);
        }
    }
}
