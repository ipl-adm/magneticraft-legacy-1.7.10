package com.cout970.magneticraft.api.heat;

import com.cout970.magneticraft.api.util.IConnectable;

/**
 * @author Cout970
 */
public interface IHeatConductor extends IConnectable {

    /**
     * @return the temperature in celsius degrees
     */
    double getTemperature();

    /**
     * Used for client side sync
     *
     * @param heat
     */
    void setTemperature(double heat);

    /**
     * the temperature before the block is melted
     */
    double getMaxTemp();

    /**
     * the amount of mass, usually 1000
     *
     * @return
     */
    double getMass();

    double getSpecificHeat();

    /**
     * Add some calories to the block
     *
     * @param j
     */
    void applyCalories(double j);

    /**
     * remove some calories from the block
     *
     * @param j
     */
    void drainCalories(double j);

    /**
     * the resistance of the heat to cross the block
     *
     * @return
     */
    double getResistance();

    void onBlockOverHeat();
}
