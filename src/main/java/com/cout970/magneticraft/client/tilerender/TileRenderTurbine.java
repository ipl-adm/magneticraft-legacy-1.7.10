package com.cout970.magneticraft.client.tilerender;

import com.cout970.magneticraft.client.model.ModelTurbine;
import com.cout970.magneticraft.tileentity.TileCopperTank;
import com.cout970.magneticraft.tileentity.multiblock.controllers.TileSteamTurbineControl;
import com.cout970.magneticraft.util.CubeRenderer_Util;
import com.cout970.magneticraft.util.RenderUtil;
import com.cout970.magneticraft.util.multiblock.MB_Register;
import com.cout970.magneticraft.util.multiblock.Multiblock;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public class TileRenderTurbine extends TileEntitySpecialRenderer {

    public ModelTurbine model = new ModelTurbine();

    @Override
    public void renderTileEntityAt(TileEntity t, double x, double y, double z, float frames) {
        TileSteamTurbineControl tile = (TileSteamTurbineControl) t;
        if (tile.getBlockMetadata() < 6) {
            if (tile.drawCounter > 0) {
                GL11.glColor4f(1, 1, 1, 1f);
                Multiblock mb = MB_Register.getMBbyID(MB_Register.ID_TURBINE);
                RenderUtil.renderMultiblock(x, y, z, tile, t, mb);
            }
        } else {
            glPushMatrix();
            glTranslated(x, y, z);
            glRotatef(180, 1, 0, 0);
            glRotatef(-90, 0, 1, 0);
            glEnable(GL_CULL_FACE);

            RenderUtil.applyRotation(tile.getDirection());

            if (tile.getFluidAmount() > 0) {
                tile.animation += (tile.getDelta() / 1E8) * tile.speed;
            }

            if (tile.animation > 1000) tile.animation = 0;

            RenderUtil.bindTexture(ModelTextures.TURBINE);
            model.renderStatic(0.0625f);
            model.renderDynamic(0.0625f, tile.animation);
            glPopMatrix();
            glPushMatrix();
            glTranslated(x, y, z);
            if (tile.in != null) {
                glEnable(GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                switch (tile.getDirection()) {
                    case EAST:
                        glTranslatef(-1.5f, -0.5f, -0.5f);
                        break;
                    case WEST:
                        glRotatef(180, 0, 1, 0);
                        glTranslatef(-2.5f, -0.5f, -1.5f);
                        break;
                    case SOUTH:
                        glRotatef(-90, 0, 1, 0);
                        glTranslatef(-1.5f, -0.5f, -1.5f);
                        break;
                    case NORTH:
                        glRotatef(90, 0, 1, 0);
                        glTranslatef(-2.5f, -0.5f, -0.5f);
                        break;
                    default:
                        break;
                }


                glPushMatrix();
                glTranslated(0.0f, 1.5f, 2.0f);
                if (tile.in[2] != null)
                    renderFluid((TileCopperTank) tile.in[2].getParent());
                glPopMatrix();

                glPushMatrix();
                glTranslated(0.0f, 1.5f, 0.0f);
                if (tile.in[3] != null)
                    renderFluid((TileCopperTank) tile.in[3].getParent());
                glPopMatrix();

                glPushMatrix();
                glTranslated(1.0f, 1.5f, 2.0f);
                if (tile.in[0] != null)
                    renderFluid((TileCopperTank) tile.in[0].getParent());
                glPopMatrix();

                glPushMatrix();
                glTranslated(1.0f, 1.5f, 0.0f);
                if (tile.in[1] != null)
                    renderFluid((TileCopperTank) tile.in[1].getParent());
                glPopMatrix();

                glDisable(GL_BLEND);
            }
            glPopMatrix();
        }
    }

    private void renderFluid(TileCopperTank te) {
        if (te.getTank() == null || te.getTank().getFluid() == null) return;
        float k = 0.002f;
        IIcon i = te.getTank().getFluid().getFluid().getIcon();
        if (i == null) return;
        float h = 0.99f * 1.5f;
        float alpha = ((float) te.getTank().getFluidAmount()) / ((float) te.getTank().getCapacity());
        if (alpha < 0.0625f) return;
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        bindTexture(TextureMap.locationBlocksTexture);
        glColor4f(1, 1, 1, alpha);

        GL11.glTranslatef(-0.375F, 0, -0.375F);
        GL11.glTranslatef(k, 0.01f, k);
        if (te.cubeRenderer == null) {
            te.cubeRenderer = new CubeRenderer_Util();
        }
        te.cubeRenderer.renderBox(i, 0.75f - k * 2, h - 0.01f, 0.75f - k * 2);
    }
}