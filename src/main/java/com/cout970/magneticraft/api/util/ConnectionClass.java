package com.cout970.magneticraft.api.util;


import net.minecraft.util.EnumFacing;

/**
 * @author Cout970
 */
public enum ConnectionClass {

    FULL_BLOCK,
    SLAB_BOTTOM(EnumFacing.DOWN),
    SLAB_TOP(EnumFacing.UP),
    SLAB_NORTH(EnumFacing.NORTH),
    SLAB_SOUTH(EnumFacing.SOUTH),
    SLAB_WEST(EnumFacing.WEST),
    SLAB_EAST(EnumFacing.EAST),
    CABLE_LOW,
    Cable_MEDIUM,
    CABLE_HIGH,
    CABLE_HUGE;

    public EnumFacing orientation;

    ConnectionClass(EnumFacing dir) {
        orientation = dir;
    }

    ConnectionClass() {
    }

    public static boolean isSlabCompatible(ConnectionClass a, ConnectionClass b) {
        return !(a.orientation == null || b.orientation == null) && !(a.orientation.getAxis() == b.orientation.getAxis());
    }
}
