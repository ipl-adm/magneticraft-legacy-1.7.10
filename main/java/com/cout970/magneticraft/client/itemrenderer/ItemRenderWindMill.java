package com.cout970.magneticraft.client.itemrenderer;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import com.cout970.magneticraft.client.model.ModelGear;
import com.cout970.magneticraft.client.model.ModelShaft;
import com.cout970.magneticraft.client.tilerender.ModelTextures;
import com.cout970.magneticraft.util.RenderUtil;

public class ItemRenderWindMill implements IItemRenderer{

	public ModelShaft rotor = new ModelShaft();
	private ModelGear gear = new ModelGear();
	
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
			render(0.0F, -0.5F, 0.0F, 1.0F,item);
			return;
		}
		case EQUIPPED: {
			render(0.5F, 0.0F, 0.5F, 1.0F,item);
			return;
		}
		case INVENTORY: {
			render(0.0F, -0.5F, 0.0F, 1.0F,item);
			return;
		}
		case EQUIPPED_FIRST_PERSON: {
			render(0.5F, 0.0F, 0.5F, 1.0F,item);
			return;
		}
		default:
			return;
		}
	}
	
	public void render(float x, float y, float z, float scale, ItemStack it){
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glScalef(scale, scale, scale);
		GL11.glTranslatef(x, y, z);
		GL11.glRotatef(180F, 0, 0, 1);
		GL11.glRotatef(180F, 0, 1, 0);
		GL11.glTranslatef(0, -1.5f, 0);
		RenderUtil.bindTexture(ModelTextures.WINDMILL);
		rotor.renderStatic(0.0625f);
		
		rotor.renderDynamic(0.0625f,0,0);
		RenderUtil.bindTexture(ModelTextures.GEAR);
		float pixel = 0.0625f;
		GL11.glRotatef(90, 0, 1, 0);
		float i = 1+pixel;
		GL11.glScalef(i, i, i);
		GL11.glTranslatef(0,-(i-1),0);
		gear.renderDynamic(pixel,0);
		GL11.glTranslatef(0.0F, 0.2F, 0.28F);
		gear.renderDynamic(0.0625f,0);
		GL11.glTranslatef(-pixel*2,0,0);
		gear.renderDynamic(0.0625f,0);
		GL11.glRotatef(90, 0, 1, 0);
		GL11.glTranslatef(-pixel*5,-pixel*3,pixel);
		rotor.renderDynamic(pixel,1,0);
		GL11.glTranslatef(pixel*9.5f, 0, -pixel*7);
		rotor.renderDynamic(pixel,2,0);
		GL11.glRotatef(-90, 0, 1, 0);
		GL11.glTranslatef(pixel*6,0,0);
		gear.renderDynamic(0.0625f,0);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

}
