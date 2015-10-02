package com.cout970.magneticraft.client.itemrenderer;

import com.cout970.magneticraft.client.model.ModelFluidPipe;
import com.cout970.magneticraft.client.model.ModelFluidPipe_Center;
import com.cout970.magneticraft.client.model.ModelFluidPipe_In;
import com.cout970.magneticraft.client.tilerender.ModelTextures;
import com.cout970.magneticraft.util.RenderUtil;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class ItemRenderCopperPipe implements IItemRenderer {

    public ModelFluidPipe model_0 = new ModelFluidPipe();
    public ModelFluidPipe_Center model_1 = new ModelFluidPipe_Center();
    public ModelFluidPipe_In model_2 = new ModelFluidPipe_In();

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
//		GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glScalef(scale, scale, scale);
        GL11.glTranslatef(x, y, z);
        GL11.glRotatef(180F, 0, 0, 1);
        GL11.glTranslatef(0, -1.5f, 0);

        boolean[] b = new boolean[]{false, false, true, true, false, false};
        RenderUtil.bindTexture(ModelTextures.PIPE_BASE_2_COPPER);
        model_0.render(0.0625f, b);
        RenderUtil.bindTexture(ModelTextures.PIPE_BASE_COPPER);
        model_1.render(0.0625f, new boolean[]{true, true, true, true, true, true});
        RenderUtil.bindTexture(ModelTextures.PIPE_IN_COPPER);
        model_2.render(0.0625f, b);
        RenderUtil.bindTexture(ModelTextures.PIPE_CONNECTION_1_COPPER);
        model_0.render(0.0625f, b, true);
//		GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }

}
