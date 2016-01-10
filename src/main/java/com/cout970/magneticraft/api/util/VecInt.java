package com.cout970.magneticraft.api.util;

import com.google.common.base.Objects;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import javax.annotation.Nonnull;


/**
 * This api is similar to BlockCoord in minecraft 1.8
 *
 * @author Cout970
 */
public class VecInt implements Comparable<VecInt> {

    public static final VecInt NULL_VECTOR = new ImmutableVecInt(0, 0, 0);

    protected int x;
    protected int y;
    protected int z;

    public VecInt(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public VecInt(VecInt base, MgDirection dir) {
        x = base.x + dir.getOffsetX();
        y = base.y + dir.getOffsetY();
        z = base.z + dir.getOffsetZ();
    }

    public VecInt(double x, double y, double z) {
        this(MathHelper.floor_double(x), MathHelper.floor_double(y), MathHelper.floor_double(z));
    }

    public VecInt(int[] ar) {
        this(ar[0], ar[1], ar[2]);
    }


    public VecInt(TileEntity tile) {
        this(tile.xCoord, tile.yCoord, tile.zCoord);
    }

    public VecInt(EntityPlayerMP pl) {
        this(pl.posX, pl.posY, pl.posZ);
    }

    public VecInt(NBTTagCompound nbt, String name) {
        this(nbt.getInteger(name + "_x"), nbt.getInteger(name + "_y"), nbt.getInteger(name + "_z"));
    }

    public static VecInt fromDirection(MgDirection d) {
        return new VecInt(d.getOffsetX(), d.getOffsetY(), d.getOffsetZ());
    }

    public static VecInt getConnection(ForgeDirection d) {
        return new VecInt(d.offsetX, d.offsetY, d.offsetZ);
    }

    public static VecInt load(NBTTagCompound nbt) {
        return new VecInt(nbt.getInteger("X"), nbt.getInteger("Y"), nbt.getInteger("Z"));
    }

    public VecInt getOpposite() {
        return new VecInt(-x, -y, -z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VecInt)) return false;

        VecInt vecInt = (VecInt) o;

        return getX() == vecInt.getX() && getY() == vecInt.getY() && getZ() == vecInt.getZ();

    }

    @Override
    public int hashCode() {
        int result = getX();
        result = 31 * result + getY();
        result = 31 * result + getZ();
        return result;
    }

    public int compareTo(@Nonnull VecInt vec) {
        return this.getY() == vec.getY() ? (this.getZ() == vec.getZ() ? this
                .getX() - vec.getX() : this.getZ() - vec.getZ()) : this.getY()
                - vec.getY();
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    public String toString() {
        return Objects.toStringHelper(this).add("x", this.getX())
                .add("y", this.getY()).add("z", this.getZ()).toString();
    }

    public MgDirection toMgDirection() {
        for (MgDirection d : MgDirection.values())
            if (d.getOffsetX() == x && d.getOffsetY() == y && d.getOffsetZ() == z)
                return d;
        return null;
    }

    public VecInt multiply(int i) {
        x *= i;
        y *= i;
        z *= i;
        return this;
    }

    public VecInt add(VecInt v) {
        x += v.x;
        y += v.y;
        z += v.z;
        return this;
    }

    public VecInt add(int a, int b, int c) {
        x += a;
        y += b;
        z += c;
        return this;
    }

    public VecInt copy() {
        return new VecInt(x, y, z);
    }

    public void save(NBTTagCompound nbt) {
        nbt.setInteger("X", x);
        nbt.setInteger("Y", y);
        nbt.setInteger("Z", z);
    }

    public int[] intArray() {
        return new int[]{x, y, z};
    }

    public int squareDistance() {
        return x * x + y * y + z * z;
    }

    public void save(NBTTagCompound nbt, String name) {
        nbt.setInteger(name + "_x", x);
        nbt.setInteger(name + "_y", y);
        nbt.setInteger(name + "_z", z);
    }

    public TileEntity getTileEntity(IBlockAccess w) {
        return w.getTileEntity(x, y, z);
    }

    public VecInt add(MgDirection dir) {
        return this.add(dir.getOffsetX(), dir.getOffsetY(), dir.getOffsetZ());
    }

    public Block getBlock(IBlockAccess world) {
        return world.getBlock(x, y, z);
    }

    public int getBlockMetadata(IBlockAccess world) {
        return world.getBlockMetadata(x, y, z);
    }

    public void setBlockMetadata(World world, int meta, int flags) {
        world.setBlockMetadataWithNotify(x, y, z, meta, flags);
    }

    public void setBlock(World world, Block block) {
        world.setBlock(x, y, z, block);
    }

    public boolean isBlockReplaceable(IBlockAccess world) {
        return getBlock(world).isReplaceable(world, x, y, z);
    }

    public void setBlockWithMetadata(World world, Block block, int meta, int flags) {
        world.setBlock(x, y, z, block, meta, flags);
    }

    public void setTileEntity(World world, TileEntity tile) {
        world.setTileEntity(x, y, z, tile);
    }

    public boolean blockExists(World world) {
        return world.blockExists(x, y, z);
    }

    public static class ImmutableVecInt extends VecInt {

        public ImmutableVecInt(int x, int y, int z) {
            super(x, y, z);
        }

        public VecInt multiply(int i) {
            throw new UnsupportedOperationException("Trying to modify immutable vector object");
        }

        public VecInt add(VecInt v) {
            throw new UnsupportedOperationException("Trying to modify immutable vector object");
        }

        public VecInt add(int a, int b, int c) {
            throw new UnsupportedOperationException("Trying to modify immutable vector object");
        }
    }
}
