package com.cout970.magneticraft.client.tilerender;

import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import com.cout970.magneticraft.api.conveyor.ItemBox;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecDouble;
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
		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		TileConveyorBelt tile = (TileConveyorBelt) t;
		
		for(ItemBox b : tile.getContent(true)){
			if(b == null)continue;
			renderItemBox(b,tile);
		}
		for(ItemBox b : tile.getContent(false)){
			if(b == null)continue;
			renderItemBox(b,tile);
		}
		GL11.glRotatef(180, 1, 0, 0);
		GL11.glRotatef(180, 0, 1, 0);
		
		if(tile.getDir() == MgDirection.NORTH){
			GL11.glRotatef(180, 0, 1, 0);
		}else if(tile.getDir() == MgDirection.WEST){
			GL11.glRotatef(90, 0, 1, 0);
		}else if(tile.getDir() == MgDirection.EAST){
			GL11.glRotatef(-90, 0, 1, 0);
		}
		
		RenderUtil.bindTexture(ModelTextures.BELT);
		model.renderStatic(0.0625f);
		GL11.glPopMatrix();
	}

	private void renderItemBox(ItemBox b, TileConveyorBelt c) {
		GL11.glPushMatrix();
		float d = (float) b.getPosition()-5/12f;
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
