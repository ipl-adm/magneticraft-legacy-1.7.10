package com.cout970.magneticraft.util.fluid;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.update1_8.IFluidHandler1_8;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

public class FluidNetWorkManager implements IFluidHandler1_8 {

    public FluidNetwork net;

    public FluidNetWorkManager(FluidNetwork fluidNetwork) {
        net = fluidNetwork;
    }

    public void computingFluids() {
        int amount = net.getFluidAmount();
        if (amount <= 0 || net.fluid == null) return;
        this.drainMg(MgDirection.DOWN, amount, true);
        this.fillMg(MgDirection.DOWN, new FluidStack(net.fluid, amount), true);
    }

    @Override
    public int fillMg(MgDirection from, FluidStack resource, boolean doFill) {

        if (resource == null) return 0;//check if is null
        if (!this.canFillMg(from, resource.getFluid())) return 0;//check if can enter
        int pipes = net.getPipes().size();//number of pipes
        if (pipes <= 0) return 0;//error
        int space = net.getCapacity() - net.getFluidAmount();
        int toFill = Math.min(resource.amount, space);//min fluid, space
        int acceptPerPipe = toFill / pipes;//divided amount per tank
        int accepted = 0;

        if (acceptPerPipe > 0) {
            for (IFluidTransport t : net.getPipes()) {
                FluidStack f = new FluidStack(resource, acceptPerPipe);
                int filled = t.getTank().fill(f, doFill);
                accepted += filled;
            }
        }
        if (acceptPerPipe * pipes != toFill) {
            for (IFluidTransport t : net.getPipes()) {
                if (toFill - accepted > 0) {
                    FluidStack f = new FluidStack(resource, 1);
                    int filled = t.getTank().fill(f, doFill);
                    accepted += filled;
                }
            }
        }
        return accepted;
    }

    @Override
    public FluidStack drainMg_F(MgDirection from, FluidStack resource,
                                boolean doDrain) {
        if (!canDrainMg(from, resource.getFluid())) return null;
        return drainMg(from, resource.amount, doDrain);
    }

    @Override
    public FluidStack drainMg(MgDirection from, int maxDrain, boolean doDrain) {
        if (net.fluid == null) return null;
        int pipes = net.getPipes().size();//number of pipes
        if (pipes <= 0) return null;//error
        int output = Math.min(maxDrain, net.getFluidAmount());
        if (output <= 0) return null;//empty
        int outPerPipe = output / pipes;//divided amount per tank
        int drained = 0;
        if (outPerPipe > 0) {
            for (IFluidTransport t : net.getPipes()) {
                FluidStack d = t.getTank().drain(outPerPipe, doDrain);//outPerPipe
                if (d != null) {
                    drained += d.amount;
                }
            }
        }
        if (output - drained > 0) {//one by one until finish
            for (IFluidTransport t : net.getPipes()) {
                if (output - drained > 0) {
                    FluidStack d = t.getTank().drain(1, doDrain);
                    if (d != null) {
                        drained += d.amount;
                    }
                }
            }
        }
        if (drained > 0) {
            FluidStack f = new FluidStack(net.fluid, drained);
            if (net.getFluidAmount() <= 0) {
                net.fluid = null;
            }
            return f;
        } else return null;
    }

    @Override
    public boolean canFillMg(MgDirection from, Fluid fluid) {
        return fluid != null && (net.fluid == null || net.fluid.equals(fluid));
    }

    @Override
    public boolean canDrainMg(MgDirection from, Fluid fluid) {
        return net.fluid != null && net.fluid.equals(fluid);
    }

    @Override
    public FluidTankInfo[] getTankInfoMg(MgDirection from) {
        return null;
    }

    //1.8 preparation

    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return this.fillMg(MgDirection.getDirection(from.ordinal()), resource, doFill);
    }

    public FluidStack drain(ForgeDirection from, FluidStack resource,
                            boolean doDrain) {
        return this.drainMg_F(MgDirection.getDirection(from.ordinal()), resource, doDrain);
    }

    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return this.drainMg(MgDirection.getDirection(from.ordinal()), maxDrain, doDrain);
    }

    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return this.canFillMg(MgDirection.getDirection(from.ordinal()), fluid);
    }

    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return this.canDrainMg(MgDirection.getDirection(from.ordinal()), fluid);
    }

    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return this.getTankInfoMg(MgDirection.getDirection(from.ordinal()));
    }

}
