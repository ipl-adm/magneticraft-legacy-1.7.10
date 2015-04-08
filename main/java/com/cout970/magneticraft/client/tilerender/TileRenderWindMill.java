package com.cout970.magneticraft.client.tilerender;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.client.model.ModelWindMillBig;
import com.cout970.magneticraft.tileentity.TileWindTurbine;
import com.cout970.magneticraft.util.RenderUtil;

public class TileRenderWindMill extends TileEntitySpecialRenderer{
	
//	private ModelWindMillSmall small;
//	private ModelWindMillMedium medium;
	private ModelWindMillBig big;
//	private ModelShaft rotor;
//	private ModelGear gear;
	
	public TileRenderWindMill(){
//		small = new ModelWindMillSmall();
//		medium = new ModelWindMillMedium();
		big = new ModelWindMillBig();
//		rotor = new ModelShaft();
//		gear = new ModelGear();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity t, double x, double y, double z, float frames) {
		TileWindTurbine tile = (TileWindTurbine) t;
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 2.5F, (float) z + 0.5F);
		GL11.glRotatef(180, 0, 0, 1);
		
		tile.rotation -= tile.getDelta()*(tile.speed);
		if(tile.rotation < -1000) tile.rotation += 1000;
		if(tile.rotation < -10000)tile.rotation = 0;
		
		if(tile.facing == MgDirection.NORTH){
			GL11.glRotatef(90, 1, 0, 0);
			GL11.glTranslatef(0,-2,-2);
		}else if(tile.facing == MgDirection.SOUTH){
			GL11.glRotatef(-90, 1, 0, 0);
			GL11.glTranslatef(0,-2,2);
		}else if(tile.facing == MgDirection.WEST){
			GL11.glRotatef(90, 0, 0, 1);
			GL11.glTranslatef(2,-2,0);
		}else if(tile.facing == MgDirection.EAST){
			GL11.glRotatef(-90, 0, 0, 1);
			GL11.glTranslatef(-2,-2,0);
		}
		GL11.glRotatef(tile.rotation*0.36f, 0, 1, 0);
		
		if(tile.turbine != -1){
			float p = tile.getTurbineScale();
			GL11.glScalef(p, 1, p);
//			RenderUtil.bindTexture(new ResourceLocation("magneticraft:textures/misc/windmill_s.png"));
//			small.renderStatic(0.0625f);
//		}else if(tile.turbine == 1){
//			RenderUtil.bindTexture(new ResourceLocation("magneticraft:textures/misc/windmill_m.png"));
//			medium.renderStatic(0.0625f);
//		}else if(tile.turbine == 2){
			
			RenderUtil.bindTexture(new ResourceLocation("magneticraft:textures/misc/windmill_b.png"));
			big.renderStatic(0.0625f);
		}
		GL11.glPopMatrix();
	}

//	private void renderShaft(TileWindMill tile, double x, double y, double z) {
//		GL11.glPushMatrix();
//		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
//		GL11.glRotatef(180, 0, 0, 1);
//		GL11.glRotatef(180, 0, 1, 0);
//
//		if(tile.facing == MgDirection.NORTH){
//			GL11.glRotatef(180, 0, 1, 0);
//		}else if(tile.facing == MgDirection.WEST){
//			GL11.glRotatef(90, 0, 1, 0);
//		}else if(tile.facing == MgDirection.EAST){
//			GL11.glRotatef(-90, 0, 1, 0);
//		}
//		
//		RenderUtil.bindTexture(ModelTextures.WINDMILL);
//		rotor.renderStatic(0.0625f);
//		
//		rotor.renderDynamic(0.0625f,0,(float) Math.toRadians(tile.rotation*0.36f));
//		RenderUtil.bindTexture(ModelTextures.GEAR);
//		
////		tile.rotation = 0;
//		float pixel = 0.0625f;
//		GL11.glRotatef(90, 0, 1, 0);
//		float i = 1+pixel;
//		GL11.glScalef(i, i, i);
//		GL11.glTranslatef(0,-(i-1),0);
//		gear.renderDynamic(pixel,(float) Math.toRadians(-tile.rotation*0.36));
//		GL11.glTranslatef(0.0F, 0.2F, 0.28F);
//		gear.renderDynamic(0.0625f,(float) Math.toRadians(tile.rotation*0.36));
//		GL11.glTranslatef(-pixel*2,0,0);
//		gear.renderDynamic(0.0625f,(float) Math.toRadians(tile.rotation*0.36*4));
//		GL11.glRotatef(90, 0, 1, 0);
//		GL11.glTranslatef(-pixel*5,-pixel*3,pixel);
//		rotor.renderDynamic(pixel,1,(float) Math.toRadians(tile.rotation*0.36));
//		GL11.glTranslatef(pixel*9.5f, 0, -pixel*7);
//		rotor.renderDynamic(pixel,2,(float) Math.toRadians(-tile.rotation*0.36*4));
//		GL11.glRotatef(-90, 0, 1, 0);
//		GL11.glTranslatef(pixel*6,0,0);
//		gear.renderDynamic(0.0625f,(float) Math.toRadians(-tile.rotation*0.36*4));
//		GL11.glPopMatrix();
//		
//
//		
//		GL11.glPushMatrix();
//		GL11.glTranslatef((float) x + 0.5F, (float) y - 0.6895F+1f, (float) z + 0.19F);
//		GL11.glRotatef(tile.rotation*0.72f, 1, 0, 0);
//		GL11.glTranslatef(0,-1,0);
//		gear.renderDynamic(0.0625f);
//		GL11.glTranslatef(-0.125f,0,0);
//		gear.renderDynamic(0.0625f);
//		GL11.glTranslatef(0.0625f,0,0);
//		GL11.glRotatef(90,0,1,0);
//		GL11.glTranslatef(-0.3125f,-0.1875f,0);
//		rotor.renderDynamic(0.0625f,1);
//		GL11.glPopMatrix();
//		
//		GL11.glPushMatrix();
//		GL11.glTranslatef((float) x, (float) y + 0.5F, (float) z + 0.5F);
//		GL11.glRotatef((1000-tile.rotation)*1.08f, 1, 0, 0);
//		GL11.glRotatef(90,0,1,0);
//		GL11.glTranslatef(0,-1,0);
//		rotor.renderDynamic(0.0625f,2);
//		GL11.glRotatef(90,0,1,0);
//		GL11.glTranslatef(0.0625f-0.5f,0,0);
//		gear.renderDynamic(0.0625f);
//		GL11.glPopMatrix();
//	}
}