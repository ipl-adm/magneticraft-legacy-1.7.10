package com.cout970.magneticraft.client.tilerender;

import codechicken.lib.vec.Vector3;
import com.cout970.magneticraft.client.model.ModelOpticFiber;
import com.cout970.magneticraft.parts.PartOpticFiber;
import com.cout970.magneticraft.util.RenderUtil;
import org.lwjgl.opengl.GL11;

public class TileRenderOpticFiber {

    public ModelOpticFiber model = new ModelOpticFiber();

    public void render(PartOpticFiber part, Vector3 pos) {
        GL11.glPushMatrix();
        GL11.glTranslated(pos.x, pos.y, pos.z);
        GL11.glRotatef(180, 1, 0, 0);
        GL11.glTranslatef(0.5F, -1.5F, -0.5F);
        RenderUtil.bindTexture(ModelTextures.OPTIC_FIBER);
        model.renderStatic(0.0625f);
        model.renderDynamic(0.0625f, part.connections);
        GL11.glPopMatrix();
    }

}
