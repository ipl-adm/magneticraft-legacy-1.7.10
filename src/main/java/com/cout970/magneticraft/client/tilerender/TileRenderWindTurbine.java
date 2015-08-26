package com.cout970.magneticraft.client.tilerender;

import com.cout970.magneticraft.api.tool.IWindTurbine;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.tileentity.TileWindTurbine;
import com.cout970.magneticraft.util.RenderUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class TileRenderWindTurbine extends TileEntitySpecialRenderer {


    public TileRenderWindTurbine() {
    }

    @Override
    public void renderTileEntityAt(TileEntity t, double x, double y, double z, float frames) {
        TileWindTurbine tile = (TileWindTurbine) t;
        if (tile.rend == null || tile.turbine != tile.oldTurbine) {
            tile.oldTurbine = tile.turbine;
            ItemStack a = tile.getInv().getStackInSlot(0);
            if (a != null) {
                if (a.getItem() instanceof IWindTurbine) {
                    tile.rend = ((IWindTurbine) a.getItem()).initRender();
                }
            }
        }
        if (tile.rend == null) return;
        if (tile.turbine != -1) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float) x + 0.5F, (float) y + 2.5F, (float) z + 0.5F);
            GL11.glRotatef(180, 0, 0, 1);
//			GL11.glDisable(GL11.GL_LIGHTING);

            tile.rotation -= tile.getDelta() * (tile.speed);
            if (tile.rotation < -1000) tile.rotation += 1000;
            if (tile.rotation < -10000) tile.rotation = 0;

            if (tile.facing == MgDirection.NORTH) {
                GL11.glRotatef(90, 1, 0, 0);
                GL11.glTranslatef(0, -2, -2);
            } else if (tile.facing == MgDirection.SOUTH) {
                GL11.glRotatef(-90, 1, 0, 0);
                GL11.glTranslatef(0, -2, 2);
            } else if (tile.facing == MgDirection.WEST) {
                GL11.glRotatef(90, 0, 0, 1);
                GL11.glTranslatef(2, -2, 0);
            } else if (tile.facing == MgDirection.EAST) {
                GL11.glRotatef(-90, 0, 0, 1);
                GL11.glTranslatef(-2, -2, 0);
            }
            RenderUtil.bindTexture(tile.rend.getTexture());
            tile.rend.renderStatic(0.0625f);
            GL11.glRotatef(tile.rotation * 0.36f, 0, 1, 0);
            tile.rend.renderDynamic(0.0625f, tile.rotation * 0.36f);
            GL11.glPopMatrix();
        }
    }
}