package com.cout970.magneticraft.util;

import codechicken.lib.vec.Vector3;
import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecDouble;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.util.multiblock.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public class RenderUtil {

    public static double zLevel;
    public static ResourceLocation MISC_ICONS = new ResourceLocation(Magneticraft.NAME.toLowerCase(), "textures/gui/misc.png");

    public static void drawTexturedModalRectScaled(int x, int y, int u, int v, int w, int h, int mx, int my) {
        float f;
        float f1;
        f = 1f / mx;
        f1 = 1f / my;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double) (x), (double) (y + h), zLevel, (double) ((float) (u) * f), (double) ((float) (v + h) * f1));
        tessellator.addVertexWithUV((double) (x + w), (double) (y + h), zLevel, (double) ((float) (u + w) * f), (double) ((float) (v + h) * f1));
        tessellator.addVertexWithUV((double) (x + w), (double) (y), zLevel, (double) ((float) (u + w) * f), (double) ((float) (v) * f1));
        tessellator.addVertexWithUV((double) (x), (double) (y), zLevel, (double) ((float) (u) * f), (double) ((float) (v) * f1));
        tessellator.draw();
    }

    public static void bindTexture(ResourceLocation r) {
        Minecraft.getMinecraft().renderEngine.bindTexture(r);
    }

    public static int getTexture(boolean[] b) {
        int count = 0;
        for (boolean c : b)
            if (c) {
                count++;
            }
        if (count != 1) return -1;
        if (b.length == 6) return b[0] ? 0 : b[1] ? 1 : b[2] ? 5 : b[3] ? 3 : b[4] ? 4 : b[5] ? 2 : -1;
        return b[0] ? 4 : b[1] ? 2 : b[2] ? 1 : b[3] ? 3 : -1;
    }

    public static int fromRGB(int r, int g, int b) {
        int color = 0;
        color += r * 65536;
        color += g * 256;
        color += b;
        return color;
    }

    public static void drawString(String string, int x, int y, int par4, boolean centered) {
        FontRenderer f = Minecraft.getMinecraft().fontRenderer;
        if (centered) {
            f.drawString(string, x - f.getStringWidth(string) / 2, y - f.getStringWidth("1") / 2, par4);
        } else {
            f.drawString(string, x, y, par4);
        }
    }

    public static int getTexture(byte b) {
        byte count = 0;
        for (byte c = 0; c < 6; c++)
            if ((b & (1 << c)) > 0) {
                count++;
            }
        if (count != 1) return -1;
        if ((b & 1) > 0) return 0;
        if ((b & 2) > 0) return 1;
        if ((b & 4) > 0) return 5;
        if ((b & 8) > 0) return 3;
        if ((b & 16) > 0) return 4;
        if ((b & 32) > 0) return 2;
        return -1;
    }

    public static Vector3 getHeatColor(double t) {

        if(t < 200) {
            return new Vector3(1, 1, 1);

        }else if(t < 500){
            double per = (t-200)/300d;
            Vector3 vec = new Vector3(0.87, 0.33, 0.35);
            return new Vector3(1-((1-vec.x)*per), 1-((1-vec.y)*per), 1-((1-vec.z)*per));

        }else if(t < 700){
            double per = (t-500)/200d;
            Vector3 vec1 = new Vector3(0.87, 0.33, 0.35);
            Vector3 vec2 = new Vector3(0.9, 0.05, 0.05);
            return new Vector3(vec1.x-((vec1.x-vec2.x)*per), vec1.y-((vec1.y-vec2.y)*per), vec1.z-((vec1.z-vec2.z)*per));

        }else{
            double per = Math.min(1, (t-700)/300d);
            Vector3 vec1 = new Vector3(0.9, 0.05, 0.05);
            Vector3 vec2 = new Vector3(1, 0, 0);
            return new Vector3(vec1.x-((vec1.x-vec2.x)*per), vec1.y-((vec1.y-vec2.y)*per), vec1.z-((vec1.z-vec2.z)*per));
        }
    }

    public static void renderBlock(Block b, int meta, int x, int y, int z, World w) {
        renderFaceDown(b.getIcon(0, meta), x, y, z);
        renderFaceUp(b.getIcon(1, meta), x, y, z);
        renderFaceNorth(b.getIcon(2, meta), x, y, z);
        renderFaceSouth(b.getIcon(3, meta), x, y, z);
        renderFaceWest(b.getIcon(4, meta), x, y, z);
        renderFaceEast(b.getIcon(5, meta), x, y, z);
    }

    public static void renderFaceUp(IIcon i, int x, int y, int z) {
        Tessellator t = Tessellator.instance;
        t.addVertexWithUV(x, y + 1, z, i.getInterpolatedU(0), i.getInterpolatedV(0));
        t.addVertexWithUV(x, y + 1, z + 1, i.getInterpolatedU(0), i.getInterpolatedV(16));
        t.addVertexWithUV(x + 1, y + 1, z + 1, i.getInterpolatedU(16), i.getInterpolatedV(16));
        t.addVertexWithUV(x + 1, y + 1, z, i.getInterpolatedU(16), i.getInterpolatedV(0));
    }

    public static void renderFaceDown(IIcon i, int x, int y, int z) {
        Tessellator t = Tessellator.instance;
        t.addVertexWithUV(x + 1, y, z, i.getInterpolatedU(0), i.getInterpolatedV(16));
        t.addVertexWithUV(x + 1, y, z + 1, i.getInterpolatedU(16), i.getInterpolatedV(16));
        t.addVertexWithUV(x, y, z + 1, i.getInterpolatedU(16), i.getInterpolatedV(0));
        t.addVertexWithUV(x, y, z, i.getInterpolatedU(0), i.getInterpolatedV(0));
    }

    public static void renderFaceNorth(IIcon i, int x, int y, int z) {
        Tessellator t = Tessellator.instance;
        t.addVertexWithUV(x, y, z + 1, i.getInterpolatedU(16), i.getInterpolatedV(0));
        t.addVertexWithUV(x, y + 1, z + 1, i.getInterpolatedU(16), i.getInterpolatedV(16));
        t.addVertexWithUV(x, y + 1, z, i.getInterpolatedU(0), i.getInterpolatedV(16));
        t.addVertexWithUV(x, y, z, i.getInterpolatedU(0), i.getInterpolatedV(0));
    }

    public static void renderFaceSouth(IIcon i, int x, int y, int z) {
        Tessellator t = Tessellator.instance;

        t.addVertexWithUV(x + 1, y + 1, z, i.getInterpolatedU(16), i.getInterpolatedV(16));
        t.addVertexWithUV(x + 1, y + 1, z + 1, i.getInterpolatedU(0), i.getInterpolatedV(16));
        t.addVertexWithUV(x + 1, y, z + 1, i.getInterpolatedU(0), i.getInterpolatedV(0));
        t.addVertexWithUV(x + 1, y, z, i.getInterpolatedU(16), i.getInterpolatedV(0));
    }

    public static void renderFaceWest(IIcon i, int x, int y, int z) {
        Tessellator t = Tessellator.instance;
        t.addVertexWithUV(x, y, z, i.getInterpolatedU(0), i.getInterpolatedV(0));
        t.addVertexWithUV(x, y + 1, z, i.getInterpolatedU(0), i.getInterpolatedV(16));
        t.addVertexWithUV(x + 1, y + 1, z, i.getInterpolatedU(16), i.getInterpolatedV(16));
        t.addVertexWithUV(x + 1, y, z, i.getInterpolatedU(16), i.getInterpolatedV(0));
    }

    public static void renderFaceEast(IIcon i, int x, int y, int z) {
        Tessellator t = Tessellator.instance;
        t.addVertexWithUV(x, y, z + 1, i.getInterpolatedU(16), i.getInterpolatedV(0));
        t.addVertexWithUV(x + 1, y, z + 1, i.getInterpolatedU(0), i.getInterpolatedV(0));
        t.addVertexWithUV(x + 1, y + 1, z + 1, i.getInterpolatedU(0), i.getInterpolatedV(16));
        t.addVertexWithUV(x, y + 1, z + 1, i.getInterpolatedU(16), i.getInterpolatedV(16));
    }

    public static void renderMultiblock(double x, double y, double z, MB_Tile tile, TileEntity t, Multiblock mb) {
        Tessellator tess = Tessellator.instance;
        RenderUtil.bindTexture(TextureMap.locationBlocksTexture);

        tess.setColorOpaque_F(1, 1, 1);
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) 65536, 1);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1, 1, 1, 1f);
        GL11.glTranslatef((float) x, (float) y, (float) z);

        float s = 0.5f, p = 1f;
        glScalef(s, s, s);
        int[] q = mb.getDimensions(tile.getDirection());
        int meta = t.getWorldObj().getBlockMetadata(t.xCoord, t.yCoord, t.zCoord);
        for (int j = 0; j < q[1]; j++) {
            for (int k = 0; k < q[2]; k++) {
                for (int i = 0; i < q[0]; i++) {
                    Mg_Component mut = mb.matrix[i][j][k];
                    VecInt rot = mb.translate(t.getWorldObj(), new VecInt(t.xCoord, t.yCoord, t.zCoord), i, j, k, mb, tile.getDirection(), meta);
                    if (mut instanceof SimpleComponent) {
                        SimpleComponent comp = (SimpleComponent) mut;
                        glPushMatrix();
                        GL11.glTranslatef(0.5f + p * rot.getX(), 0.5f + p * rot.getY(), 0.5f + p * rot.getZ());
                        if (comp.blocks.get(0) != Blocks.air) {
                            tess.startDrawingQuads();
                            RenderUtil.renderBlock(comp.blocks.get(0), 0, rot.getX(), rot.getY(), rot.getZ(), t.getWorldObj());
                            tess.draw();
                        }
                        glPopMatrix();
                    } else if (mut instanceof ReplaceComponent) {
                        ReplaceComponent comp = (ReplaceComponent) mut;
                        glPushMatrix();
                        GL11.glTranslatef(0.5f + p * rot.getX(), 0.5f + p * rot.getY(), 0.5f + p * rot.getZ());
                        if (comp.origin != Blocks.air) {
                            tess.startDrawingQuads();
                            if (comp.origin instanceof BlockSlab) {
                                RenderUtil.renderSlab(comp.origin, 0, rot.getX(), rot.getY(), rot.getZ(), t.getWorldObj());
                            } else {
                                RenderUtil.renderBlock(comp.origin, 0, rot.getX(), rot.getY(), rot.getZ(), t.getWorldObj());
                            }
                            tess.draw();
                        }
                        glPopMatrix();
                    }
                }
            }
        }
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    private static void renderSlab(Block b, int meta, int x, int y, int z, World worldObj) {
        renderFaceDown(b.getIcon(0, meta), x, y, z);
        renderFaceUp_Slab(b.getIcon(0, meta), x, y, z);
        renderFaceNorth_Slab(b.getIcon(2, meta), x, y, z);
        renderFaceSouth_Slab(b.getIcon(3, meta), x, y, z);
        renderFaceWest_Slab(b.getIcon(4, meta), x, y, z);
        renderFaceEast_Slab(b.getIcon(5, meta), x, y, z);
    }

    private static void renderFaceUp_Slab(IIcon i, int x, int y, int z) {
        Tessellator t = Tessellator.instance;
        t.addVertexWithUV(x, y + 0.5, z, i.getInterpolatedU(0), i.getInterpolatedV(0));
        t.addVertexWithUV(x, y + 0.5, z + 1, i.getInterpolatedU(0), i.getInterpolatedV(16));
        t.addVertexWithUV(x + 1, y + 0.5, z + 1, i.getInterpolatedU(16), i.getInterpolatedV(16));
        t.addVertexWithUV(x + 1, y + 0.5, z, i.getInterpolatedU(16), i.getInterpolatedV(0));
    }

    public static void renderFaceNorth_Slab(IIcon i, int x, int y, int z) {
        Tessellator t = Tessellator.instance;
        t.addVertexWithUV(x, y, z + 1, i.getInterpolatedU(8), i.getInterpolatedV(0));
        t.addVertexWithUV(x, y + 0.5, z + 1, i.getInterpolatedU(8), i.getInterpolatedV(8));
        t.addVertexWithUV(x, y + 0.5, z, i.getInterpolatedU(0), i.getInterpolatedV(8));
        t.addVertexWithUV(x, y, z, i.getInterpolatedU(0), i.getInterpolatedV(0));
    }

    public static void renderFaceSouth_Slab(IIcon i, int x, int y, int z) {
        Tessellator t = Tessellator.instance;

        t.addVertexWithUV(x + 1, y + 0.5, z, i.getInterpolatedU(8), i.getInterpolatedV(8));
        t.addVertexWithUV(x + 1, y + 0.5, z + 1, i.getInterpolatedU(0), i.getInterpolatedV(8));
        t.addVertexWithUV(x + 1, y, z + 1, i.getInterpolatedU(0), i.getInterpolatedV(0));
        t.addVertexWithUV(x + 1, y, z, i.getInterpolatedU(8), i.getInterpolatedV(0));
    }

    public static void renderFaceWest_Slab(IIcon i, int x, int y, int z) {
        Tessellator t = Tessellator.instance;
        t.addVertexWithUV(x, y, z, i.getInterpolatedU(0), i.getInterpolatedV(0));
        t.addVertexWithUV(x, y + 0.5, z, i.getInterpolatedU(0), i.getInterpolatedV(8));
        t.addVertexWithUV(x + 1, y + 0.5, z, i.getInterpolatedU(8), i.getInterpolatedV(8));
        t.addVertexWithUV(x + 1, y, z, i.getInterpolatedU(8), i.getInterpolatedV(0));
    }

    public static void renderFaceEast_Slab(IIcon i, int x, int y, int z) {
        Tessellator t = Tessellator.instance;
        t.addVertexWithUV(x, y, z + 1, i.getInterpolatedU(8), i.getInterpolatedV(0));
        t.addVertexWithUV(x + 1, y, z + 1, i.getInterpolatedU(0), i.getInterpolatedV(0));
        t.addVertexWithUV(x + 1, y + 0.5, z + 1, i.getInterpolatedU(0), i.getInterpolatedV(8));
        t.addVertexWithUV(x, y + 0.5, z + 1, i.getInterpolatedU(8), i.getInterpolatedV(8));
    }

    public static double interpolate(double fa, double fb, double fc, double x) {
        double a = 0, b = 0.5, c = 1;
        double L0 = ((x - b) / (a - b)) * ((x - c) / (a - c));
        double L1 = ((x - a) / (b - a)) * ((x - c) / (b - c));
        double L2 = ((x - a) / (c - a)) * ((x - b) / (c - b));
        return fa * L0 + fb * L1 + fc * L2;
    }

    public static void drawLine(VecDouble a, VecDouble b, float f) {
        Tessellator t = Tessellator.instance;
        float w = f / 2;
        t.addVertex(a.getX(), a.getY() - w, a.getZ());
        t.addVertex(a.getX(), a.getY() + w, a.getZ());
        t.addVertex(b.getX(), b.getY() + w, b.getZ());
        t.addVertex(b.getX(), b.getY() - w, b.getZ());

        t.addVertex(a.getX(), a.getY(), a.getZ() - w);
        t.addVertex(a.getX(), a.getY(), a.getZ() + w);
        t.addVertex(b.getX(), b.getY(), b.getZ() + w);
        t.addVertex(b.getX(), b.getY(), b.getZ() - w);

        t.addVertex(a.getX() - w, a.getY(), a.getZ());
        t.addVertex(a.getX() + w, a.getY(), a.getZ());
        t.addVertex(b.getX() + w, b.getY(), b.getZ());
        t.addVertex(b.getX() - w, b.getY(), b.getZ());
        //inverted
        t.addVertex(a.getX(), a.getY() + w, a.getZ());
        t.addVertex(a.getX(), a.getY() - w, a.getZ());
        t.addVertex(b.getX(), b.getY() - w, b.getZ());
        t.addVertex(b.getX(), b.getY() + w, b.getZ());

        t.addVertex(a.getX(), a.getY(), a.getZ() + w);
        t.addVertex(a.getX(), a.getY(), a.getZ() - w);
        t.addVertex(b.getX(), b.getY(), b.getZ() - w);
        t.addVertex(b.getX(), b.getY(), b.getZ() + w);

        t.addVertex(a.getX() + w, a.getY(), a.getZ());
        t.addVertex(a.getX() - w, a.getY(), a.getZ());
        t.addVertex(b.getX() - w, b.getY(), b.getZ());
        t.addVertex(b.getX() + w, b.getY(), b.getZ());

    }

    public static void applyRotation(MgDirection direction) {
        switch (direction) {
            case NORTH:
                GL11.glRotatef(-90, 0, 1, 0);
                GL11.glTranslated(-1.5, -0.5, 0.5);
                break;
            case SOUTH:
                GL11.glRotatef(90, 0, 1, 0);
                GL11.glTranslated(-0.5, -0.5, -0.5);
                break;
            case WEST:
                GL11.glRotatef(180, 0, 1, 0);
                GL11.glTranslated(-0.5, -0.5, 0.5);
                break;
            case EAST:
                GL11.glTranslated(-1.5, -0.5, -0.5);
                break;
            default:
                break;
        }
    }
}
