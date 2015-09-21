package com.cout970.magneticraft.client.tilerender;

import codechicken.lib.vec.Vector3;
import com.cout970.magneticraft.client.model.ModelWire;
import com.cout970.magneticraft.client.model.ModelWireCorner;
import com.cout970.magneticraft.parts.electric.PartWireCopper;
import com.cout970.magneticraft.parts.electric.wires.*;
import com.cout970.magneticraft.util.RenderUtil;

import static org.lwjgl.opengl.GL11.*;

public class TileRenderWireCopper {

    public ModelWire model = new ModelWire();
    public ModelWireCorner model2 = new ModelWireCorner();

    public void render(PartWireCopper p, Vector3 pos) {
        glPushMatrix();
        glTranslated(pos.x, pos.y, pos.z);
        RenderUtil.bindTexture(ModelTextures.WIRE_COPPER_BLOCK);

        applyRotations(p);
        model.renderStatic(PartWireCopper.pixel);
        if (p.Conn != 0) {
            int source = p.Conn & p.getConMask();
            byte conn = (byte) (source & 0x3F);//mg dirs
            byte aux = (byte) ((source >> 6) & 0x3F);//extended dirs
            byte[] rot;//rotation
            byte ext = 0;//extended dir final
            int fin = 0;//final result

            rot = getRotation(p, conn);//dn up nh sh wt et

            ext |= (1 & (aux >> rot[0]));
            ext |= (1 & (aux >> rot[1])) << 1;
            ext |= (1 & (aux >> rot[2])) << 2;
            ext |= (1 & (aux >> rot[3])) << 3;


            fin |= (1 & (conn >> rot[0]));
            fin |= (1 & (conn >> rot[1])) << 1;
            fin |= (1 & (conn >> rot[2])) << 2;
            fin |= (1 & (conn >> rot[3])) << 3;

            model.renderDynamic(PartWireCopper.pixel, fin);
            RenderUtil.bindTexture(ModelTextures.WIRE_COPPER_CORNER);
            model2.renderDynamic(PartWireCopper.pixel, ext);
        }
        glPopMatrix();
    }

    private byte[] getRotation(PartWireCopper p, int s) {
        if (p instanceof PartWireCopper_Down) {
            return new byte[]{3, 5, 2, 4};//sh et nh wt
        } else if (p instanceof PartWireCopper_Up) {
            return new byte[]{2, 5, 3, 4};//nh et sh wt
        } else if (p instanceof PartWireCopper_East) {
            return new byte[]{3, 1, 2, 0};//sh up nh dn
        } else if (p instanceof PartWireCopper_West) {
            return new byte[]{3, 0, 2, 1};//sh dn nh up
        } else if (p instanceof PartWireCopper_North) {
            return new byte[]{0, 5, 1, 4};//dn et up wt
        } else if (p instanceof PartWireCopper_South) {
            return new byte[]{1, 5, 0, 4};//up et dn wt
        }
        return new byte[]{3, 5, 2, 4};
    }

    private void applyRotations(PartWireCopper p) {
        if (p instanceof PartWireCopper_Down) {
            glTranslatef(0.5f, -1.5f, 0.5f);
            glTranslatef(0f, PartWireCopper.pixel * 3f, 0f);
            return;
        }
        if (p instanceof PartWireCopper_Up) {
            glRotatef(180, 1, 0, 0);
            glTranslatef(0.5f, -2.5f, -0.5f);
            glTranslatef(0f, PartWireCopper.pixel * 3f, 0f);
            return;
        }
        if (p instanceof PartWireCopper_East) {
            glRotatef(90, 0, 0, 1);
            glTranslatef(0.5f, -2.5f, 0.5f);
            glTranslatef(0f, PartWireCopper.pixel * 3f, 0f);
            return;
        }
        if (p instanceof PartWireCopper_West) {
            glRotatef(-90, 0, 0, 1);
            glTranslatef(-0.5f, -1.5f, 0.5f);
            glTranslatef(0f, PartWireCopper.pixel * 3f, 0f);
            return;
        }
        if (p instanceof PartWireCopper_North) {
            glRotatef(90, 1, 0, 0);
            glTranslatef(0.5f, -1.5f, -0.5f);
            glTranslatef(0f, PartWireCopper.pixel * 3f, 0f);
            return;
        }
        if (p instanceof PartWireCopper_South) {
            glRotatef(-90, 1, 0, 0);
            glTranslatef(0.5f, -2.5f, 0.5f);
            glTranslatef(0f, PartWireCopper.pixel * 3f, 0f);
        }
    }

}
