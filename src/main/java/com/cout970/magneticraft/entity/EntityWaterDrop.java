package com.cout970.magneticraft.entity;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

public class EntityWaterDrop extends EntityFX {
    private boolean falling = false;
    public EntityWaterDrop(World w, double x, double y, double z, double angle, double speed, Fluid f) {
        super(w, x, y, z);
        particleIcon = f.getStillIcon();
        motionX = Math.cos(angle) * speed;
        motionZ = Math.sin(angle) * speed;
        motionY = 0;
        particleGravity = 0;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
    }
}
