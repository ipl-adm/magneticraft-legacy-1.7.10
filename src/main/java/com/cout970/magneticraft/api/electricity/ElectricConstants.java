package com.cout970.magneticraft.api.electricity;

import com.cout970.magneticraft.api.util.EnergyConverter;

/**
 * @author Cout970
 */
public interface ElectricConstants {

    double MAX_VOLTAGE = 100;
    double MACHINE_DISCHARGE = MAX_VOLTAGE * 7 / 12;//todo remove
    double MACHINE_CHARGE = MAX_VOLTAGE * 8 / 12;//todo remove
    double MACHINE_WORK = MAX_VOLTAGE * 0.6;
    double GENERATOR_DISCHARGE = MAX_VOLTAGE * 8 / 12;//todo remove
    double GENERATOR_CHARGE = MAX_VOLTAGE * 9 / 12;//todo remove
    double BATTERY_DISCHARGE = MAX_VOLTAGE * 0.75;//
    double BATTERY_CHARGE = MAX_VOLTAGE * 0.85;
    double RESISTANCE_COPPER_LOW = 0.01D;
    double RESISTANCE_COPPER_MED = 0.01D;
    double RESISTANCE_COPPER_HIGH = 0.25D;
    double ALTERNATOR_DISCHARGE = MAX_VOLTAGE;
    double ENERGY_INTERFACE_LEVEL = MAX_VOLTAGE *0.6;
    double CONVERSION_SPEED = EnergyConverter.RFtoW(5);
    double RESISTANCE_COPPER_WIRE = RESISTANCE_COPPER_LOW;//electric poles
    double MACHINE_CAPACITY = 1;
    double CABLE_LOW_CAPACITY = 0.25D;
    double CABLE_MEDIUM_CAPACITY = CABLE_LOW_CAPACITY/3.6;
    double CABLE_HIGH_CAPACITY = 0.001D;
}
