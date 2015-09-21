package com.cout970.magneticraft.api.util;


/**
 * @author Cout970
 */
public enum ConnectionClass {

    FULL_BLOCK,
    SLAB_BOTTOM(MgDirection.DOWN),
    SLAB_TOP(MgDirection.UP),
    SLAB_NORTH(MgDirection.NORTH),
    SLAB_SOUTH(MgDirection.SOUTH),
    SLAB_WEST(MgDirection.WEST),
    SLAB_EAST(MgDirection.EAST),
    CABLE_LOW,
    Cable_MEDIUM,
    CABLE_HIGH,
    CABLE_HUGE;

    public MgDirection orientation;

    ConnectionClass(MgDirection dir) {
        orientation = dir;
    }

    ConnectionClass() {
    }

    public static boolean isSlabCompatible(ConnectionClass a, ConnectionClass b) {
        return !(a.orientation == null || b.orientation == null) && !a.orientation.isParallel(b.orientation);
    }
}
