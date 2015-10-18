package com.cout970.magneticraft.api.pressure;

import com.cout970.magneticraft.api.util.IConnectable;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public interface IPressureConductor extends IConnectable {

    /**
     * Ideal Gas constant
     * units: (Pa*m^3)/(mol*K) or J/(mol*K)
     */
    double R = 8.3144621;

    /**
     * the volume should be in mB mili Buckets
     * volume must be more than 0
     *
     * @return
     */
    double getVolume();

    void setVolume(double c);

    /**
     * the pressure should be in Pa (Pascals)
     *
     * @return
     */
    double getPressure();

    double getMaxPressure();

    /**
     * moles of gas in the conductor
     *
     * @return
     */
    double getMoles();

    void setMoles(double moles);

    /**
     * temperature of the gas in kelvin
     *
     * @return
     */
    double getTemperature();

    void setTemperature(double temp);

    /**
     * adds fluid to this conductor
     * @param pack fluid input
     * @return excess fluid
     */
    PressurizedFluid moveFluid(PressurizedFluid pack);

    /**
     * @param gas
     * @return the amount accepted by the conductor, will be 0 if the fluid is not valid(no gas or diferent fluid) or the same amount as the fluidstack.
     */
    int applyGas(FluidStack gas, boolean doFill);

    FluidStack drainGas(int amount, boolean doDrain);

    void onBlockExplode();

    Fluid getFluid();

    void setFluid(Fluid fluid);
}
