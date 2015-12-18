package com.cout970.magneticraft.parts.electric;

import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Vector3;
import codechicken.multipart.NormallyOccludedPart;
import codechicken.multipart.TileMultipart;
import com.cout970.magneticraft.ManagerItems;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.ElectricUtils;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.IEnergyInterface;
import com.cout970.magneticraft.api.electricity.prefab.ElectricConductor;
import com.cout970.magneticraft.api.util.*;
import com.cout970.magneticraft.client.tilerender.TileRenderWireCopper;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class PartWireCopper extends PartElectric {

    public static List<Cuboid6> Down_Boxes = new ArrayList<>();
    public static List<Cuboid6> Up_Boxes = new ArrayList<>();
    public static List<Cuboid6> North_Boxes = new ArrayList<>();
    public static List<Cuboid6> South_Boxes = new ArrayList<>();
    public static List<Cuboid6> West_Boxes = new ArrayList<>();
    public static List<Cuboid6> East_Boxes = new ArrayList<>();

    static {
        float width = 2 / 16f;
        float h = 3 / 16f;
        Down_Boxes.clear();
        Up_Boxes.clear();
        North_Boxes.clear();
        South_Boxes.clear();
        West_Boxes.clear();
        East_Boxes.clear();

        Down_Boxes.add(new Cuboid6(0.5f - width, 0, 0.5f - width, 0.5f + width, h, 0.5f + width));//base
        Down_Boxes.add(new Cuboid6(0.5f - width, 0, 0, 0.5f + width, h, 0.5f - width));//-z
        Down_Boxes.add(new Cuboid6(0.5f - width, 0, 0.5f + width, 0.5f + width, h, 1));//+z
        Down_Boxes.add(new Cuboid6(0, 0, 0.5f - width, 0.5f - width, h, 0.5f + width));//-x
        Down_Boxes.add(new Cuboid6(0.5f + width, 0, 0.5f - width, 1, h, 0.5f + width));//+x

        Up_Boxes.add(new Cuboid6(0.5f - width, 1 - h, 0.5f - width, 0.5f + width, 1, 0.5f + width));//base
        Up_Boxes.add(new Cuboid6(0.5f - width, 1 - h, 0, 0.5f + width, 1, 0.5f - width));//-z
        Up_Boxes.add(new Cuboid6(0.5f - width, 1 - h, 0.5f + width, 0.5f + width, 1, 1));//+z
        Up_Boxes.add(new Cuboid6(0, 1 - h, 0.5f - width, 0.5f - width, 1, 0.5f + width));//-x
        Up_Boxes.add(new Cuboid6(0.5f + width, 1 - h, 0.5f - width, 1, 1, 0.5f + width));//+x

        North_Boxes.add(new Cuboid6(0.5f - width, 0.5f - width, 0, 0.5f + width, 0.5f + width, h));//base
        North_Boxes.add(new Cuboid6(0.5f - width, 0, 0, 0.5f + width, 0.5f - width, h));//-z
        North_Boxes.add(new Cuboid6(0.5f - width, 0.5f + width, 0, 0.5f + width, 1, h));//+z
        North_Boxes.add(new Cuboid6(0, 0.5f - width, 0, 0.5f - width, 0.5f + width, h));//-x
        North_Boxes.add(new Cuboid6(0.5f + width, 0.5f - width, 0, 1, 0.5f + width, h));//+x

        South_Boxes.add(new Cuboid6(0.5f - width, 0.5f - width, 1 - h, 0.5f + width, 0.5f + width, 1));//base
        South_Boxes.add(new Cuboid6(0.5f - width, 0, 1 - h, 0.5f + width, 0.5f - width, 1));//-z
        South_Boxes.add(new Cuboid6(0.5f - width, 0.5f + width, 1 - h, 0.5f + width, 1, 1));//+z
        South_Boxes.add(new Cuboid6(0, 0.5f - width, 1 - h, 0.5f - width, 0.5f + width, 1));//-x
        South_Boxes.add(new Cuboid6(0.5f + width, 0.5f - width, 1 - h, 1, 0.5f + width, 1));//+x

        West_Boxes.add(new Cuboid6(0, 0.5f - width, 0.5f - width, h, 0.5f + width, 0.5f + width));//base
        West_Boxes.add(new Cuboid6(0, 0, 0.5f - width, h, 0.5f - width, 0.5f + width));//-z
        West_Boxes.add(new Cuboid6(0, 0.5f + width, 0.5f - width, h, 1, 0.5f + width));//+z
        West_Boxes.add(new Cuboid6(0, 0.5f - width, 0, h, 0.5f + width, 0.5f - width));//-x
        West_Boxes.add(new Cuboid6(0, 0.5f - width, 0.5f + width, h, 0.5f + width, 1));//+x

        East_Boxes.add(new Cuboid6(1 - h, 0.5f - width, 0.5f - width, 1, 0.5f + width, 0.5f + width));//base
        East_Boxes.add(new Cuboid6(1 - h, 0, 0.5f - width, 1, 0.5f - width, 0.5f + width));//-z
        East_Boxes.add(new Cuboid6(1 - h, 0.5f + width, 0.5f - width, 1, 1, 0.5f + width));//+z
        East_Boxes.add(new Cuboid6(1 - h, 0.5f - width, 0, 1, 0.5f + width, 0.5f - width));//-x
        East_Boxes.add(new Cuboid6(1 - h, 0.5f - width, 0.5f + width, 1, 0.5f + width, 1));//+x
    }

    private TileRenderWireCopper render;
    private VecInt[] validCon;
    public int Conn = 0;

    public abstract MgDirection getDirection();

    public abstract List<Cuboid6> getBoxes();

    public PartWireCopper() {
        super(ManagerItems.part_copper_wire);
    }

    public void create() {
        cond = new ElectricConductor(getTile(), 0, ElectricConstants.RESISTANCE_COPPER_LOW) {
            @Override
            public VecInt[] getValidConnections() {
                return PartWireCopper.this.getValidConnexions(getDirection());
            }

            @Override
            public boolean isAbleToConnect(IConnectable c, VecInt d) {
                if (d.equals(VecInt.NULL_VECTOR)) return true;
                if (d.equals(getDirection().toVecInt())) return true;
                if (d.equals(getDirection().opposite().toVecInt())) return false;
                MgDirection dir = d.toMgDirection();
                if (dir != null) {
                    //FORGE_DIRECTIONS
                    return ((TileMultipart) getTile()).canAddPart(new NormallyOccludedPart(getBoxes().get(getBoxBySide(dir))))
                            && (c.getConnectionClass(d.getOpposite()) == this.getConnectionClass(d) || c.getConnectionClass(d.getOpposite()) == ConnectionClass.FULL_BLOCK);
                } else {
                    dir = (d.copy().add(getDirection().toVecInt().getOpposite())).toMgDirection();
                }
                if (dir != null) {
                    //EXTENDED_DIRECTIONS
                    VecInt g = dir.toVecInt().add(X(), Y(), Z());
                    Block b = W().getBlock(g.getX(), g.getY(), g.getZ());
                    return ((TileMultipart) getTile()).canAddPart(new NormallyOccludedPart(getBoxes().get(getBoxBySide(dir))))
                            && isPassable(b)
                            && (c.getConnectionClass(d.getOpposite()) == ConnectionClass.FULL_BLOCK
                            || ConnectionClass.isSlabCompatible(c.getConnectionClass(d.getOpposite()), getConnectionClass(d)));
                }
                return false;
            }

            @Override
            public ConnectionClass getConnectionClass(VecInt v) {
                return ConnectionType();
            }

            @Override
            public double getVoltageCapacity() {
                return ElectricConstants.CABLE_LOW_CAPACITY;
            }
        };
    }

    public abstract int getBoxBySide(MgDirection dir);

    public ConnectionClass ConnectionType() {
        if (getDirection() == MgDirection.DOWN) return ConnectionClass.SLAB_BOTTOM;
        if (getDirection() == MgDirection.UP) return ConnectionClass.SLAB_TOP;
        if (getDirection() == MgDirection.NORTH) return ConnectionClass.SLAB_NORTH;
        if (getDirection() == MgDirection.SOUTH) return ConnectionClass.SLAB_SOUTH;
        if (getDirection() == MgDirection.WEST) return ConnectionClass.SLAB_WEST;
        if (getDirection() == MgDirection.EAST) return ConnectionClass.SLAB_EAST;
        return ConnectionClass.SLAB_BOTTOM;
    }

    public VecInt[] getValidConnexions(MgDirection dir) {
        if (validCon == null) {
            validCon = new VecInt[10];
            validCon[0] = VecInt.NULL_VECTOR;
            validCon[1] = dir.toVecInt();
            byte i = 2;

            for (MgDirection d : MgDirection.values()) {
                if (d != dir && d != dir.opposite()) {
                    validCon[i] = d.toVecInt();
                    i++;
                }
            }
            for (MgDirection d : MgDirection.values()) {
                if (d != dir && d != dir.opposite()) {
                    validCon[i] = d.toVecInt().copy().add(dir.toVecInt());
                    i++;
                }
            }
        }
        return validCon;
    }

    @Override
    public int getTier() {
        return 0;
    }

    @Override
    public void updateConnections() {
        Conn = 0;
        for (MgDirection f : MgDirection.values()) {
            TileEntity target = MgUtils.getTileEntity(tile(), f.toVecInt());
            IElectricConductor[] c = ElectricUtils.getElectricCond(target, f.toVecInt().getOpposite(), getTier());
            IEnergyInterface inter = ElectricUtils.getInterface(target, f.toVecInt().getOpposite(), getTier());
            if (c != null) {
                for (IElectricConductor e : c) {
                    if (cond.isAbleToConnect(e, f.toVecInt()) && e.isAbleToConnect(cond, f.toVecInt().getOpposite())) {
                        Conn |= 1 << f.ordinal();
                    }
                }
            }
            tile().jPartList().stream().filter(t -> t instanceof PartWireCopper && t != this).filter(t -> ((PartWireCopper) t).getDirection() == f).forEach(t -> Conn |= 1 << f.ordinal());
            if (inter != null) {
                Conn |= 1 << f.ordinal();
            }
        }
        for (MgDirection d : MgDirection.values()) {
            VecInt f = d.toVecInt().add(getDirection().toVecInt());
            TileEntity target = MgUtils.getTileEntity(tile(), f);
            IElectricConductor[] c = ElectricUtils.getElectricCond(target, f.getOpposite(), getTier());
            IEnergyInterface inter = ElectricUtils.getInterface(target, f.getOpposite(), getTier());
            if (c != null || inter != null) {
                VecInt g = d.toVecInt().copy().add(X(), Y(), Z());
                Block b = W().getBlock(g.getX(), g.getY(), g.getZ());
                if (isPassable(b)) {
                    if (c != null) {
                        for (IElectricConductor e : c) {
                            if (cond.isAbleToConnect(e, f) && e.isAbleToConnect(cond, f.getOpposite())) {
                                Conn |= 1 << d.ordinal();
                                Conn |= 1 << d.ordinal() + 6;
                            }
                        }
                    }
                    if (inter != null) {
                        Conn |= 1 << d.ordinal();
                        Conn |= 1 << d.ordinal() + 6;
                    }
                }
            }
        }
    }

    @Override
    public void onNeighborChanged() {
        super.onNeighborChanged();
        MgDirection d = getDirection();
        if (!world().isRemote && !world().isSideSolid(X() + d.getOffsetX(), Y() + d.getOffsetY(), Z() + d.getOffsetZ(), d.toForgeDir(), false)) {
            tile().dropItems(this.getDrops());
            tile().remPart(this);
        }
    }

    private boolean isPassable(Block b) {
        return b == Blocks.air;
    }

    @Override
    public String getType() {
        return item.getUnlocalizedName() + "_" + getDirection().name();
    }

    @Override
    public List<Cuboid6> getOcclusionCubes() {
        return Collections.singletonList(getBoxes().get(0));
    }

    @Override
    public List<Cuboid6> getCollisionCubes() {
        List<Cuboid6> l = new ArrayList<>();
        l.add(getBoxes().get(0));
        for (MgDirection d : MgDirection.values()) {
            if (getBoxBySide(d) != 0 && (Conn & (1 << d.ordinal())) > 0) {
                l.add(getBoxes().get(getBoxBySide(d)));
            }
        }
        return l;
    }

    @Override
    public void renderPart(Vector3 pos) {
        if (render == null) render = new TileRenderWireCopper();
        render.render(this, pos);
    }

    public boolean isConnected() {
        return (Conn & getConMask()) != 0;
    }

    public int getConMask() {
        if (getDirection() == MgDirection.DOWN) return 4095 ^ 2;
        if (getDirection() == MgDirection.UP) return 4095 ^ 1;
        if (getDirection() == MgDirection.NORTH) return 4095 ^ 8;
        if (getDirection() == MgDirection.SOUTH) return 4095 ^ 4;
        if (getDirection() == MgDirection.WEST) return 4095 ^ 32;
        if (getDirection() == MgDirection.EAST) return 4095 ^ 16;
        return 4095;
    }
}
