package com.cout970.magneticraft.client.tilerender;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.IElectricPole;
import com.cout970.magneticraft.api.util.VecDouble;
import com.cout970.magneticraft.client.model.ModelElectricalPoleTier1;
import com.cout970.magneticraft.tileentity.TileElectricPoleTier1;
import com.cout970.magneticraft.util.Log;
import com.cout970.magneticraft.util.RenderUtil;

public class TileRenderElectricPolrTier1 extends TileEntitySpecialRenderer{
	
	private ModelElectricalPoleTier1 model;
	
	public TileRenderElectricPolrTier1(){
		model = new ModelElectricalPoleTier1();
	}

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y - 2.5F, (float) z + 0.5F);
		GL11.glColor4f(1, 1, 1, 1);
		GL11.glRotatef(180, 0, 0, 1);

		switch(te.getBlockMetadata()-6){
		case 0:
			GL11.glRotatef(90, 0, 1, 0);
			break;
		case 1:
			GL11.glRotatef(-45, 0, 1, 0);
			break;
		case 2: break;
		case 3:
			GL11.glRotatef(-135, 0, 1, 0);
			break;
		case 4:
			GL11.glRotatef(-90, 0, 1, 0);
			break;
		case 5:
			GL11.glRotatef(-45, 0, 1, 0);
			break;
		case 6: break;
		case 7:
			GL11.glRotatef(45, 0, 1, 0);
			break;
		}

		RenderUtil.bindTexture(ModelTextures.POLE_TIER1);
		model.renderStatic(0.0625f);
		GL11.glPopMatrix();

		TileElectricPoleTier1 pole1 = (TileElectricPoleTier1) te;
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y, (float) z);
		GL11.glColor4f(1, 1, 1, 1);
		if(pole1.glList == -1){
			pole1.glList = GL11.glGenLists(1);
			GL11.glNewList(pole1.glList, GL11.GL_COMPILE_AND_EXECUTE);

			int count = pole1.getWireConnector().length;
			for(IElectricPole pole2 : pole1.getConnectedConductors()){
				VecDouble off = new VecDouble(-te.xCoord, -te.yCoord, -te.zCoord);
				if(pole2.getWireConnector().length != count){
					continue;
				}
				VecDouble dist = new VecDouble((TileEntity)pole2).add(off);
				RenderUtil.bindTexture(ModelTextures.PIPE_IN_BRONCE);
				for(int i = 0; i < count ;i++){
					VecDouble a = pole1.getWireConnector()[i], b = pole2.getWireConnector()[i];
					b.add(dist);//b relative to a
					VecDouble ab = b.copy().add(a.getOpposite());//(b-a)
					double lenght = ab.mag();//distance between a and b
					VecDouble mid = ab.copy(); //(b-a)
					mid.multiply(0.5).add(a);//(b-a)/2 + a
					double lowPoint = mid.getY() - lenght * 0.05;//height of the middel point less the weight
					double quallity = 15;
					for(int p = 0; p < quallity; p++){
						double adv1 = p/quallity, adv2 = (p+1)/quallity;
						drawLine(new VecDouble(a.getX()+ab.getX()*adv1, interpolate(a.getY(), lowPoint, b.getY(), adv1), a.getZ()+ab.getZ()*adv1),
								new VecDouble(a.getX()+ab.getX()*adv2, interpolate(a.getY(), lowPoint, b.getY(), adv2), a.getZ()+ab.getZ()*adv2),
								0.0625F*0.5F);
					}
				}
			}
			GL11.glEndList();
		}else{
			GL11.glCallList(pole1.glList);
		}
		GL11.glPopMatrix();
	}

	private double interpolate(double fa, double fb, double fc, double x) {
		double a = 0, b = 0.5, c = 1;
		double L0 = ((x - b)/(a-b))*((x - c)/(a-c));
		double L1 = ((x - a)/(b-a))*((x - c)/(b-c));
		double L2 = ((x - a)/(c-a))*((x - b)/(c-b));
		return fa*L0+fb*L1+fc*L2;
	}

	private void drawLine(VecDouble a, VecDouble b, float f) {
		Tessellator t = Tessellator.instance;
		float w = f/2;
		GL11.glEnable(GL11.GL_CULL_FACE);
		t.startDrawing(GL11.GL_QUADS);
		t.addVertex(a.getX(), a.getY()-w, a.getZ());
		t.addVertex(a.getX(), a.getY()+w, a.getZ());
		t.addVertex(b.getX(), b.getY()+w, b.getZ());
		t.addVertex(b.getX(), b.getY()-w, b.getZ());
		
		t.addVertex(a.getX(), a.getY(), a.getZ()-w);
		t.addVertex(a.getX(), a.getY(), a.getZ()+w);
		t.addVertex(b.getX(), b.getY(), b.getZ()+w);
		t.addVertex(b.getX(), b.getY(), b.getZ()-w);
		
		t.addVertex(a.getX()-w, a.getY(), a.getZ());
		t.addVertex(a.getX()+w, a.getY(), a.getZ());
		t.addVertex(b.getX()+w, b.getY(), b.getZ());
		t.addVertex(b.getX()-w, b.getY(), b.getZ());
		t.draw();
		GL11.glDisable(GL11.GL_CULL_FACE);
	}
}