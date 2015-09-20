package com.cout970.magneticraft.client.itemrenderer;

import com.cout970.magneticraft.util.CubeRenderer_Util;
import com.cout970.magneticraft.util.IBlockWithData;
import com.cout970.magneticraft.util.RenderUtil;
import com.cout970.magneticraft.util.fluid.TankMg;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

public class ItemRenderCopperTank implements IItemRenderer {

    private TankMg tank = new TankMg(null, 16000);
    private CubeRenderer_Util fluid;
    private CubeRenderer_Util box;

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
                                         ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        switch (type) {
            case ENTITY: {
                render(0.0F, -0.5F, 0.0F, 1.0F, item);
                return;
            }
            case EQUIPPED: {
                render(0.5F, 0.0F, 0.5F, 1.0F, item);
                return;
            }
            case INVENTORY: {
                render(0.0F, -0.5F, 0.0F, 1.0F, item);
                return;
            }
            case EQUIPPED_FIRST_PERSON: {
                render(0.5F, 0.0F, 0.5F, 1.0F, item);
            }
        }
    }

    public void render(float x, float y, float z, float scale, ItemStack i) {
        GL11.glPushMatrix();
        GL11.glColor4f(1, 1, 1, 1);
        GL11.glTranslatef(x - 0.5F, y, z - 0.5F);
        if (box == null) {
            box = new CubeRenderer_Util();
        }
//		GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
//		GL11.glTranslatef(-0.5F, 0.5F, -0.5F);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1, 1, 1, 1);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 200, 100);
        box.renderBox(Block.getBlockFromItem(i.getItem()).getIcon(0, 0), 1, 1, 1);
        renderFluid(i.stackTagCompound);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    public TankMg getTank() {
        return tank;
    }

    private void renderFluid(NBTTagCompound nbt) {
        if (nbt == null || !nbt.getBoolean(IBlockWithData.KEY)) return;
        FluidStack f = tank.getFluid();
        tank.readFromNBT(nbt, "fluid");
        if (tank.getFluid() == null) return;
        if (this.fluid == null) {
            this.fluid = new CubeRenderer_Util();
        }
        if (f != tank.getFluid() || f.amount == tank.getFluid().amount) {
            fluid.reset();
        }

        float k = 0.002f;
        IIcon i = getTank().getFluid().getFluid().getIcon();
        if (i == null) return;
        float h = ((float) getTank().getFluidAmount()) / ((float) getTank().getCapacity());
        RenderUtil.bindTexture(TextureMap.locationBlocksTexture);

        if (h > 0) {
            if (h <= 0.01f) h = 0.02f;
            if (h >= 1) h = 0.99f;
            GL11.glTranslatef(k, 0.01f, k);
            this.fluid.renderBox(i, 1f - k * 2, h - 0.01f, 1f - k * 2);
        }
    }

}
