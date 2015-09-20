package com.cout970.magneticraft.client.tilerender;

import com.cout970.magneticraft.api.conveyor.IConveyorBelt;
import com.cout970.magneticraft.api.conveyor.IConveyorBeltLane;
import com.cout970.magneticraft.api.conveyor.IItemBox;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecDouble;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.block.BlockConveyorLow;
import com.cout970.magneticraft.client.model.ModelConveyorBelt;
import com.cout970.magneticraft.client.model.ModelConveyorBeltAddition;
import com.cout970.magneticraft.tileentity.TileConveyorBelt;
import com.cout970.magneticraft.util.RenderUtil;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import org.lwjgl.opengl.GL11;

public class TileRenderConveyorBelt extends TileEntitySpecialRenderer {

    private final RenderItem RenderItemMG;
    private final EntityItem itemEntity = new EntityItem(null);
    private ModelConveyorBelt model;
    private static int DirectList = -1, UpList = -1, DownList = -1;
    private ModelConveyorBeltAddition extras;

    public TileRenderConveyorBelt() {
        model = new ModelConveyorBelt();
        extras = new ModelConveyorBeltAddition();
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
        GL11.glTranslatef((float) x, (float) y, (float) z);
        GL11.glTranslatef(0.5f, 1.5f, 0.5f);
        TileConveyorBelt tile = (TileConveyorBelt) t;

        for (IItemBox b : tile.getSideLane(true).getItemBoxes()) {
            if (b == null) continue;
            renderItemBox(b, tile);
        }
        for (IItemBox b : tile.getSideLane(false).getItemBoxes()) {
            if (b == null) continue;
            renderItemBox(b, tile);
        }
        GL11.glColor4f(1, 1, 1, 1);

        GL11.glRotatef(180, 1, 0, 0);
        GL11.glRotatef(180, 0, 1, 0);

        if (tile.getDir() == MgDirection.SOUTH) {
            GL11.glRotatef(180, 0, 1, 0);
        } else if (tile.getDir() == MgDirection.EAST) {
            GL11.glRotatef(90, 0, 1, 0);
        } else if (tile.getDir() == MgDirection.WEST) {
            GL11.glRotatef(-90, 0, 1, 0);
        }
        if (tile.getOrientation().getLevel() == 0) {
            int sides = 3;
            for (int h = -1; h <= 1; h++) {
                VecInt vec = tile.getDir().step(MgDirection.UP).toVecInt();
                vec.add(new VecInt(tile));
                TileEntity conveyor = tile.getWorldObj().getTileEntity(vec.getX(), vec.getY() + h, vec.getZ());
                if (conveyor instanceof IConveyorBelt) {
                    if (((IConveyorBelt) conveyor).getDir() == tile.getDir().step(MgDirection.UP).opposite()) {
                        sides &= 1;
                    }
                }

                vec = tile.getDir().step(MgDirection.DOWN).toVecInt();
                vec.add(new VecInt(tile));
                conveyor = tile.getWorldObj().getTileEntity(vec.getX(), vec.getY() + h, vec.getZ());
                if (conveyor instanceof IConveyorBelt) {
                    if (((IConveyorBelt) conveyor).getDir() == tile.getDir().step(MgDirection.DOWN).opposite()) {
                        sides &= 2;
                    }
                }
            }

            RenderUtil.bindTexture(ModelTextures.CONVEYOR_BELT_LOW);
            model.renderStatic_block(0.0625f);
            model.renderDynamic(0.0625f, sides);
        } else {
            if (tile.getOrientation().getLevel() == -1) {
                GL11.glRotatef(180, 0, 1, 0);
            }
            RenderUtil.bindTexture(ModelTextures.CONVEYOR_BELT_LOW_ADD);
            extras.renderStatic(0.0625f);
        }
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.0F, (float) y - 13 / 16F, (float) z + 0.0F);
        if (tile.getDir() == MgDirection.NORTH) {
            GL11.glRotatef(180, 0, 1, 0);
            GL11.glTranslatef(-1, 0, -1);
        } else if (tile.getDir() == MgDirection.WEST) {
            GL11.glRotatef(-90, 0, 1, 0);
            GL11.glTranslatef(0, 0, -1);
        } else if (tile.getDir() == MgDirection.EAST) {
            GL11.glRotatef(90, 0, 1, 0);
            GL11.glTranslatef(-1, 0, 0);
        }

        if (tile.getOrientation().getLevel() == 0) {
            if (DirectList == -1) {
                Tessellator tess = Tessellator.instance;
                DirectList = GL11.glGenLists(1);
                GL11.glNewList(DirectList, GL11.GL_COMPILE_AND_EXECUTE);
                RenderUtil.bindTexture(TextureMap.locationBlocksTexture);
                tess.startDrawingQuads();
                renderFaceTop(BlockConveyorLow.conveyor_low, 0, 0, 0);
                renderFaceFront(BlockConveyorLow.conveyor_low, 0, 0, 0);
                renderFaceBack(BlockConveyorLow.conveyor_low, 0, 0, 0);
                tess.draw();
                RenderUtil.bindTexture(ModelTextures.CONVEYOR_BELT_LOW);
                tess.startDrawingQuads();
                renderFaceBottom(0, 0, 0);
                tess.draw();
                GL11.glEndList();
            } else {
                GL11.glCallList(DirectList);
            }
        } else if (tile.getOrientation().getLevel() == -1) {
            renderSlopeDown(BlockConveyorLow.conveyor_low, 0, 0, 0);
        } else if (tile.getOrientation().getLevel() == 1) {
            renderSlopeUp(BlockConveyorLow.conveyor_low, 0, 0, 0);
        }
        GL11.glPopMatrix();
    }

    public static void renderFaceTop(IIcon i, int x, int y, int z) {
        Tessellator t = Tessellator.instance;
        t.addVertexWithUV(x, y + 1, z, i.getInterpolatedU(0), i.getInterpolatedV(0));
        t.addVertexWithUV(x, y + 1, z + 1, i.getInterpolatedU(0), i.getInterpolatedV(16));
        t.addVertexWithUV(x + 1, y + 1, z + 1, i.getInterpolatedU(16), i.getInterpolatedV(16));
        t.addVertexWithUV(x + 1, y + 1, z, i.getInterpolatedU(16), i.getInterpolatedV(0));
    }

    public static void renderFaceFront(IIcon i, int x, int y, int z) {
        Tessellator t = Tessellator.instance;
        float k = 13 / 16f;
        t.addVertexWithUV(x, y + k, z, i.getInterpolatedU(0), i.getInterpolatedV(0));
        t.addVertexWithUV(x, y + 3 / 16f + k, z, i.getInterpolatedU(0), i.getInterpolatedV(3));
        t.addVertexWithUV(x + 1, y + 3 / 16f + k, z, i.getInterpolatedU(16), i.getInterpolatedV(3));
        t.addVertexWithUV(x + 1, y + k, z, i.getInterpolatedU(16), i.getInterpolatedV(0));
    }

    public static void renderFaceBack(IIcon i, int x, int y, int z) {
        Tessellator t = Tessellator.instance;
        float k = 13 / 16f;
        t.addVertexWithUV(x, y + k, z + 1, i.getInterpolatedU(16), i.getInterpolatedV(3));
        t.addVertexWithUV(x + 1, y + k, z + 1, i.getInterpolatedU(0), i.getInterpolatedV(3));
        t.addVertexWithUV(x + 1, y + 3 / 16f + k, z + 1, i.getInterpolatedU(0), i.getInterpolatedV(0));
        t.addVertexWithUV(x, y + 3 / 16f + k, z + 1, i.getInterpolatedU(16), i.getInterpolatedV(0));
    }

    public static void renderFaceBottom(int x, int y, int z) {
        Tessellator t = Tessellator.instance;
        float d = 1 / 64f;
        float k = 13 / 16f;
        t.addVertexWithUV(x + 15 / 16f, y + k, z, d * 30, d * 21);
        t.addVertexWithUV(x + 15 / 16f, y + k, z + 1, d * 44, d * 21);
        t.addVertexWithUV(x + 1 / 16f, y + k, z + 1, d * 44, d * 37);
        t.addVertexWithUV(x + 1 / 16f, y + k, z, d * 30, d * 37);
    }

    public static void renderSlopeUp(IIcon i, int x, int y, int z) {
        if (UpList == -1) {
            UpList = GL11.glGenLists(1);
            GL11.glNewList(UpList, GL11.GL_COMPILE_AND_EXECUTE);
            Tessellator t = Tessellator.instance;
            float k = 13 / 16f;
            float d = 1 / 64f;
            //top
            RenderUtil.bindTexture(TextureMap.locationBlocksTexture);
            t.startDrawingQuads();
            t.addVertexWithUV(x, y + 1, z, i.getInterpolatedU(0), i.getInterpolatedV(0));
            t.addVertexWithUV(x, y + 2, z + 1, i.getInterpolatedU(0), i.getInterpolatedV(16));
            t.addVertexWithUV(x + 1, y + 2, z + 1, i.getInterpolatedU(16), i.getInterpolatedV(16));
            t.addVertexWithUV(x + 1, y + 1, z, i.getInterpolatedU(16), i.getInterpolatedV(0));
            t.draw();
            //front
            t.startDrawingQuads();
            t.addVertexWithUV(x, y + k, z, i.getInterpolatedU(0), i.getInterpolatedV(0));
            t.addVertexWithUV(x, y + 3 / 16f + k, z, i.getInterpolatedU(0), i.getInterpolatedV(3));
            t.addVertexWithUV(x + 1, y + 3 / 16f + k, z, i.getInterpolatedU(16), i.getInterpolatedV(3));
            t.addVertexWithUV(x + 1, y + k, z, i.getInterpolatedU(16), i.getInterpolatedV(0));
            t.draw();
            //back
            t.startDrawingQuads();
            t.addVertexWithUV(x, y + k + 1, z + 1, i.getInterpolatedU(16), i.getInterpolatedV(3));
            t.addVertexWithUV(x + 1, y + k + 1, z + 1, i.getInterpolatedU(0), i.getInterpolatedV(3));
            t.addVertexWithUV(x + 1, y + 3 / 16f + k + 1, z + 1, i.getInterpolatedU(0), i.getInterpolatedV(0));
            t.addVertexWithUV(x, y + 3 / 16f + k + 1, z + 1, i.getInterpolatedU(16), i.getInterpolatedV(0));
            t.draw();
            //bottom
            RenderUtil.bindTexture(ModelTextures.CONVEYOR_BELT_LOW);
            t.startDrawingQuads();
            t.addVertexWithUV(x + 15 / 16f, y + k, z, d * 30, d * 21);
            t.addVertexWithUV(x + 15 / 16f, y + k, z + 1, d * 44, d * 21);
            t.addVertexWithUV(x + 1 / 16f, y + k, z + 1, d * 44, d * 37);
            t.addVertexWithUV(x + 1 / 16f, y + k, z, d * 30, d * 37);
            t.draw();
            GL11.glEndList();
        } else {
            GL11.glCallList(UpList);
        }
    }

    public static void renderSlopeDown(IIcon i, int x, int y, int z) {
        if (DownList == -1) {
            DownList = GL11.glGenLists(1);
            GL11.glNewList(DownList, GL11.GL_COMPILE_AND_EXECUTE);
            Tessellator t = Tessellator.instance;
            float k = 13 / 16f;
            float d = 1 / 64f;
            //top
            RenderUtil.bindTexture(TextureMap.locationBlocksTexture);
            t.startDrawingQuads();
            t.addVertexWithUV(x, y + 2, z, i.getInterpolatedU(0), i.getInterpolatedV(0));
            t.addVertexWithUV(x, y + 1, z + 1, i.getInterpolatedU(0), i.getInterpolatedV(16));
            t.addVertexWithUV(x + 1, y + 1, z + 1, i.getInterpolatedU(16), i.getInterpolatedV(16));
            t.addVertexWithUV(x + 1, y + 2, z, i.getInterpolatedU(16), i.getInterpolatedV(0));
            t.draw();
            //front
            t.startDrawingQuads();
            t.addVertexWithUV(x, y + k + 1, z, i.getInterpolatedU(0), i.getInterpolatedV(0));
            t.addVertexWithUV(x, y + 3 / 16f + k + 1, z, i.getInterpolatedU(0), i.getInterpolatedV(3));
            t.addVertexWithUV(x + 1, y + 3 / 16f + k + 1, z, i.getInterpolatedU(16), i.getInterpolatedV(3));
            t.addVertexWithUV(x + 1, y + k + 1, z, i.getInterpolatedU(16), i.getInterpolatedV(0));
            t.draw();
            //back
            t.startDrawingQuads();
            t.addVertexWithUV(x, y + k, z + 1, i.getInterpolatedU(16), i.getInterpolatedV(3));
            t.addVertexWithUV(x + 1, y + k, z + 1, i.getInterpolatedU(0), i.getInterpolatedV(3));
            t.addVertexWithUV(x + 1, y + 3 / 16f + k, z + 1, i.getInterpolatedU(0), i.getInterpolatedV(0));
            t.addVertexWithUV(x, y + 3 / 16f + k, z + 1, i.getInterpolatedU(16), i.getInterpolatedV(0));
            t.draw();
            //bottom
            RenderUtil.bindTexture(ModelTextures.CONVEYOR_BELT_LOW);
            t.startDrawingQuads();
            t.addVertexWithUV(x + 15 / 16f, y + k, z, d * 30, d * 21);
            t.addVertexWithUV(x + 15 / 16f, y + k, z + 1, d * 44, d * 21);
            t.addVertexWithUV(x + 1 / 16f, y + k, z + 1, d * 44, d * 37);
            t.addVertexWithUV(x + 1 / 16f, y + k, z, d * 30, d * 37);
            t.draw();
            GL11.glEndList();
        } else {
            GL11.glCallList(DownList);
        }
    }

    private void renderItemBox(IItemBox b, TileConveyorBelt c) {
        GL11.glPushMatrix();
        float delta = (int) (System.currentTimeMillis() - c.time);
        if (delta > 50) {
            delta = 50;
        }
        IConveyorBeltLane lane = c.getSideLane(b.isOnLeft());

        lane.setHitBoxSpace(b.getPosition(), false);
        if (lane.hasHitBoxSpace(b.getPosition() + 1)) {
            delta = (delta / 50f);
        } else delta = 0;
        lane.setHitBoxSpace(b.getPosition(), true);

        float pos = b.getPosition() + delta;
        float d = pos * 0.0625f - 0.5f + 0.125f;
        float renderScale = 0.7f;
        float h = 0;

        VecDouble v = new VecDouble(c.getDir().step(MgDirection.DOWN).toVecInt());
        if (c.getOrientation().getLevel() == 1) {
            h = pos / 16f;
        } else if (c.getOrientation().getLevel() == -1) {
            h = 1 - pos / 16f;
        }
        GL11.glTranslatef(0, -1.125F, 0);
        if (b.isOnLeft()) {
            v.multiply(0.7 * 0.4);
        } else {
            v.multiply(-0.7 * 0.4);
        }
        GL11.glTranslated(v.getX(), 0, v.getZ());
        GL11.glTranslatef(c.getDir().getOffsetX() * d, h, c.getDir().getOffsetZ() * d);
        GL11.glScalef(renderScale, renderScale, renderScale);
        itemEntity.setEntityItemStack(b.getContent());
        RenderItemMG.doRender(itemEntity, 0, 0, 0, 0, 0);
        GL11.glPopMatrix();
    }
}
