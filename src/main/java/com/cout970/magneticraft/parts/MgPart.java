package com.cout970.magneticraft.parts;

import codechicken.lib.raytracer.IndexedCuboid6;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Vector3;
import codechicken.multipart.JNormalOcclusion;
import codechicken.multipart.NormalOcclusionTest;
import codechicken.multipart.TMultiPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.LinkedList;
import java.util.List;

public abstract class MgPart extends TMultiPart implements JNormalOcclusion {

    public static float pixel = 1f / 16f;
    public Item item;

    public MgPart(Item i) {
        item = i;
    }

    public abstract List<Cuboid6> getOcclusionCubes();

    public abstract List<Cuboid6> getCollisionCubes();

    public abstract void renderPart(Vector3 pos);

    @Override
    public String getType() {
        return item.getUnlocalizedName();
    }

    public World W() {
        return world();
    }

    public int X() {
        return x();
    }

    public int Y() {
        return y();
    }

    public int Z() {
        return z();
    }

    public TileEntity Parent() {
        return tile();
    }

    @Override
    public boolean occlusionTest(TMultiPart part) {
        return NormalOcclusionTest.apply(this, part);
    }

    @Override
    public Iterable<Cuboid6> getOcclusionBoxes() {
        return getOcclusionCubes();
    }

    @Override
    public Iterable<ItemStack> getDrops() {
        LinkedList<ItemStack> items = new LinkedList<>();
        items.add(new ItemStack(item, 1));
        return items;
    }

    @Override
    public float getStrength(MovingObjectPosition hit, EntityPlayer p) {
        return 10f;
    }

    public void onNeigUpdate() {
        onNeighborChanged();
    }

    @Override
    public Iterable<IndexedCuboid6> getSubParts() {
        Iterable<Cuboid6> boxList = getCollisionBoxes();
        LinkedList<IndexedCuboid6> partList = new LinkedList<>();
        for (Cuboid6 c : boxList)
            partList.add(new IndexedCuboid6(0, c));
        return partList;
    }

    @Override
    public Iterable<Cuboid6> getCollisionBoxes() {
        return getCollisionCubes();
    }

    @Override
    public void renderDynamic(Vector3 pos, float frame, int pass) {
        if (pass == 0) {
            renderPart(pos);
        }
    }

    @Override
    public ItemStack pickItem(MovingObjectPosition hit) {
        return new ItemStack(item, 1);
    }
}
