package com.cout970.magneticraft.client.tilerender;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import com.cout970.magneticraft.tileentity.TileCrusher;
import com.cout970.magneticraft.tileentity.TileGrinder;
import com.cout970.magneticraft.util.RenderUtil;
import com.cout970.magneticraft.util.multiblock.MB_Register;
import com.cout970.magneticraft.util.multiblock.Multiblock;

public class TileRenderCrusher extends TileEntitySpecialRenderer{

	@Override
	public void renderTileEntityAt(TileEntity t, double x, double y, double z, float frames) {
		TileCrusher tile = (TileCrusher) t;
		if(tile.drawCounter > 0){
			GL11.glColor4f(1, 1, 1, 1f);
			Multiblock mb = MB_Register.getMBbyID(MB_Register.ID_CRUSHER);
			RenderUtil.renderMultiblock(x,y,z,tile,t,mb);
		}
	}
}
