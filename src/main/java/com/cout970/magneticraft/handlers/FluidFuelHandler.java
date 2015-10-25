package com.cout970.magneticraft.handlers;

import buildcraft.api.fuels.IFuel;
import buildcraft.api.fuels.IFuelManager;
import cpw.mods.fml.common.Optional;
import net.minecraftforge.fluids.Fluid;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Optional.Interface(iface = "buildcraft.api.fuels.IFuelManager", modid = "BuildCraft|Core")
public class FluidFuelHandler implements IFuelManager {

    public Map<Fluid, IFuel> fluids = new HashMap<>();

    @Override
    public IFuel addFuel(IFuel fuel) {
        if (fluids.containsKey(fuel.getFluid())) {
            return fluids.get(fuel.getFluid());
        }
        fluids.put(fuel.getFluid(), fuel);
        return fuel;
    }

    @Override
    public IFuel addFuel(Fluid fluid, int powerPerCycle, int totalBurningTime) {
        IFuel fuel = new Fuel(fluid, powerPerCycle, totalBurningTime);
        if (fluids.containsKey(fuel.getFluid())) {
            return fluids.get(fuel.getFluid());
        }
        fluids.put(fuel.getFluid(), fuel);
        return fuel;
    }

    @Override
    public Collection<IFuel> getFuels() {
        return fluids.values();
    }

    @Override
    public IFuel getFuel(Fluid fluid) {
        return fluids.get(fluid);
    }

    public class Fuel implements IFuel {

        private Fluid fluid;
        private int burnTime;
        private int energy;

        public Fuel(Fluid f, int e, int b) {
            fluid = f;
            energy = e;
            burnTime = b;
        }

        @Override
        public Fluid getFluid() {
            return fluid;
        }

        @Override
        public int getTotalBurningTime() {
            return burnTime;
        }

        @Override
        public int getPowerPerCycle() {
            return energy;
        }

    }
}
