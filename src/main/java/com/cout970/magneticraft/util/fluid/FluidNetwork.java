package com.cout970.magneticraft.util.fluid;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;

import java.util.ArrayList;
import java.util.List;

public class FluidNetwork {

    public FluidNetWorkManager manager = new FluidNetWorkManager(this);
    private List<IFluidTransport> pipes = new ArrayList<>();
    private TileEntity tile;
    public Fluid fluid;


    private FluidNetwork() {
    }

    public static FluidNetwork create(IFluidTransport base, TileEntity tile) {
        FluidNetwork net = new FluidNetwork();
        net.pipes.add(base);
        net.tile = tile;
        return net;
    }

    public TileEntity getTile() {
        return tile;
    }

    public List<IFluidTransport> getPipes() {
        return pipes;
    }

    public void addPipe(IFluidTransport p) {
        pipes.add(p);
    }

    public void refresh() {
        IFluidTransport base = null;
        for (IFluidTransport e : pipes) {
            if (e != null) {
                TileEntity t = e.getTileEntity();
                if (t != null && t.getWorldObj().getTileEntity(t.xCoord, t.yCoord, t.zCoord) != null) {
                    base = e;
                }
            }
        }

        if (base != null) {
            List<IFluidTransport> things = new ArrayList<>();
            FluidPathfinder found = new FluidPathfinder(base, null);
            things.addAll(found.getPipes());
            pipes.stream().filter(ft -> !things.contains(ft)).forEach(ft -> ft.setNetwork(null));
            pipes = things;
        }

        for (IFluidTransport e : pipes) {
            e.setNetwork(this);
            e.onNetworkUpdate();
        }
    }

    public void mergeWith(FluidNetwork net) {
        for (IFluidTransport te : pipes) {
            te.setNetwork(net);
        }
        pipes.forEach(com.cout970.magneticraft.util.fluid.IFluidTransport::onNetworkUpdate);
    }

    public void exclude(IFluidTransport te) {
        List<IFluidTransport> members = te.getNetwork().getPipes();
        for (IFluidTransport i : members) i.setNetwork(null);
    }

    public void excludeAndRecalculate(IFluidTransport te) {
        exclude(te);
        refresh();
    }

    public int getFluidAmount() {
        int amount = 0;
        for (IFluidTransport t : pipes) {
            amount += t.getTank().getFluidAmount();
        }
        return amount;
    }

    public Fluid getFluid() {
        if (fluid == null) {
            if (getFluidAmount() == 0) return null;
            for (IFluidTransport t : pipes) {
                if (t.getTank().getFluidAmount() > 0) {
                    fluid = t.getTank().getFluid().getFluid();
                    break;
                }
            }
        }
        return fluid;
    }

    public int getCapacity() {
        int cap = 0;
        for (IFluidTransport t : pipes) {
            cap += t.getTank().getCapacity();
        }
        return cap;
    }
}
