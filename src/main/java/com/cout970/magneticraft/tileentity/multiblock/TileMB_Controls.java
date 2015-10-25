package com.cout970.magneticraft.tileentity.multiblock;

import codechicken.lib.raytracer.IndexedCuboid6;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Vector3;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.tileentity.multiblock.controllers.TileSifter;
import com.cout970.magneticraft.util.multiblock.MB_Register;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;

import java.util.ArrayList;
import java.util.List;

public class TileMB_Controls extends TileMB_Base {

    public boolean onClick(MovingObjectPosition hit) {
        if (multi != null && multi.getID() == MB_Register.ID_SIFTER) {
            TileEntity tile = control.getTileEntity(worldObj);
            if (!(tile instanceof TileSifter)) return false;
            Vector3 vec = new Vector3(hit.hitVec);
            List<IndexedCuboid6> box = getBoundingBoxes();
            for (IndexedCuboid6 aBox : box) {
                if (isHited(aBox, vec) && aBox.data != Integer.valueOf(0)) {
                    ((TileSifter) tile).switchClick((Integer) aBox.data - 1);
                    return true;
                }
            }
        }
        return false;
    }

    public List<IndexedCuboid6> getBoundingBoxes() {
        ArrayList<IndexedCuboid6> list = new ArrayList<>();
        float dist = 2 / 16F;

        if (multi != null && multi.getID() == MB_Register.ID_SIFTER) {
            TileEntity tile = control.getTileEntity(worldObj);
            if (tile instanceof TileSifter) {
                if (((TileSifter) tile).getDirection() == MgDirection.WEST) {
                    if (tile.xCoord == xCoord && tile.zCoord == zCoord) {
                        list.add(new IndexedCuboid6(0, new Cuboid6(xCoord + dist, yCoord, zCoord + dist, xCoord + 1, yCoord + 1, zCoord + 1)));
                        list.add(new IndexedCuboid6(1, new Cuboid6(xCoord + 5 / 16F, yCoord + 3 / 16F, zCoord, xCoord + 11 / 16F, yCoord + 13 / 16F, zCoord + dist)));
                        list.add(new IndexedCuboid6(2, new Cuboid6(xCoord, yCoord + 3 / 16F, zCoord + 5 / 16F, xCoord + dist, yCoord + 13 / 16F, zCoord + 11 / 16F)));
                        list.add(new IndexedCuboid6(3, new Cuboid6(xCoord, yCoord + 3 / 16F, zCoord + 13 / 16F, xCoord + dist, yCoord + 13 / 16F, zCoord + 1)));
                    } else {
                        list.add(new IndexedCuboid6(0, new Cuboid6(xCoord + dist, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1)));
                        list.add(new IndexedCuboid6(3, new Cuboid6(xCoord, yCoord + 3 / 16F, zCoord, xCoord + dist, yCoord + 13 / 16F, zCoord + 3 / 16F)));
                        list.add(new IndexedCuboid6(4, new Cuboid6(xCoord, yCoord + 3 / 16F, zCoord + 5 / 16F, xCoord + dist, yCoord + 13 / 16F, zCoord + 11 / 16F)));
                    }
                } else if (((TileSifter) tile).getDirection() == MgDirection.EAST) {
                    if (tile.xCoord == xCoord && tile.zCoord == zCoord) {
                        list.add(new IndexedCuboid6(0, new Cuboid6(xCoord, yCoord, zCoord, xCoord + 1 - dist, yCoord + 1, zCoord + 1 - dist)));
                        list.add(new IndexedCuboid6(1, new Cuboid6(xCoord + 5 / 16F, yCoord + 3 / 16F, zCoord + 1 - dist, xCoord + 11 / 16F, yCoord + 13 / 16F, zCoord + 1)));
                        list.add(new IndexedCuboid6(2, new Cuboid6(xCoord + 1 - dist, yCoord + 3 / 16F, zCoord + 5 / 16F, xCoord + 1, yCoord + 13 / 16F, zCoord + 11 / 16F)));
                        list.add(new IndexedCuboid6(3, new Cuboid6(xCoord + 1 - dist, yCoord + 3 / 16F, zCoord, xCoord + 1, yCoord + 13 / 16F, zCoord + 3 / 16F)));
                    } else {
                        list.add(new IndexedCuboid6(0, new Cuboid6(xCoord, yCoord, zCoord, xCoord + 1 - dist, yCoord + 1, zCoord + 1)));
                        list.add(new IndexedCuboid6(3, new Cuboid6(xCoord + 1 - dist, yCoord + 3 / 16F, zCoord + 1 - 3 / 16F, xCoord + 1, yCoord + 13 / 16F, zCoord + 1)));
                        list.add(new IndexedCuboid6(4, new Cuboid6(xCoord + 1 - dist, yCoord + 3 / 16F, zCoord + 5 / 16F, xCoord + 1, yCoord + 13 / 16F, zCoord + 11 / 16F)));
                    }
                } else if (((TileSifter) tile).getDirection() == MgDirection.NORTH) {
                    if (tile.xCoord == xCoord && tile.zCoord == zCoord) {
                        list.add(new IndexedCuboid6(0, new Cuboid6(xCoord, yCoord, zCoord + dist, xCoord + 1 - dist, yCoord + 1, zCoord + 1)));
                        list.add(new IndexedCuboid6(1, new Cuboid6(xCoord + 1, yCoord + 3 / 16F, zCoord + 5 / 16F, xCoord + 1 - dist, yCoord + 13 / 16F, zCoord + 11 / 16F)));
                        list.add(new IndexedCuboid6(2, new Cuboid6(xCoord + 5 / 16F, yCoord + 3 / 16F, zCoord, xCoord + 11 / 16F, yCoord + 13 / 16F, zCoord + dist)));
                        list.add(new IndexedCuboid6(3, new Cuboid6(xCoord, yCoord + 3 / 16F, zCoord, xCoord + 1 - 13 / 16F, yCoord + 13 / 16F, zCoord + dist)));
                    } else {
                        list.add(new IndexedCuboid6(0, new Cuboid6(xCoord, yCoord, zCoord + dist, xCoord + 1, yCoord + 1, zCoord + 1)));
                        list.add(new IndexedCuboid6(3, new Cuboid6(xCoord + 1 - 3 / 16F, yCoord + 3 / 16F, zCoord, xCoord + 1, yCoord + 13 / 16F, zCoord + dist)));
                        list.add(new IndexedCuboid6(4, new Cuboid6(xCoord + 5 / 16F, yCoord + 3 / 16F, zCoord, xCoord + 11 / 16F, yCoord + 13 / 16F, zCoord + dist)));
                    }
                } else {//south
                    if (tile.xCoord == xCoord && tile.zCoord == zCoord) {
                        list.add(new IndexedCuboid6(0, new Cuboid6(xCoord + dist, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1 - dist)));
                        list.add(new IndexedCuboid6(1, new Cuboid6(xCoord, yCoord + 3 / 16F, zCoord + 5 / 16F, xCoord + dist, yCoord + 13 / 16F, zCoord + 11 / 16F)));
                        list.add(new IndexedCuboid6(2, new Cuboid6(xCoord + 5 / 16F, yCoord + 3 / 16F, zCoord + 1, xCoord + 11 / 16F, yCoord + 13 / 16F, zCoord + 1 - dist)));
                        list.add(new IndexedCuboid6(3, new Cuboid6(xCoord + 1 - 3 / 16F, yCoord + 3 / 16F, zCoord + 1 - dist, xCoord + 1, yCoord + 13 / 16F, zCoord + 1)));
                    } else {
                        list.add(new IndexedCuboid6(0, new Cuboid6(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1 - dist)));
                        list.add(new IndexedCuboid6(3, new Cuboid6(xCoord, yCoord + 3 / 16F, zCoord + 1 - dist, xCoord + 3 / 16F, yCoord + 13 / 16F, zCoord + 1)));
                        list.add(new IndexedCuboid6(4, new Cuboid6(xCoord + 5 / 16F, yCoord + 3 / 16F, zCoord + 1, xCoord + 11 / 16F, yCoord + 13 / 16F, zCoord + 1 - dist)));
                    }
                }
            } else {
                list.add(new IndexedCuboid6(0, new Cuboid6(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1)));
            }
        } else {
            list.add(new IndexedCuboid6(0, new Cuboid6(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1)));
        }
        return list;
    }

    public static boolean isHited(Cuboid6 c, Vector3 v) {
        if (c == null || v == null) return false;
        if ((float) c.max.y == (float) v.y || (float) c.min.y == (float) v.y) {
            if ((c.min.x <= v.x) && (c.max.x >= v.x)) {
                if ((c.min.z <= v.z) && (c.max.z >= v.z)) {
                    return true;
                }
            }
        }
        if ((float) c.max.x == (float) v.x || (float) c.min.x == (float) v.x) {
            if ((c.min.y <= v.y) && (c.max.y >= v.y)) {
                if ((c.min.z <= v.z) && (c.max.z >= v.z)) {
                    return true;
                }
            }
        }
        if ((float) c.max.z == (float) v.z || (float) c.min.z == (float) v.z) {
            if ((c.min.x <= v.x) && (c.max.x >= v.x)) {
                if ((c.min.y <= v.y) && (c.max.y >= v.y)) {
                    return true;
                }
            }
        }
        return false;
    }
}
