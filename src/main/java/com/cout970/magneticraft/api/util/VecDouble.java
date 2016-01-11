package com.cout970.magneticraft.api.util;

import com.google.common.base.Objects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

/**
 * Simple implementation a 3D vector with doubles
 *
 * @author Cout970
 */
public class VecDouble {

    public static final VecDouble NULL_VECTOR = new VecDouble(0, 0, 0);
    protected double x;
    protected double y;
    protected double z;

    public VecDouble(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public VecDouble(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public VecDouble(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public VecDouble(NBTTagCompound nbt, String name) {
        this(nbt.getDouble(name + "_x"), nbt.getDouble(name + "_y"), nbt.getDouble(name + "_z"));
    }

    public VecDouble(double[] ar) {
        this(ar[0], ar[1], ar[2]);
    }

    public VecDouble(BlockPos vec) {
        this(vec.getX(), vec.getY(), vec.getZ());
    }

    public VecDouble(TileEntity t) {
        this(t.getPos());
    }

    public static VecDouble getConnection(EnumFacing d) {
        return new VecDouble(d.getFrontOffsetX(), d.getFrontOffsetY(), d.getFrontOffsetZ());
    }

    public VecDouble getOpposite() {
        return new VecDouble(-x, -y, -z);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof VecDouble)) {
            return false;
        } else {
            VecDouble vec = (VecDouble) obj;
            return (this.getX() == vec.getX()) && ((this.getY() == vec.getY()) && (this.getZ() == vec.getZ()));
        }
    }

    public int hashCode() {
        return (int) ((this.getY() + this.getZ() * 31) * 31 + this.getX());
    }

    public double compareTo(VecDouble vec) {
        return this.getY() == vec.getY() ? (this.getZ() == vec.getZ() ? this
                .getX() - vec.getX() : this.getZ() - vec.getZ()) : this.getY()
                - vec.getY();
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public String toString() {
        return Objects.toStringHelper(this).add("x", this.getX())
                .add("y", this.getY()).add("z", this.getZ()).toString();
    }

    public VecDouble multiply(double d) {
        x *= d;
        y *= d;
        z *= d;
        return this;
    }

    public VecDouble add(VecDouble v) {
        x += v.x;
        y += v.y;
        z += v.z;
        return this;
    }

    public VecDouble add(double a, double b, double c) {
        x += a;
        y += b;
        z += c;
        return this;
    }

    public VecDouble copy() {
        return new VecDouble(x, y, z);
    }

    public double mag() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public void save(NBTTagCompound nbt, String name) {
        nbt.setDouble(name + "_x", x);
        nbt.setDouble(name + "_y", y);
        nbt.setDouble(name + "_z", x);
    }
}
