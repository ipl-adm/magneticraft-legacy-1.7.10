package com.cout970.magneticraft.api.util;


import net.minecraft.util.EnumFacing;

public enum Orientation {

    UP_NORTH(1, EnumFacing.NORTH),
    UP_EAST(1, EnumFacing.EAST),
    UP_SOUTH(1, EnumFacing.SOUTH),
    UP_WEST(1, EnumFacing.WEST),
    NORTH(0, EnumFacing.NORTH),
    EAST(0, EnumFacing.EAST),
    SOUTH(0, EnumFacing.SOUTH),
    WEST(0, EnumFacing.WEST),
    DOWN_NORTH(-1, EnumFacing.NORTH),
    DOWN_EAST(-1, EnumFacing.EAST),
    DOWN_SOUTH(-1, EnumFacing.SOUTH),
    DOWN_WEST(-1, EnumFacing.WEST);

    private int level;
    private EnumFacing dir;

    Orientation(int l, EnumFacing dir) {
        level = l;
        this.dir = dir;
    }

    public EnumFacing getDirection() {
        return dir;
    }

    public int getLevel() {
        return level;
    }

    public static Orientation find(int level, EnumFacing dir) {
        for (Orientation o : values()) {
            if (o.level == level && o.dir == dir) return o;
        }
        return null;
    }

    public Orientation rotateY(boolean left) {
        return find(level, dir.rotateAround(left ? EnumFacing.DOWN.getAxis() : EnumFacing.UP.getAxis()));
    }

    public int toMeta() {
        return ordinal();
    }

    public static Orientation fromMeta(int meta) {
        return values()[meta % values().length];
    }
}