package com.cout970.magneticraft.client.tilerender;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.client.model.ModelInserter;
import com.cout970.magneticraft.tileentity.TileInserter;
import com.cout970.magneticraft.tileentity.TileInserter.InserterAnimation;
import com.cout970.magneticraft.util.Log;
import com.cout970.magneticraft.util.RenderUtil;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class TileRenderInserter extends TileEntitySpecialRenderer {

    private ModelInserter model;
    private final RenderItem RenderItemMG;
    private final EntityItem itemEntity = new EntityItem(null);

    public TileRenderInserter() {
        model = new ModelInserter();
        RenderItemMG = new RenderItem() {
            @Override
            public boolean shouldBob() {
                return false;
            }

            @Override
            public boolean shouldSpreadItems() {
                return false;
            }
        };
        RenderItemMG.setRenderManager(RenderManager.instance);
    }

    @Override
    public void renderTileEntityAt(TileEntity t, double x, double y, double z, float frames) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        TileInserter tile = (TileInserter) t;
        ItemStack item = tile.getInv().getStackInSlot(0);

        GL11.glRotatef(180, 0, 0, 1);

        if (tile.getDir() == MgDirection.NORTH) {
            GL11.glRotatef(180, 0, 1, 0);
        } else if (tile.getDir() == MgDirection.WEST) {
            GL11.glRotatef(90, 0, 1, 0);
        } else if (tile.getDir() == MgDirection.EAST) {
            GL11.glRotatef(-90, 0, 1, 0);
        }

        RenderUtil.bindTexture(ModelTextures.INSERTER);
        model.renderStatic(0.0625f);
        float[] array = getAngles(tile.counter+frames, tile.anim);

        model.renderDynamic(0.0625f, array[0], array[1], array[2], array[3], RenderItemMG, itemEntity, item);

        GL11.glPopMatrix();
    }

    //  0;-85;90  catch near
    // 45;-45;100 rotate and standard
    //-45;-90;135 catch far

    public static float[] getAngles(float counter, InserterAnimation anim) {
        float d0;
        float[] result = new float[4];
        result[3] = 0;
        if (anim == null) {
            result[0] = 45;
            result[1] = -45;
            result[2] = 100;
        } else {
            switch (anim) {
                case RETRACTING_SHORT: {//bring the item from the belt/inv
                    d0 = (counter) / 180f;
                    result[0] = 45 * d0;
                    result[1] = -85 + 40 * d0;
                    result[2] = 90 + 10 * d0;
                    break;
                }

                case ROTATING: {//rotating from default to rotated
                    result[0] = 45;
                    result[1] = -45;
                    result[2] = 100;
                    d0 = counter;
                    GL11.glRotatef(d0, 0, 1, 0);
                    break;
                }

                case EXTENDING_INV_SHORT: {
                    d0 = counter / 180f;
                    result[0] = 45 - 45 * d0;
                    result[1] = -45 - 40 * d0;
                    result[2] = 100 - 10 * d0;
                    GL11.glRotatef(180, 0, 1, 0);
                    break;
                }

                case RETRACTING_INV_SHORT: {
                    d0 = 1 - (counter / 180f);
                    result[0] = 45 - 45 * d0;
                    result[1] = -45 - 40 * d0;
                    result[2] = 100 - 10 * d0;
                    GL11.glRotatef(180, 0, 1, 0);
                    break;
                }

                case ROTATING_INV: {//rotating from inverted to default
                    result[0] = 45;
                    result[1] = -45;
                    result[2] = 100;
                    d0 = 180 - counter;
                    GL11.glRotatef(d0, 0, 1, 0);
                    break;
                }

                case EXTENDING_SHORT: {
                    d0 = 1 - (counter / 180f);
                    result[0] = 45 * d0;
                    result[1] = -85 + 40 * d0;
                    result[2] = 90 + 10 * d0;
                    break;
                }

                case EXTENDING_INV_LARGE: {
                    d0 = counter / 180f;
                    result[0] = 45 - 105 * d0;
                    result[1] = -45 - 35 * d0;
                    result[2] = 100;
                    GL11.glRotatef(180, 0, 1, 0);
                    break;
                }

                case RETRACTING_INV_LARGE: {
                    d0 = 1 - (counter / 180f);
                    result[0] = 45 - 105 * d0;
                    result[1] = -45 - 35 * d0;
                    result[2] = 100;
                    GL11.glRotatef(180, 0, 1, 0);
                    break;
                }

                case EXTENDING_LARGE: {
                    d0 = counter / 180f;
                    result[0] = 45 - 105 * d0;
                    result[1] = -45 - 35 * d0;
                    result[2] = 100;
                    break;
                }

                case RETRACTING_LARGE: {
                    d0 = 1 - (counter / 180f);
                    result[0] = 45 - 105 * d0;
                    result[1] = -45 - 35 * d0;
                    result[2] = 100;
                    break;
                }

                case DROP_ITEM: {
                    d0 = 0;
                    result[0] = 45 * d0;
                    result[1] = -85 + 40 * d0;
                    result[2] = 90 + 10 * d0;
                    GL11.glRotatef(180, 0, 1, 0);
                    break;
                }

                case SUCK_ITEM: {
                    d0 = 0;
                    result[0] = 45 * d0;
                    result[1] = -85 + 40 * d0;
                    result[2] = 90 + 10 * d0;
                    break;

                }

                case DROP_ITEM_LARGE: {
                    d0 = 1 - (counter / 180f);
                    result[0] = 45 - 105 * d0;
                    result[1] = -45 - 35 * d0;
                    result[2] = 100;
                    GL11.glRotatef(180, 0, 1, 0);
                    break;
                }

                case SUCK_ITEM_LARGE: {
                    d0 = 1;
                    result[0] = 45 - 105 * d0;
                    result[1] = -45 - 35 * d0;
                    result[2] = 100;
                    break;
                }

                default: {
                    result[0] = 45;
                    result[1] = -45;
                    result[2] = 100;
                }
            }
        }
        return result;
    }
}
