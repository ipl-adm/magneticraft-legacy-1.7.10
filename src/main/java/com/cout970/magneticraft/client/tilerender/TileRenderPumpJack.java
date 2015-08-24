package com.cout970.magneticraft.client.tilerender;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.client.model.ModelPumpHead;
import com.cout970.magneticraft.client.model.ModelPumpMotor;
import com.cout970.magneticraft.client.model.ModelPumpPiston;
import com.cout970.magneticraft.tileentity.TilePumpJack;
import com.cout970.magneticraft.util.RenderUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class TileRenderPumpJack extends TileEntitySpecialRenderer {

    private ModelPumpHead head;
    private ModelPumpMotor motor;
    private ModelPumpPiston piston;

    public TileRenderPumpJack() {
        head = new ModelPumpHead();
        motor = new ModelPumpMotor();
        piston = new ModelPumpPiston();
    }

    @Override
    public void renderTileEntityAt(TileEntity t, double x, double y, double z, float frames) {
        TilePumpJack tile = (TilePumpJack) t;

        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y - 0.5F, (float) z + 0.5F);
        GL11.glRotatef(180, 1, 0, 0);
        GL11.glTranslatef(0, -2, 0);
        if (tile.facing == MgDirection.SOUTH) {
            GL11.glRotatef(180, 0, 1, 0);
        } else if (tile.facing == MgDirection.EAST) {
            GL11.glRotatef(90, 0, 1, 0);
        } else if (tile.facing == MgDirection.WEST) {
            GL11.glRotatef(-90, 0, 1, 0);
        }
        if (tile.isActive())
            tile.m += 0.25f * (tile.getDelta() / 1E6);
        if (tile.m > 1000) tile.m -= 1000;
        if (tile.m > 10000) tile.m = 0;

        RenderUtil.bindTexture(ModelTextures.PUMP_HEAD);
        head.renderStatic(0.0625f);
        head.renderDynamic(0.0625f, tile.m);

        GL11.glTranslatef(0, 0, -1);
        RenderUtil.bindTexture(ModelTextures.PUMP_MOTOR);
        motor.renderStatic(0.0625f);
        motor.renderDynamic(0.0625f, tile.m);

        GL11.glTranslatef(0, 0, 2f);
        RenderUtil.bindTexture(ModelTextures.PUMP_PISTON);
        piston.renderStatic(0.0625f);
        float angle = (float) Math.cos(Math.toRadians(360) * tile.m / 1000f + Math.toRadians(180)) * 5.5f + 4.5f;
        GL11.glTranslatef(0, 0.95F, 0);
        GL11.glPushMatrix();
        GL11.glRotatef(angle + 180, 1, 0, 0);

        piston.renderDynamic(0.0625f);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }

}
