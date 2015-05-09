package com.cout970.magneticraft.client.tilerender;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;

import org.lwjgl.opengl.GL11;

import codechicken.lib.render.RenderUtils;
import codechicken.lib.vec.Cuboid6;

import com.cout970.magneticraft.api.conveyor.IConveyor;
import com.cout970.magneticraft.api.conveyor.ItemBox;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecDouble;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.block.BlockConveyorLow;
import com.cout970.magneticraft.client.model.ModelConveyorBelt;
import com.cout970.magneticraft.tileentity.TileConveyorBelt;
import com.cout970.magneticraft.util.RenderUtil;

public class TileRenderConveyorBelt extends TileEntitySpecialRenderer{

	private final RenderItem RenderItemMG;
	private final EntityItem itemEntity = new EntityItem(null);
	private ModelConveyorBelt model;
	
	public TileRenderConveyorBelt(){
		model = new ModelConveyorBelt();
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
		
		for(ItemBox b : tile.getSideLane(true).content){
			if(b == null)continue;
			renderItemBox(b,tile);
		}
		for(ItemBox b : tile.getSideLane(false).content){
			if(b == null)continue;
			renderItemBox(b,tile);
		}
		GL11.glColor4f(1, 1, 1, 1);
//		if(tile.getDir() == MgDirection.NORTH) GL11.glRotatef(90, 0, 1, 0);
//		if(tile.getDir() == MgDirection.SOUTH) GL11.glRotatef(-90, 0, 1, 0);
//		if(tile.getDir() == MgDirection.WEST) GL11.glRotatef(180, 0, 1, 0);
//		for(int i = 0; i<16;i++){
//			if(!tile.getSideLane(false).spaces[i]){
//				GL11.glPushMatrix();
//				GL11.glScalef(1/16f, 1/16f, 1/16f);
//				GL11.glTranslatef(i-8, -20, 3);
//				RenderUtil.bindTexture(TextureMap.locationBlocksTexture);
//				RenderUtils.drawCuboidOutline(new Cuboid6(0,0,0,1,1,1));
//				GL11.glPopMatrix();
//			}
//			if(!tile.getSideLane(true).spaces[i]){
//				GL11.glPushMatrix();
//				GL11.glScalef(1/16f, 1/16f, 1/16f);
//				GL11.glTranslatef(i-8, -20, -4);
//				RenderUtil.bindTexture(TextureMap.locationBlocksTexture);
//				RenderUtils.drawCuboidOutline(new Cuboid6(0,0,0,1,1,1));
//				GL11.glPopMatrix();
//			}
//		}
//		if(tile.getDir() == MgDirection.NORTH) GL11.glRotatef(-90, 0, 1, 0);
//		if(tile.getDir() == MgDirection.SOUTH) GL11.glRotatef(90, 0, 1, 0);
//		if(tile.getDir() == MgDirection.WEST) GL11.glRotatef(180, 0, 1, 0);
		
		GL11.glRotatef(180, 1, 0, 0);
		GL11.glRotatef(180, 0, 1, 0);
		
		if(tile.getDir() == MgDirection.NORTH){
			GL11.glRotatef(180, 0, 1, 0);
		}else if(tile.getDir() == MgDirection.WEST){
			GL11.glRotatef(90, 0, 1, 0);
		}else if(tile.getDir() == MgDirection.EAST){
			GL11.glRotatef(-90, 0, 1, 0);
		}
		int sides = 3;
		VecInt vec = tile.getDir().step(MgDirection.UP).getVecInt();
		vec.add(new VecInt(tile));
		TileEntity conveyor = tile.getWorldObj().getTileEntity(vec.getX(), vec.getY(), vec.getZ());
		if(conveyor instanceof IConveyor){
			if(((IConveyor) conveyor).getDir() == tile.getDir().step(MgDirection.UP).opposite()){
			sides &= 2; 
			}
		}
		
		vec = tile.getDir().step(MgDirection.DOWN).getVecInt();
		vec.add(new VecInt(tile));
		conveyor = tile.getWorldObj().getTileEntity(vec.getX(), vec.getY(), vec.getZ());
		if(conveyor instanceof IConveyor){
			if(((IConveyor) conveyor).getDir() == tile.getDir().step(MgDirection.DOWN).opposite()){
					sides &= 1; 
			}
		}
		
		RenderUtil.bindTexture(ModelTextures.CONVEYOR_BELT_LOW);
		model.renderStatic_block(0.0625f);
		model.renderDynamic(0.0625f,sides);
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		Tessellator tess = Tessellator.instance;
		GL11.glTranslatef((float) x + 0.0F, (float) y - 13/16F, (float) z + 0.0F);
		if(tile.getDir() == MgDirection.NORTH){
			GL11.glRotatef(180, 0, 1, 0);
			GL11.glTranslatef(-1,0,-1);
		}else if(tile.getDir() == MgDirection.WEST){
			GL11.glRotatef(-90, 0, 1, 0);
			GL11.glTranslatef(0,0,-1);
		}else if(tile.getDir() == MgDirection.EAST){
			GL11.glRotatef(90, 0, 1, 0);
			GL11.glTranslatef(-1,0,0);
		}
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
		GL11.glPopMatrix();
	}

	public static void renderFaceTop(IIcon i, int x, int y, int z){
		Tessellator t = Tessellator.instance;
		t.addVertexWithUV(x,y+1,z, i.getInterpolatedU(0), i.getInterpolatedV(0));
		t.addVertexWithUV(x,y+1,z+1, i.getInterpolatedU(0), i.getInterpolatedV(16));
		t.addVertexWithUV(x+1,y+1,z+1, i.getInterpolatedU(16), i.getInterpolatedV(16));
		t.addVertexWithUV(x+1,y+1,z, i.getInterpolatedU(16), i.getInterpolatedV(0));
	}
	
	public static void renderFaceFront(IIcon i, int x, int y, int z){
		Tessellator t = Tessellator.instance;
		float k = 13/16f;
		t.addVertexWithUV(x,y+k,z, i.getInterpolatedU(0), i.getInterpolatedV(0));
		t.addVertexWithUV(x,y+3/16f+k,z, i.getInterpolatedU(0), i.getInterpolatedV(3));
		t.addVertexWithUV(x+1,y+3/16f+k,z, i.getInterpolatedU(16), i.getInterpolatedV(3));
		t.addVertexWithUV(x+1,y+k,z, i.getInterpolatedU(16), i.getInterpolatedV(0));
	}
	
	public static void renderFaceBack(IIcon i, int x, int y, int z){
		Tessellator t = Tessellator.instance;
		float k = 13/16f;
		t.addVertexWithUV(x,y+k,z+1, i.getInterpolatedU(16), i.getInterpolatedV(3));
		t.addVertexWithUV(x+1,y+k,z+1, i.getInterpolatedU(0), i.getInterpolatedV(3));
		t.addVertexWithUV(x+1,y+3/16f+k,z+1, i.getInterpolatedU(0), i.getInterpolatedV(0));
		t.addVertexWithUV(x,y+3/16f+k,z+1, i.getInterpolatedU(16), i.getInterpolatedV(0));
	}
	
	public static void renderFaceBottom(int x, int y, int z){
		Tessellator t = Tessellator.instance;
		float k = 1/64f;
		float h = 13/16f;
		t.addVertexWithUV(x+15/16f,y+h,z, 	k*30,	k*21);
		t.addVertexWithUV(x+15/16f,y+h,z+1,k*44,	k*21);
		t.addVertexWithUV(x+1/16f,y+h,z+1, 	k*44,	k*37);
		t.addVertexWithUV(x+1/16f,y+h,z, 	k*30,	k*37);
	}
	
	private void renderItemBox(ItemBox b, TileConveyorBelt c) {
		GL11.glPushMatrix();
		float d = (float) (b.getPosition())*0.0625f-0.5f+0.125f;
		float renderScale = 0.7f;
		VecDouble v = new VecDouble(c.getDir().step(MgDirection.UP).getVecInt());
		
		GL11.glTranslatef(0, -4.5f*0.25F, 0);
		if(b.isOnLeft()){
			v.multiply(0.7*0.4);
		}else{
			v.multiply(-0.7*0.4);
		}
		GL11.glTranslated(v.getX(),0,v.getZ());
		GL11.glTranslatef(c.getDir().getOffsetX()*d, 0, c.getDir().getOffsetZ()*d);
		GL11.glScalef(renderScale, renderScale, renderScale);
		itemEntity.setEntityItemStack(b.getContent());
		RenderItemMG.doRender(itemEntity, 0, 0, 0, 0, 0);
		GL11.glPopMatrix();
	}
}
