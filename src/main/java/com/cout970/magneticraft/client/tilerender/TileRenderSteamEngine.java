package com.cout970.magneticraft.client.tilerender;

import com.cout970.magneticraft.client.model.ModelSteamEngine;
import com.cout970.magneticraft.tileentity.TileSteamEngine;
import com.cout970.magneticraft.util.RenderUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class TileRenderSteamEngine extends TileEntitySpecialRenderer {

    private ModelSteamEngine model;

    public TileRenderSteamEngine() {
        model = new ModelSteamEngine();
    }

    @Override
    public void renderTileEntityAt(TileEntity t, double x, double y, double z, float frames) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glRotatef(180, 0, 0, 1);
        TileSteamEngine tile = (TileSteamEngine) t;

        if (tile.getBlockMetadata() % 6 == 3) {
            GL11.glRotatef(180, 0, 1, 0);
        } else if (tile.getBlockMetadata() % 6 == 4) {
            GL11.glRotatef(-90, 0, 1, 0);
        } else if (tile.getBlockMetadata() % 6 == 5) {
            GL11.glRotatef(90, 0, 1, 0);
        }

        RenderUtil.bindTexture(ModelTextures.STEAM_ENGINE);
        model.renderStatic(0.0625f);
        if (tile.isActive())
            tile.animation += tile.getDelta() * 0.5f;
//			tile.animation += tile.getDelta()*0.75f;
        if (tile.animation > 1000) tile.animation -= 1000;
        if (tile.animation > 10000) tile.animation = 0;
        model.renderDynamic(0.0625f, tile.animation);
        GL11.glPopMatrix();
    }
}

/*

public void renderDynamic(float f5, float anim){
	  float pixel = 1/16F;
	  float rot = (float) Math.toRadians((anim/1000f)*360f);
	  float off = (float) Math.cos(rot)*pixel*3F-pixel*3F;
	  float biel = (float) Math.sin(rot)*pixel*4;
	  
	  float rot2 = (float) Math.toRadians(((anim+500)/1000f)*360f);
	  float off2 = (float) Math.cos(rot2)*pixel*3F-pixel*3F;
	  float biel2 = (float) (Math.sin(rot2)*pixel*4);
	  //rot
	  Shape6.rotateAngleZ = rot;
	  Shape8.rotateAngleZ = rot;
	  Shape9.rotateAngleZ = rot;
	  Shape17.rotateAngleZ = rot2;
	  Shape18.rotateAngleZ = rot2;
	  //bielas
	  Shape15.rotateAngleZ = biel;
	  Shape7.rotateAngleZ = biel;
	  Shape16.rotateAngleZ = biel2;
	  Shape14.rotateAngleZ = biel2;
	  //move
	  Shape2.offsetX = off;
	  Shape3.offsetX = off;
	  Shape7.offsetX = off;
	  Shape15.offsetX = off;
	  Shape11.offsetX = off2;
	  Shape12.offsetX = off2;
	  Shape14.offsetX = off2;
	  Shape16.offsetX = off2;
	  
	  //RENDER
	  //pistons
	  Shape2.render(f5);
	  Shape11.render(f5);
	  //riels
	  Shape3.render(f5);
	  Shape12.render(f5);
	  //biela
	  Shape7.render(f5);
	  Shape14.render(f5);
	  //rot
	  Shape6.render(f5);
	  Shape8.render(f5);
	  Shape9.render(f5);
	  Shape15.render(f5);
	  Shape16.render(f5);
	  Shape17.render(f5);
	  Shape18.render(f5);
  }
*/