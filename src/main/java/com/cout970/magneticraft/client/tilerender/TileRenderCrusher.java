package com.cout970.magneticraft.client.tilerender;

import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecDouble;
import com.cout970.magneticraft.client.model.ModelCrusherLeft;
import com.cout970.magneticraft.client.model.ModelCrusherRight;
import com.cout970.magneticraft.tileentity.multiblock.controllers.TileCrusher;
import com.cout970.magneticraft.util.Log;
import com.cout970.magneticraft.util.RenderUtil;
import com.cout970.magneticraft.util.multiblock.MB_Register;
import com.cout970.magneticraft.util.multiblock.Multiblock;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class TileRenderCrusher extends TileEntitySpecialRenderer {

    public ModelCrusherLeft model;
    public ModelCrusherRight model2;
    private final RenderItem RenderItemMG;
    private final EntityItem itemEntity = new EntityItem(null);

    public TileRenderCrusher() {
        model = new ModelCrusherLeft();
        model2 = new ModelCrusherRight();

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
        TileCrusher tile = (TileCrusher) t;

        int meta = t.getWorldObj().getBlockMetadata(t.xCoord, t.yCoord, t.zCoord);
        if (meta >= 8) {
            GL11.glPushMatrix();
            GL11.glTranslated(x, y, z);
            GL11.glRotatef(180, 1, 0, 0);
            GL11.glRotatef(180, 0, 1, 0);
            if (tile.getDirection() == MgDirection.SOUTH) {
                GL11.glRotatef(180, 0, 1, 0);
            } else if (tile.getDirection() == MgDirection.EAST) {
                GL11.glRotatef(90, 0, 1, 0);
            } else if (tile.getDirection() == MgDirection.WEST) {
                GL11.glRotatef(-90, 0, 1, 0);
            }
            VecDouble vec;
            switch (meta % 8) {
                case 0:
                    vec = new VecDouble(-1.5f, -0.5f, 0.5f);
                    break;
                case 1:
                    vec = new VecDouble(-0.5f, -0.5f, -0.5f);
                    break;
                case 2:
                    vec = new VecDouble(-0.5f, -0.5f, 0.5f);
                    break;
                case 3:
                    vec = new VecDouble(-1.5f, -0.5f, -0.5f);
                    break;
                case 4:
                    vec = new VecDouble(-1.5f, -0.5f, 0.5f);
                    break;
                case 5:
                    vec = new VecDouble(-0.5f, -0.5f, -0.5f);
                    break;
                case 6:
                    vec = new VecDouble(-0.5f, -0.5f, 0.5f);
                    break;
                case 7:
                    vec = new VecDouble(-1.5f, -0.5f, -0.5f);
                    break;
                default:
                    vec = new VecDouble(0, 0, 0);
            }
            GL11.glTranslated(vec.getX(), vec.getY(), vec.getZ());
            if (tile.cond.getVoltage() > ElectricConstants.MACHINE_WORK && tile.working) {
                tile.animation += (tile.getDelta() / 1E6) * (((int) Math.ceil(tile.cond.getStorage() * 10f / tile.cond.getMaxStorage())) / 5f);
            }

            if (tile.animation > 1000) tile.animation = 0;
            RenderUtil.bindTexture(ModelTextures.CRUSHER);
            if (meta % 8 >= 4) {
                model.renderStatic(0.0625f);
                model.renderDynamic(0.0625f, tile.animation);
            } else {
                model2.renderStatic(0.0625f);
                model2.renderDynamic(0.0625f, tile.animation);
            }
            GL11.glPopMatrix();

            ItemStack item = tile.getStackInSlot(0);
            if (item != null) {
                GL11.glPushMatrix();
                GL11.glTranslated(x + 0.5, y + 1.4f, z + 0.5);
                MgDirection d = tile.getDirection().opposite();
                vec = new VecDouble(d.toVecInt());
                vec.multiply(0.45);
                GL11.glTranslated(vec.getX(), vec.getY(), vec.getZ());
                item = item.copy();
                item.stackSize = 1;
                itemEntity.setEntityItemStack(item);
                RenderItemMG.doRender(itemEntity, 0, 0, 0, 0, 0);
                if (tile.cond.getVoltage() > ElectricConstants.MACHINE_WORK && tile.working) {
                    Random r = tile.getWorldObj().rand;
                    if ((int) (tile.animation) < 550 && (int) (tile.animation) > 450 && r.nextBoolean()) {
                        try {
                            tile.getWorldObj().spawnParticle("smoke", tile.xCoord + vec.getX() + 0.5f, tile.yCoord + vec.getY() + 1.4f, tile.zCoord + vec.getZ() + 0.5f, 0.03125 / 2 - r.nextFloat() * 0.03125, 0.03125 / 2 - r.nextFloat() * 0.03125, 0.03125 / 2 - r.nextFloat() * 0.03125);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.error("-----------------------------------------------------------");
                            Log.error("Please Report this bug to the mod author of Magneticraft");
                            Log.error("-----------------------------------------------------------");
                        }
                    }
                }
                GL11.glPopMatrix();
            }

        } else {
            if (tile.drawCounter > 0) {
                GL11.glColor4f(1, 1, 1, 1f);
                Multiblock mb = MB_Register.getMBbyID(MB_Register.ID_CRUSHER);
                RenderUtil.renderMultiblock(x, y, z, tile, t, mb);
            }
        }
    }
}
