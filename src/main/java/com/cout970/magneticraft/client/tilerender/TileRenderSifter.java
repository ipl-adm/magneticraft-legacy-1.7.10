package com.cout970.magneticraft.client.tilerender;

import com.cout970.magneticraft.client.model.ModelGauge;
import com.cout970.magneticraft.client.model.ModelSifter;
import com.cout970.magneticraft.client.model.ModelSwitch;
import com.cout970.magneticraft.tileentity.multiblock.controllers.TileSifter;
import com.cout970.magneticraft.util.RenderUtil;
import com.cout970.magneticraft.util.multiblock.MB_Register;
import com.cout970.magneticraft.util.multiblock.Multiblock;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class TileRenderSifter extends TileEntitySpecialRenderer {

    private ModelSifter model;
    private ModelSwitch model2;
    private ModelGauge model3;

    public TileRenderSifter() {
        model = new ModelSifter();
        model2 = new ModelSwitch();
        model3 = new ModelGauge();
    }

    @Override
    public void renderTileEntityAt(TileEntity t, double x, double y, double z, float frames) {
        TileSifter tile = (TileSifter) t;
        if (tile.isActive()) {
            GL11.glPushMatrix();
            GL11.glColor4f(1, 1, 1, 1);
            bindTexture(ModelTextures.SIFTER);
            GL11.glTranslatef((float) x + 1.5F, (float) y + 1.5F, (float) z - 0.5F);
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            switch (tile.getDirection()) {
                case EAST:
                    GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
                    GL11.glTranslatef(0, 0, 2);
                    break;
                case SOUTH:
                    GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
                    break;
                case NORTH:
                    GL11.glTranslatef(2, 0, 2);
                    break;
                case WEST:
                    GL11.glRotatef(-90F, 0.0F, 1.0F, 0.0F);
                    GL11.glTranslatef(2, 0, 0);
                    break;
                default:
                    break;
            }
            model.renderStatic(0.0625f);

            bindTexture(ModelTextures.ELECTRIC_SWITCH);
            float dist = 2 / 16F;
            float delta = (float) (tile.getDelta() / 1E6) * 0.05F;
            GL11.glPushMatrix();

            GL11.glTranslatef(0, 0, -3 + dist);
            GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);
            float rot = !tile.leverState[3] ? 0 : (float) Math.toRadians(180);
            if (tile.leverCount[3] > 0) {
                rot -= Math.toRadians(tile.leverCount[3] * (180 / 50F));
                rot = Math.abs(rot);
                tile.leverCount[3] -= delta;
            }
            model2.renderStatic(0.0625f);
            model2.renderDynamic(0.0625f, rot);
            GL11.glTranslatef(-0.5F, 0, 0);

            rot = !tile.leverState[2] ? 0 : (float) Math.toRadians(180);
            if (tile.leverCount[2] > 0) {
                rot -= Math.toRadians(tile.leverCount[2] * (180 / 50F));
                rot = Math.abs(rot);
                tile.leverCount[2] -= delta;
            }
            model2.renderStatic(0.0625f);
            model2.renderDynamic(0.0625f, rot);
            GL11.glTranslatef(-0.5F, 0, 0);

            rot = !tile.leverState[1] ? 0 : (float) Math.toRadians(180);
            if (tile.leverCount[1] > 0) {
                rot -= Math.toRadians(tile.leverCount[1] * (180 / 50F));
                rot = Math.abs(rot);
                tile.leverCount[1] -= delta;
            }
            model2.renderStatic(0.0625f);
            model2.renderDynamic(0.0625f, rot);
            GL11.glPopMatrix();
            GL11.glPushMatrix();

            GL11.glTranslatef(-3 + dist, 0, -1);
            GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);
            //on/off
            rot = !tile.leverState[0] ? 0 : (float) Math.toRadians(180);
            if (tile.leverCount[0] > 0) {
                rot -= Math.toRadians(tile.leverCount[0] * (180 / 50F));
                rot = Math.abs(rot);
                tile.leverCount[0] -= delta;
            }
            model2.renderStatic(0.0625f);
            model2.renderDynamic(0.0625f, rot);
            GL11.glPopMatrix();

            float s = 8 / 16F;
            GL11.glColor4f(1, 1, 1, 1);
            bindTexture(ModelTextures.GAUGE);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glTranslatef(1 + 0.0625f * 2, -0.1F, -0.95f);
            GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(90F, 0.0F, 0.0F, 1.0F);
            GL11.glScalef(s, s, s);
            model3.renderStatic(0.0625F);
            model3.renderDynamic(0.0625F, tile.working ? 100 : 0);
            GL11.glTranslatef(0, 0, -0.95f);
            model3.renderStatic(0.0625F);
            model3.renderDynamic(0.0625F, tile.speed == 100 ? 0 : tile.speed == 75 ? 33 : tile.speed == 50 ? 66 : 100);
            GL11.glTranslatef(0, 0, -0.95f);
            model3.renderStatic(0.0625F);
            model3.renderDynamic(0.0625F, tile.cond == null ? 0 : tile.cond.getVoltage() > 240 ? 100f : (float) tile.cond.getVoltage() / 2.4f);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glPopMatrix();

        } else if (tile.drawCounter > 0) {
            Multiblock mb = MB_Register.getMBbyID(MB_Register.ID_SIFTER);
            RenderUtil.renderMultiblock(x, y, z, tile, t, mb);
        }
    }
}
