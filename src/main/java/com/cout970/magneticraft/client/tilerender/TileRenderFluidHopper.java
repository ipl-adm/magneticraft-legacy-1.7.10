package com.cout970.magneticraft.client.tilerender;

import com.cout970.magneticraft.client.model.ModelFluidHopper;
import com.cout970.magneticraft.tileentity.TileFluidHopper;
import com.cout970.magneticraft.util.RenderUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class TileRenderFluidHopper extends TileEntitySpecialRenderer {

    private ModelFluidHopper model;

    public TileRenderFluidHopper() {
        model = new ModelFluidHopper();
    }

    @Override
    public void renderTileEntityAt(TileEntity t, double x, double y, double z, float frames) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glRotatef(180, 0, 0, 1);
        TileFluidHopper tile = (TileFluidHopper) t;
        RenderUtil.bindTexture(ModelTextures.HOPPER);
        model.renderStatic(0.0625f, tile.getBlockMetadata());
        GL11.glPopMatrix();
    }
}
