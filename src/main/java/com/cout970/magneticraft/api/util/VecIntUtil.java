package com.cout970.magneticraft.api.util;


/**
 * @author Cout970
 */
public class VecIntUtil {

    public static final VecInt[] FORGE_DIRECTIONS = {
            VecInt.fromDirection(MgDirection.DOWN),
            VecInt.fromDirection(MgDirection.UP),
            VecInt.fromDirection(MgDirection.NORTH),
            VecInt.fromDirection(MgDirection.SOUTH),
            VecInt.fromDirection(MgDirection.WEST),
            VecInt.fromDirection(MgDirection.EAST)
    };

    public static final VecInt[] EXTENDED_DIRECTIONS = {// a cube 3x3x3 around
            // the position
            VecInt.fromDirection(MgDirection.DOWN),
            VecInt.fromDirection(MgDirection.UP),
            VecInt.fromDirection(MgDirection.NORTH),
            VecInt.fromDirection(MgDirection.SOUTH),
            VecInt.fromDirection(MgDirection.WEST),
            VecInt.fromDirection(MgDirection.EAST),

            new VecInt(-1, -1, 0),    // -y -x
            new VecInt(1, -1, 0),    // -y +x

            new VecInt(0, -1, 1),   // -y +z
            new VecInt(0, -1, -1),    // -y -z

            new VecInt(-1, 1, 0),    // +y -x 10
            new VecInt(1, 1, 0),    // +y +x

            new VecInt(0, 1, 1),    // +y +z
            new VecInt(0, 1, -1),    // +y -z

            new VecInt(-1, 0, 1),    // +z -x
            new VecInt(1, 0, 1),    // +z +x 15

            new VecInt(1, 0, -1),    // -z +x
            new VecInt(-1, 0, -1),    // -z -x
    };
    public static final VecInt[] WIRE_DOWN = {VecInt.NULL_VECTOR,
            VecInt.fromDirection(MgDirection.DOWN),
            VecInt.fromDirection(MgDirection.NORTH),
            VecInt.fromDirection(MgDirection.SOUTH),
            VecInt.fromDirection(MgDirection.WEST),
            VecInt.fromDirection(MgDirection.EAST),
            new VecInt(1, -1, 0), // -y	+x
            new VecInt(-1, -1, 0),// -y -x
            new VecInt(0, -1, 1), // -y +z
            new VecInt(0, -1, -1) // -y -z;
    };
    public static final VecInt[] WIRE_UP = {VecInt.NULL_VECTOR,
            VecInt.fromDirection(MgDirection.UP),
            VecInt.fromDirection(MgDirection.NORTH),
            VecInt.fromDirection(MgDirection.SOUTH),
            VecInt.fromDirection(MgDirection.WEST),
            VecInt.fromDirection(MgDirection.EAST),
            new VecInt(1, 1, 0), // +y +x
            new VecInt(-1, 1, 0),// +y -x
            new VecInt(0, 1, 1), // +y +z
            new VecInt(0, 1, -1) // +y -z
    };
    public static final VecInt[] WIRE_NORTH = {VecInt.NULL_VECTOR,
            VecInt.fromDirection(MgDirection.DOWN),
            VecInt.fromDirection(MgDirection.UP),
            VecInt.fromDirection(MgDirection.NORTH),
            VecInt.fromDirection(MgDirection.WEST),
            VecInt.fromDirection(MgDirection.EAST),
            new VecInt(0, 1, -1), // +y	-z
            new VecInt(0, -1, -1),// -y -z
            new VecInt(1, 0, -1), // +x -z
            new VecInt(-1, 0, -1),// -x -z
    };
    public static final VecInt[] WIRE_WEST = {VecInt.NULL_VECTOR,
            VecInt.fromDirection(MgDirection.DOWN),
            VecInt.fromDirection(MgDirection.UP),
            VecInt.fromDirection(MgDirection.NORTH),
            VecInt.fromDirection(MgDirection.SOUTH),
            VecInt.fromDirection(MgDirection.WEST),
            new VecInt(-1, 1, 0), // +y -x
            new VecInt(-1, -1, 0),// -y -x
            new VecInt(-1, 0, 1), // +z -x
            new VecInt(-1, 0, -1) // -z -x
    };
    public static final VecInt[] WIRE_SOUTH = {VecInt.NULL_VECTOR,
            VecInt.fromDirection(MgDirection.DOWN),
            VecInt.fromDirection(MgDirection.UP),
            VecInt.fromDirection(MgDirection.SOUTH),
            VecInt.fromDirection(MgDirection.WEST),
            VecInt.fromDirection(MgDirection.EAST),
            new VecInt(0, 1, 1),    // +y +z
            new VecInt(0, -1, 1),    // -y +z
            new VecInt(1, 0, 1),    // +z +z
            new VecInt(-1, 0, 1)    // -z +z
    };
    public static final VecInt[] WIRE_EAST = {VecInt.NULL_VECTOR,
            VecInt.fromDirection(MgDirection.DOWN),
            VecInt.fromDirection(MgDirection.UP),
            VecInt.fromDirection(MgDirection.NORTH),
            VecInt.fromDirection(MgDirection.SOUTH),
            VecInt.fromDirection(MgDirection.EAST),
            new VecInt(-1, 1, 0), // +y -x
            new VecInt(-1, -1, 0),// -y -x
            new VecInt(-1, 0, 1), // +z -x
            new VecInt(-1, 0, -1) // -z -x
    };

    /**
     * Rotates block offset to match Minecraft coordinates based on direction provided.
     * For ease of explanation, this will be seen as direction of sight centered on block with offset (0, 0, 0)
     * @param dir Direction you're looking in.
     * @param right offset to the right, negative for left.
     * @param up Y-axis offset, will not be changed
     * @param back offset to the back (away from point of view), negative for towards the point of view.
     * @return new {@link VecInt}
     * @throws IllegalArgumentException is thrown if direction is not horizontal
     */
    public static VecInt getRotatedOffset(MgDirection dir, int right, int up, int back) {
        int x, y, z;
        switch (dir) {
            case NORTH:
                x = right;
                y = up;
                z = -back;
                break;
            case EAST:
                x = back;
                y = up;
                z = right;
                break;
            case SOUTH:
                x = -right;
                y = up;
                z = back;
                break;
            case WEST:
                x = -back;
                y = up;
                z = -right;
                break;
            default:
                throw new IllegalArgumentException("Rotation for vertical direction is not defined");
        }
        return new VecInt(x, y, z);
    }
}
