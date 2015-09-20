package com.cout970.magneticraft.client.itemrenderer;

import com.cout970.magneticraft.api.electricity.IBatteryItem;
import com.cout970.magneticraft.client.model.ModelJackHammer;
import com.cout970.magneticraft.client.tilerender.ModelTextures;
import com.cout970.magneticraft.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class ItemRenderJackHammer implements IItemRenderer {

    public ModelJackHammer model = new ModelJackHammer();

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
                render(0.0F, -0.9F, 0.0F, 0.5F, item, type);
                return;
            }
            case EQUIPPED: {
                render(0.1F, -1.1F, 0.6F, 2.0F, item, type);
                return;
            }
            case INVENTORY: {
                render(0.0F, -1.0F, 0.0F, 1.0F, item, type);
                return;
            }
            case EQUIPPED_FIRST_PERSON: {
                render(0.5F, -0.5F, 0.5F, 1.5F, item, type);
            }
        }
    }

    public void render(float x, float y, float z, float scale, ItemStack i, ItemRenderType type) {
        GL11.glPushMatrix();
        RenderHelper.enableStandardItemLighting();
        GL11.glScalef(scale, scale, scale);
        GL11.glTranslatef(x, y, z);
        GL11.glRotatef(180F, 0, 0, 1);
        if (type == ItemRenderType.EQUIPPED)
            GL11.glRotatef(140F, 0, 1, 0);
        if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
            GL11.glRotatef(45, 0, 1, 0);
            GL11.glTranslatef(0, -1.0f, 0);
            GL11.glTranslatef(0, 0.0f, 0.5f);
            GL11.glRotatef(-100, 1, 0, 0);
        } else GL11.glTranslatef(0, -1.6f, 0);
        RenderUtil.bindTexture(ModelTextures.JACK_HAMMER);
        float mot = 0;
        if (((IBatteryItem) i.getItem()).getCharge(i) > 0) {
            long time = Minecraft.getMinecraft().theWorld.getTotalWorldTime();
            mot = (time % 3) / 16f;
        }
        model.renderDynamic(0.0625f, mot);
        GL11.glPopMatrix();
    }
}