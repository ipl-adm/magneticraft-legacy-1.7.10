package com.cout970.magneticraft.client.tilerender;

import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.client.model.ModelInserter;
import com.cout970.magneticraft.client.model.ModelInserter;
import com.cout970.magneticraft.tileentity.TileInserter;
import com.cout970.magneticraft.util.Log;
import com.cout970.magneticraft.util.RenderUtil;

public class TileRenderInserter extends TileEntitySpecialRenderer{

	private ModelInserter model;
	private final RenderItem RenderItemMG;
	private final EntityItem itemEntity = new EntityItem(null);

	public TileRenderInserter(){
		model = new ModelInserter();
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
		TileInserter tile = (TileInserter) t;
		ItemStack item = tile.getInv().getStackInSlot(0);
		
		GL11.glRotatef(180, 0, 0, 1);
		
		if(tile.getDir() == MgDirection.NORTH){
			GL11.glRotatef(180, 0, 1, 0);
		}else if(tile.getDir() == MgDirection.WEST){
			GL11.glRotatef(90, 0, 1, 0);
		}else if(tile.getDir() == MgDirection.EAST){
			GL11.glRotatef(-90, 0, 1, 0);
		}

		RenderUtil.bindTexture(ModelTextures.INSERTER);
		model.renderStatic(0.0625f);
		boolean large = true;
		float[] array = getAngles(tile.counter, large);

		model.renderDynamic(0.0625f, array[0], array[1], array[2], array[3],RenderItemMG,itemEntity,item);
		
		GL11.glPopMatrix();
	}

	//  0;-85;90  catch near
	// 45;-45;100 rotagte and standart 
	//-45;-90;135 catch far

	public static float[] getAngles(float counter, boolean large) {
		float a = 0,b=0,c=0,d0;
		float[] result = new float[4];
		result[3] = 0;
		if(counter <= 180){
			d0 = (counter)/180f;
			result[0] = 45*d0;
			result[1] = -85+40*d0;
			result[2] = 90+10*d0;
		}else if(counter <= 360){
			result[0] = 45;
			result[1] = -45;
			result[2] = 100;
			d0 = (counter-180);
			GL11.glRotatef(d0, 0, 1, 0);
		}else if(counter <= 540){
			d0 = (counter-360)/180f;
			result[0] = 45-45*d0;
			result[1] = -45-40*d0;
			result[2] = 100-10*d0;
			GL11.glRotatef(180, 0, 1, 0);
		}else if(counter <= 720){
			d0 = (counter-540)/180f;
			result[0] = 45*d0;
			result[1] = -85+40*d0;
			result[2] = 90+10*d0;
			GL11.glRotatef(180, 0, 1, 0);
		}else if(counter <= 900){
			result[0] = 45;
			result[1] = -45;
			result[2] = 100;
			d0 = (counter-720);
			GL11.glRotatef(180+d0, 0, 1, 0);
		}else if(counter <= 1080){
			d0 = (counter-900)/180f;
			result[0] = 45-45*d0;
			result[1] = -45-40*d0;
			result[2] = 100-10*d0;
		}else{
			result[0] = 45;
			result[1] = -45;
			result[2] = 100;
		}
		return result;
	}
}
