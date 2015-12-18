package com.cout970.magneticraft.parts.electric;

import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Vector3;
import codechicken.microblock.ISidedHollowConnect;
import codechicken.multipart.NormallyOccludedPart;
import codechicken.multipart.TileMultipart;
import com.cout970.magneticraft.ManagerItems;
import com.cout970.magneticraft.api.electricity.*;
import com.cout970.magneticraft.api.electricity.prefab.ElectricConductor;
import com.cout970.magneticraft.api.util.*;
import com.cout970.magneticraft.client.tilerender.TileRenderCableLow;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PartCableLow extends PartElectric implements ISidedHollowConnect, IElectricMultiPart {

    public byte connections;
    public static List<Cuboid6> boxes = new ArrayList<>();

    static {
        double w = 2 / 16d;
        boxes.add(new Cuboid6(0.5 - w, 0, 0.5 - w, 0.5 + w, 0.5 - w, 0.5 + w));//down
        boxes.add(new Cuboid6(0.5 - w, 0.5 + w, 0.5 - w, 0.5 + w, 1, 0.5 + w));//up
        boxes.add(new Cuboid6(0.5 - w, 0.5 - w, 0, 0.5 + w, 0.5 + w, 0.5 - w));//north
        boxes.add(new Cuboid6(0.5 - w, 0.5 - w, 0.5 + w, 0.5 + w, 0.5 + w, 1));//south
        boxes.add(new Cuboid6(0, 0.5 - w, 0.5 - w, 0.5 - w, 0.5 + w, 0.5 + w));//west
        boxes.add(new Cuboid6(0.5 + w, 0.5 - w, 0.5 - w, 1, 0.5 + w, 0.5 + w));//east
        boxes.add(new Cuboid6(0.5 - w, 0.5 - w, 0.5 - w, 0.5 + w, 0.5 + w, 0.5 + w));//base
    }

    public PartCableLow() {
        super(ManagerItems.part_copper_cable_low);
    }

    @Override
    public List<Cuboid6> getOcclusionCubes() {
        return Collections.singletonList(boxes.get(6));
    }

    @Override
    public List<Cuboid6> getCollisionCubes() {
        ArrayList<Cuboid6> t2 = new ArrayList<>();
        t2.add(boxes.get(6));
        for (byte i = 0; i < 6; i++) {
            if ((connections & (1 << i)) > 0) {
                t2.add(boxes.get(i));
            }
        }
        return t2;
    }

    @Override
    public int getHollowSize(int arg0) {
        return 4;
    }

    public void create() {
        cond = new ElectricConductor(getTile(), ElectricConstants.RESISTANCE_COPPER_LOW) {

            @Override
            public VecInt[] getValidConnections() {
                return new VecInt[]{
                        VecInt.fromDirection(MgDirection.DOWN),
                        VecInt.fromDirection(MgDirection.UP),
                        VecInt.fromDirection(MgDirection.NORTH),
                        VecInt.fromDirection(MgDirection.SOUTH),
                        VecInt.fromDirection(MgDirection.WEST),
                        VecInt.fromDirection(MgDirection.EAST),
                        VecInt.NULL_VECTOR};
            }

            @Override
            public boolean isAbleToConnect(IConnectable c, VecInt d) {
                if (d.equals(VecInt.NULL_VECTOR)) return true;
                if (d.toMgDirection() == null) return false;
                if (c.getConnectionClass(d.getOpposite()) == ConnectionClass.FULL_BLOCK || c.getConnectionClass(d.getOpposite()) == ConnectionClass.CABLE_LOW) {
                    if (((TileMultipart) getTile()).canAddPart(new NormallyOccludedPart(boxes.get(d.toMgDirection().ordinal())))) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public ConnectionClass getConnectionClass(VecInt v) {
                return ConnectionClass.CABLE_LOW;
            }

            @Override
            public double getVoltageCapacity() {
                return ElectricConstants.CABLE_LOW_CAPACITY;
            }
        };
    }

    public void updateConnections() {
        connections = 0;
        for (MgDirection d : MgDirection.values()) {
            TileEntity t = MgUtils.getTileEntity(getTile(), d);
            IElectricConductor[] c = ElectricUtils.getElectricCond(t, VecInt.fromDirection(d).getOpposite(), getTier());
            if (c != null && cond != null) {
                for (IElectricConductor e : c) {
                    if (e.isAbleToConnect(cond, VecInt.fromDirection(d.opposite())) && cond.isAbleToConnect(e, VecInt.fromDirection(d))) {
                        connections = (byte) (connections | (1 << d.ordinal()));
                    }
                }
            }
            IEnergyInterface inter = ElectricUtils.getInterface(t, d.toVecInt().getOpposite(), getTier());
            if (inter != null && inter.canConnect(d.toVecInt())) {
                if (((TileMultipart) getTile()).canAddPart(new NormallyOccludedPart(boxes.get(d.ordinal()))))
                    connections = (byte) (connections | (1 << d.ordinal()));
            }
        }
        tile().jPartList().stream().filter(t -> t instanceof IElectricMultiPart && ((IElectricMultiPart) t).getElectricConductor(getTier()) != null).filter(t -> t instanceof PartWireCopper).forEach(t -> connections = (byte) (connections | (1 << ((PartWireCopper) t).getDirection().ordinal())));
    }


    //Render

    private static TileRenderCableLow render;

    @Override
    public int getTier() {
        return 0;
    }

    @Override
    public void renderPart(Vector3 pos) {
        if (render == null) render = new TileRenderCableLow();
        render.render(this, pos);
    }

}
