package com.cout970.magneticraft.client.tilerender;

import codechicken.lib.vec.Vector3;
import com.cout970.magneticraft.client.model.ModelBrassPipe;
import com.cout970.magneticraft.parts.fluid.PartBrassPipe;
import com.cout970.magneticraft.util.RenderUtil;
import org.lwjgl.opengl.GL11;

public class TileRenderBrassPipe {

    private ModelBrassPipe model = new ModelBrassPipe();

    public void render(PartBrassPipe part, Vector3 pos) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) pos.x + 0.5F, (float) pos.y - 0.5F, (float) pos.z + 0.5F);
        GL11.glColor4f(1, 1, 1, 1);
        RenderUtil.bindTexture(ModelTextures.BRASS_PIPE);
        model.renderStatic(0.0625f);
        model.renderDynamic(0.0625f, (int) part.connections, part.interactions);
        GL11.glPopMatrix();
    }
}
