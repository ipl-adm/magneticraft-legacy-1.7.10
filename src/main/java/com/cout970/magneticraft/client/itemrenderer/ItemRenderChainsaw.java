package com.cout970.magneticraft.client.itemrenderer;

import com.cout970.magneticraft.client.model.ModelChainSaw;
import com.cout970.magneticraft.client.tilerender.ModelTextures;
import com.cout970.magneticraft.util.RenderUtil;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class ItemRenderChainsaw implements IItemRenderer {

    public ModelChainSaw model = new ModelChainSaw();

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
                render(0.2F, -0.3F, 0.3F, 0.45F, item, type);
                return;
            }
            case EQUIPPED: {
                render(0.5F, -0.5F, 0.275F, 2.0F, item, type);
                return;
            }
            case INVENTORY: {
                render(0.0F, -0.5F, -0.25F, 1.0F, item, type);
                return;
            }
            case EQUIPPED_FIRST_PERSON: {
                render(0.5F, 0.0F, 0.5F, 1.5F, item, type);
            }
        }
    }

    public void render(float x, float y, float z, float scale, ItemStack i, ItemRenderType type) {
        GL11.glPushMatrix();
//		GL11.glDisable(GL11.GL_LIGHTING);
        RenderHelper.enableStandardItemLighting();
        GL11.glScalef(scale, scale, scale);
        GL11.glTranslatef(x, y, z);
        GL11.glRotatef(180F, 0, 0, 1);
        if (type == ItemRenderType.EQUIPPED)
            GL11.glRotatef(130F, 0, 1, 0);
        else if (type == ItemRenderType.INVENTORY) GL11.glRotatef(90, 0, 1, 0);
        else GL11.glRotatef(180, 0, 1, 0);
        if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
            GL11.glRotatef(30, 1, 0, 0);
            GL11.glRotatef(-20, 0, 0, 1);
            GL11.glRotatef(20, 0, 1, 0);
            GL11.glTranslatef(0, 0, 0.3f);
        }
        GL11.glTranslatef(0, -1.5f, 0);
        RenderUtil.bindTexture(ModelTextures.CHAINSAW);
        model.renderStatic(0.0625f);
//		GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }
}