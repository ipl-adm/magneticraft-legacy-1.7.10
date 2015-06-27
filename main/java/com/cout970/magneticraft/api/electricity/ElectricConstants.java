package com.cout970.magneticraft.api.electricity;
/**
 * 
 * @author Cout970
 *
 */
public class ElectricConstants {
																				//old amounts
	public static final double MAX_VOLTAGE 				= 240;					//120;//generators voltage limit
	public static final double ENERGY_INTERFACE_LEVEL	= MAX_VOLTAGE/2;		//60;//voltage until transfer to an ICompatibilityInterface from a Conductor
	public static final double CONVERSION_SPEED 		= 600;					//700;//Voltage * speed = energy transfer
	public static final double MACHINE_DISCHARGE 		= MAX_VOLTAGE*7/12;		//70;
	public static final double MACHINE_CHARGE 			= MAX_VOLTAGE*8/12;		//80;
	public static final double MACHINE_WORK 			= MAX_VOLTAGE/2;		//60;
	public static final double GENERATOR_DISCHARGE 		= MAX_VOLTAGE*8/12;		//80;
	public static final double GENERATOR_CHARGE 		= MAX_VOLTAGE*9/12;		//90;
	public static final double BATTERY_DISCHARGE 		= MAX_VOLTAGE*9/12;		//90;
	public static final double BATTERY_CHARGE 			= MAX_VOLTAGE*10/12;	//100;
	public static final double RESISTANCE_COPPER_LOW	= 0.01D;
	public static final double RESISTANCE_COPPER_MED	= 0.05D;
	public static final double RESISTANCE_COPPER_HIG	= 0.25D;
	public static final double ALTERNATOR_DISCHARGE		= MAX_VOLTAGE;
}
