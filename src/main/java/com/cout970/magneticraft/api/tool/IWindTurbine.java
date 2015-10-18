package com.cout970.magneticraft.api.tool;

import com.cout970.magneticraft.api.util.IRenderable;

/**
 * Interface to implement in an Item to be able to work in the Wind Turbine
 *
 * @author Cout970
 */
public interface IWindTurbine {

    /**
     * should be more than 3 and be diferent for every type of turbine
     *
     * @return unique id
     */
    int getID();

    /**
     * number of blocks from the base block, equal to radio
     *
     * @return
     */
    int getHeight();

    /**
     * number of blocks from the base block, equal to radio
     *
     * @return
     */
    int getLength();

    /**
     * @return amount of enery produced in the best conditions
     */
    double getPotency();

    /**
     * the render scale
     *
     * @return
     */
    float getScale();

    /**
     * the the turbine item change, this method is called to generate an Object to render the new Turbine item
     *
     * @return the render object
     */
    IRenderable initRender();
}
