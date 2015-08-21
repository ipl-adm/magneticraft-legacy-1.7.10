package com.cout970.magneticraft.client.tilerender;

import org.lwjgl.opengl.GL11;

import com.cout970.magneticraft.client.model.ModelHammerTable;
import com.cout970.magneticraft.tileentity.TileHammerTable;
import com.cout970.magneticraft.util.RenderUtil;

import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;

public class TileRenderHammerTable extends TileEntitySpecialRenderer{

	private final RenderItem RenderItemMG;
	private final EntityItem itemEntity = new EntityItem(null);
	private ModelHammerTable model;

	public TileRenderHammerTable(){
		model = new ModelHammerTable();
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
		GL11.glRotatef(180, 0, 0, 1);
		TileHammerTable tile = (TileHammerTable) t;
		
		RenderUtil.bindTexture(ModelTextures.HAMMER_TABLE);
		model.renderStatic(0.0625f);
		if(tile.getInput() != null){
			GL11.glRotatef(180, 0, 0, 1);
			GL11.glRotatef(19, 0, 1, 0);
			GL11.glTranslatef(0, -0.55F, 0);
			itemEntity.setEntityItemStack(tile.getInput());
			RenderItemMG.doRender(itemEntity, 0, 0, 0, 0, 0);
		}
		GL11.glPopMatrix();
	}
}
