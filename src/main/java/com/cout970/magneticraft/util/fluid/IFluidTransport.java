package com.cout970.magneticraft.util.fluid;

import com.cout970.magneticraft.api.util.MgDirection;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.IFluidTank;

public interface IFluidTransport {

    TileEntity getTileEntity();

    void setNetwork(FluidNetwork fluidNetwork);

    void onNetworkUpdate();

    FluidNetwork getNetwork();

    IFluidTank getTank();

    boolean canConnectOnSide(MgDirection dir);

    boolean isCompatible(IFluidTransport a);

}
