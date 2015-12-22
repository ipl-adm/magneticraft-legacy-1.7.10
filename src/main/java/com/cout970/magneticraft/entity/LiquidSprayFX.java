package com.cout970.magneticraft.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

//Taken from AgriCraft (https://github.com/InfinityRaider/AgriCraft/blob/1.7.10/src/main/java/com/InfinityRaider/AgriCraft/renderers/particles/LiquidSprayFX.java)

@SideOnly(Side.CLIENT)
public class LiquidSprayFX extends ExtendedFX {
    public LiquidSprayFX(World world, double x, double y, double z, float scale, float gravity, Vec3 vector, Fluid fluid) {
        super(world, x, y, z, scale, gravity, vector, fluid.getStillIcon());
        this.particleMaxAge = 15;
        this.setSize(0.2f, 0.2f);
        this.noClip = false;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (onGround) {
            //setDead();
            particleAge = Math.max(particleAge, 13);
        }
    }
}