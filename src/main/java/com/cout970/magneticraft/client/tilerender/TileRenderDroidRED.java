package com.cout970.magneticraft.client.tilerender;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.client.model.ModelDroneRED;
import com.cout970.magneticraft.tileentity.TileDroidRED;
import com.cout970.magneticraft.util.RenderUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class TileRenderDroidRED extends TileEntitySpecialRenderer {

    private ModelDroneRED model;

    public TileRenderDroidRED() {
        model = new ModelDroneRED();
    }

    @Override
    public void renderTileEntityAt(TileEntity t, double x, double y, double z, float frames) {
        TileDroidRED tile = (TileDroidRED) t;
        if (!tile.activate) return;
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glRotatef(180, 0, 0, 1);


        if (tile.droidProgress != -1) {
            if (tile.droidAction == 3) {
                GL11.glRotatef(-90 + tile.droidProgress * 90 / 5f, 0, 1, 0);
            } else if (tile.droidAction == 2) {
                GL11.glRotatef(90 - tile.droidProgress * 90 / 5f, 0, 1, 0);
            } else if (tile.droidAction == 4) {
                GL11.glTranslatef(0, 1, 0);
                if (tile.getOrientation().getDirection() == MgDirection.EAST) {
                    GL11.glRotatef(-90 + tile.droidProgress * 90 / 5f, 0, 0, 1);
                } else if (tile.getOrientation().getDirection() == MgDirection.WEST) {
                    GL11.glRotatef(90 - tile.droidProgress * 90 / 5f, 0, 0, 1);
                } else if (tile.getOrientation().getDirection() == MgDirection.SOUTH) {
                    GL11.glRotatef(-90 + tile.droidProgress * 90 / 5f, 1, 0, 0);
                } else if (tile.getOrientation().getDirection() == MgDirection.NORTH) {
                    GL11.glRotatef(90 - tile.droidProgress * 90 / 5f, 1, 0, 0);
                }
                GL11.glTranslatef(0, -1, 0);
            } else if (tile.droidAction == 5) {
                GL11.glTranslatef(0, 1, 0);
                if (tile.getOrientation().getDirection() == MgDirection.EAST) {
                    GL11.glRotatef(-90 + tile.droidProgress * 90 / 5f, 0, 0, 1);
                } else if (tile.getOrientation().getDirection() == MgDirection.WEST) {
                    GL11.glRotatef(90 - tile.droidProgress * 90 / 5f, 0, 0, 1);
                } else if (tile.getOrientation().getDirection() == MgDirection.SOUTH) {
                    GL11.glRotatef(-90 + tile.droidProgress * 90 / 5f, 1, 0, 0);
                } else if (tile.getOrientation().getDirection() == MgDirection.NORTH) {
                    GL11.glRotatef(90 - tile.droidProgress * 90 / 5f, 1, 0, 0);
                }
                GL11.glTranslatef(0, -1, 0);
            }
        }

        if (tile.getOrientation().getDirection() == MgDirection.NORTH) {
            GL11.glRotatef(180, 0, 1, 0);
        } else if (tile.getOrientation().getDirection() == MgDirection.WEST) {
            GL11.glRotatef(90, 0, 1, 0);
        } else if (tile.getOrientation().getDirection() == MgDirection.EAST) {
            GL11.glRotatef(-90, 0, 1, 0);
        }

        if (tile.getDirection() == MgDirection.DOWN) {
            GL11.glRotatef(90, 1, 0, 0);
            GL11.glTranslatef(0, -1, -1);
        } else if (tile.getDirection() == MgDirection.UP) {
            GL11.glRotatef(-90, 1, 0, 0);
            GL11.glTranslatef(0, -1, 1);
        }

        if (tile.droidProgress != -1) {
            if (tile.droidAction == 0 || tile.droidAction == 1) {
                float f = tile.droidProgress / 5f;
                GL11.glTranslated(0, 0, tile.droidAction == 0 ? f - 1 : 1 - f);
            }
        }

        RenderUtil.bindTexture(ModelTextures.DROID_RED);
        tile.drillAnim += (tile.getDelta() / 1E6) * 1.5;
        if (tile.drillAnim > 1000) tile.drillAnim = 0;

        model.renderDynamic(0.0625f, (float) Math.toRadians(tile.drillAnim * 0.36f), 0);

        if (tile.getOrientation().getLevel() == -1) {
            GL11.glRotatef(-90, 1, 0, 0);
            GL11.glTranslatef(0, -1, 1);
        } else if (tile.getOrientation().getLevel() == 1) {
            GL11.glRotatef(90, 1, 0, 0);
            GL11.glTranslatef(0, -1, -1);
        }
        if (tile.droidProgress != -1) {
            if (tile.droidAction == 4) {
                GL11.glTranslatef(0, 1, 0);
                if (tile.getOrientation().getDirection() == MgDirection.EAST) {
                    GL11.glRotatef(+90 - tile.droidProgress * 90 / 5f, 1, 0, 0);
                } else if (tile.getOrientation().getDirection() == MgDirection.WEST) {
                    GL11.glRotatef(90 - tile.droidProgress * 90 / 5f, 1, 0, 0);
                } else if (tile.getOrientation().getDirection() == MgDirection.SOUTH) {
                    GL11.glRotatef(90 - tile.droidProgress * 90 / 5f, 1, 0, 0);
                } else if (tile.getOrientation().getDirection() == MgDirection.NORTH) {
                    GL11.glRotatef(90 - tile.droidProgress * 90 / 5f, 1, 0, 0);
                }
                GL11.glTranslatef(0, -1, 0);
            }
        }
        model.renderStatic(0.0625f);
        GL11.glPopMatrix();
    }
}
