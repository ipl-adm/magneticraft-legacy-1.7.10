package com.cout970.magneticraft.client.tilerender;

import org.lwjgl.opengl.GL11;

import codechicken.lib.vec.Vector3;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.client.model.ModelFluidPipe;
import com.cout970.magneticraft.client.model.ModelFluidPipe_Center;
import com.cout970.magneticraft.client.model.ModelFluidPipe_In;
import com.cout970.magneticraft.parts.micro.PartBroncePipe;
import com.cout970.magneticraft.parts.micro.PartPipe.ConnectionMode;
import com.cout970.magneticraft.util.Log;
import com.cout970.magneticraft.util.RenderUtil;

public class TileRenderPipeBronce {

	private ModelFluidPipe model;
	private ModelFluidPipe_In in;
	private ModelFluidPipe_Center base;

	public TileRenderPipeBronce(){
		model = new ModelFluidPipe();
		in = new ModelFluidPipe_In();
		base = new ModelFluidPipe_Center();
	}
	
	public void render(PartBroncePipe partPipe, Vector3 pos) {
		GL11.glPushMatrix();
		partPipe.updateConnections();
		GL11.glTranslatef((float) pos.x + 0.5F, (float) pos.y + 1.5F, (float) pos.z + 0.5F);
		GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
		boolean[] a = {true,true,true,true,true,true};
		for(int h=0;h<6;h++){
			a[h] = !partPipe.connections[h];
			if(partPipe.side[h] == null || (partPipe.side[h] == ConnectionMode.NOTHING && partPipe.tanks.get(MgDirection.getDirection(h)) != null))
				a[h] = true;
		}
		
		int c = 0;
		for(boolean a1 : a)if(!a1)c++;
		boolean[] t = {true,true,true,true,true,true};
		RenderUtil.bindTexture(ModelTextures.PIPE_BASE_BRONCE);
		if(c != 2)	base.render(0.0625f, t);//render centeter
		else 		base.render(0.0625f, a);//render centeter
		
		//render pipe connections
		for(int j=0;j<a.length;j++)a[j] = !a[j];
		//
		//render in
		RenderUtil.bindTexture(ModelTextures.PIPE_IN_BRONCE);
		in.render(1/16f, a);

		RenderUtil.bindTexture(ModelTextures.PIPE_BASE_2_BRONCE);
		model.render(0.0625f,a);
		//render connectors
		for(MgDirection d : MgDirection.values()){
			if(partPipe.tanks.get(d) != null){
				boolean[] b2 = new boolean[6];
				b2[d.opposite().ordinal()] = true;
				if(partPipe.side[d.ordinal()] == ConnectionMode.OUTPUT)RenderUtil.bindTexture(ModelTextures.PIPE_CONECTION_1_BRONCE);
				else RenderUtil.bindTexture(ModelTextures.PIPE_CONECTION_2_BRONCE);
				if(partPipe.side[d.ordinal()] != ConnectionMode.NOTHING)
					model.render(0.0625f,b2,true);//render connections
			}
		}
		GL11.glPopMatrix();
	}

}