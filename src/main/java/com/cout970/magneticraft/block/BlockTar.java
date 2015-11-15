package com.cout970.magneticraft.block;

import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by cout970 on 15/11/2015.
 */
public class BlockTar extends BlockSimple {

    public BlockTar() {
        super("tar");
    }

    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        entity.setInWeb();
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return null;
    }

    public int quantityDropped(Random p_149745_1_) {
        return 0;
    }

    public int quantityDroppedWithBonus(int fortune, Random ran) {
        return 0;
    }
}
