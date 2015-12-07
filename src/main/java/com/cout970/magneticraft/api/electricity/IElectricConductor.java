package com.cout970.magneticraft.api.electricity;

import com.cout970.magneticraft.api.util.IConnectable;

/**
 * @author Cout970
 */
public interface IElectricConductor extends IConnectable {

    //basic energy utility
    double[] ENERGY_TIERS = {100, 360, 12500, 65000, 130000, 580000, 1200000};

    /**
     * @return the voltage stored in the conductor
     */
    double getVoltage();

    /**
     * Used for high voltage cables
     *
     * @return 10^tier
     */
    double getVoltageMultiplier();

    /**
     * @return the flow generated when energy pass through, should be constant
     */
    double getIndScale();

    /**
     * @return the capacity of the block, voltage capacity, no storage capacity
     */
    double getVoltageCapacity();

    /**
     * this method should prepare the basic things for the iteration, like the connexions
     */
    void recache();

    /**
     * this method add to the voltage the intensity in amps(I * 0.05 seconds/tick * getVoltageMultiplier())
     */
    void computeVoltage();

    /**
     * @return Intensity that pass through in the last iteration, only for display
     */
    double getIntensity();

    /**
     * @return the constant of resistance, must be positive and non cero.
     */
    double getResistance();

    /**
     * Adds an intensity to the conductor, allow negative values
     */
    void applyCurrent(double amps);

    /**
     * adds Watts to the conductor, negative values are not allowed
     */
    void applyPower(double amps);

    /**
     * remove Watts from the conductor, negative values are not allowed
     */
    void drainPower(double amps);

    //sync client and server
    void setResistance(double d);

    void setVoltage(double d);

    //storage in the internal buffer, only for machines and batteries
    int getStorage();

    int getMaxStorage();

    void setStorage(int charge);

    void applyCharge(int charge);

    void drainCharge(int charge);

    //cable connections

    /**
     * reset the connexions
     */
    void disconnect();

    /**
     * @return true if recache method was called after to disconet()
     */
    boolean isConnected();

    /**
     * @return the Indexed connexions established, used to not repeat them.
     */
    IIndexedConnection[] getConnections();

    /**
     * @param con connexion between two conductors
     * @return if the energy can flow on this connection
     */
    boolean canFlowPower(IIndexedConnection con);

    /**
     * @return the tier of the conductor, used for high voltage.
     */
    int getTier();

}
