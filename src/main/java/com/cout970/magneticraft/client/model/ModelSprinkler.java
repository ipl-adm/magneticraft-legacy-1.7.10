package com.cout970.magneticraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSprinkler extends ModelBase {
    //fields
    ModelRenderer Base1;
    ModelRenderer blade[] = new ModelRenderer[8];
    ModelRenderer Base2;

    public ModelSprinkler() {
        textureWidth = 64;
        textureHeight = 64;

        Base1 = new ModelRenderer(this, 0, 9);
        Base1.addBox(-1F, 0F, -1F, 2, 5, 2);
        Base1.setRotationPoint(0F, 10F, 0F);
        Base1.setTextureSize(64, 32);
        Base1.mirror = true;
        setRotation(Base1, 0F, 0F, 0F);
        blade[0] = new ModelRenderer(this, 9, 13);
        blade[0].addBox(1F, 0F, -0.5F, 2, 1, 1);
        blade[0].setRotationPoint(0F, 13.5F, 0F);
        blade[0].setTextureSize(64, 32);
        blade[0].mirror = true;
        setRotation(blade[0], 0F, 0F, 0F);
        blade[1] = new ModelRenderer(this, 9, 13);
        blade[1].addBox(-3F, 0F, -0.5F, 2, 1, 1);
        blade[1].setRotationPoint(0F, 13.5F, 0F);
        blade[1].setTextureSize(64, 32);
        blade[1].mirror = true;
        setRotation(blade[1], 0F, 0F, 0F);
        blade[2] = new ModelRenderer(this, 9, 9);
        blade[2].addBox(-0.5F, 0F, 1F, 1, 1, 2);
        blade[2].setRotationPoint(0F, 13.5F, 0F);
        blade[2].setTextureSize(64, 32);
        blade[2].mirror = true;
        setRotation(blade[2], 0F, 0F, 0F);
        blade[3] = new ModelRenderer(this, 9, 9);
        blade[3].addBox(-0.5F, 0F, -3F, 1, 1, 2);
        blade[3].setRotationPoint(0F, 13.5F, 0F);
        blade[3].setTextureSize(64, 32);
        blade[3].mirror = true;
        setRotation(blade[3], 0F, 0F, 0F);
        blade[4] = new ModelRenderer(this, 9, 9);
        blade[4].addBox(-0.5F, 0F, 1F, 1, 1, 2);
        blade[4].setRotationPoint(0F, 13.5F, 0F);
        blade[4].setTextureSize(64, 32);
        blade[4].mirror = true;
        setRotation(blade[4], 0F, 0.7853982F, 0F);
        blade[5] = new ModelRenderer(this, 9, 9);
        blade[5].addBox(-0.5F, 0F, -3F, 1, 1, 2);
        blade[5].setRotationPoint(0F, 13.5F, 0F);
        blade[5].setTextureSize(64, 32);
        blade[5].mirror = true;
        setRotation(blade[5], 0F, 0.7853982F, 0F);
        blade[6] = new ModelRenderer(this, 9, 13);
        blade[6].addBox(1F, 0F, -0.5F, 2, 1, 1);
        blade[6].setRotationPoint(0F, 13.5F, 0F);
        blade[6].setTextureSize(64, 32);
        blade[6].mirror = true;
        setRotation(blade[6], 0F, 0.7853982F, 0F);
        blade[7] = new ModelRenderer(this, 9, 13);
        blade[7].addBox(-3F, 0F, -0.5F, 2, 1, 1);
        blade[7].setRotationPoint(0F, 13.5F, 0F);
        blade[7].setTextureSize(64, 32);
        blade[7].mirror = true;
        setRotation(blade[7], 0F, 0.7853982F, 0F);
        Base2 = new ModelRenderer(this, 0, 0);
        Base2.addBox(-3F, 0F, -3F, 6, 2, 6);
        Base2.setRotationPoint(0F, 8F, 0F);
        Base2.setTextureSize(64, 32);
        Base2.mirror = true;
        setRotation(Base2, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5);
        Base1.render(f5);
        blade[0].render(f5);
        blade[1].render(f5);
        blade[2].render(f5);
        blade[3].render(f5);
        blade[4].render(f5);
        blade[5].render(f5);
        blade[6].render(f5);
        blade[7].render(f5);
        Base2.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, null);
    }

}
