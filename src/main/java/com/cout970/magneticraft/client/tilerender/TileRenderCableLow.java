package com.cout970.magneticraft.client.tilerender;

import codechicken.lib.vec.Vector3;
import com.cout970.magneticraft.client.model.ModelCableLow;
import com.cout970.magneticraft.parts.electric.PartCableLow;
import com.cout970.magneticraft.util.RenderUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class TileRenderCableLow extends TileEntitySpecialRenderer {

    private ModelCableLow model;

    public TileRenderCableLow() {
        model = new ModelCableLow();
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y,
                                   double z, float scale) {
//		GL11.glPushMatrix();
//		GL11.glTranslatef((float) x + 0.5F, (float) y - 0.5F, (float) z + 0.5F);
//		GL11.glColor4f(1, 1, 1, 1);
//		RenderUtil.bindTexture(ModelTextures.CABLE_LOW);
//		boolean[] s = new boolean[6];
//		for(MgDirection d : MgDirection.values()){
//			s[d.ordinal()] = MgUtils.isConductor(MgUtils.getTileEntity(te, d), 0);
//		}
//			
//		model.render(0.0625f, s);
//		GL11.glPopMatrix();

    }

    public void render(PartCableLow partCableLow, Vector3 pos) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) pos.x + 0.5F, (float) pos.y - 0.5F, (float) pos.z + 0.5F);
        GL11.glColor4f(1, 1, 1, 1);
        RenderUtil.bindTexture(ModelTextures.CABLE_LOW);
//		partCableLow.updateConnections();
        model.render(0.0625f, partCableLow.connections);
        int i;
        if ((i = RenderUtil.getTexture(partCableLow.connections)) != -1) {
            RenderUtil.bindTexture(new ResourceLocation("magneticraft:textures/misc/cables/low_" + i + ".png"));
        } else RenderUtil.bindTexture(ModelTextures.CABLE_LOW);
        model.renderBase(0.0625f);
        GL11.glPopMatrix();
    }
}
