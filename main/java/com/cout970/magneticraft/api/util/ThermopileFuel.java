package com.cout970.magneticraft.api.util;

/**
 * This Class if for register an fuel for the Termopile
 * @author Cout970
 *
 */
public class ThermopileFuel {

	public boolean heat;//true == heat source, false == cold source
	public BlockInfo source;
	public double temp;//always positive
	
	public ThermopileFuel(BlockInfo s,double t,boolean heat){
		source = s;
		temp = t;
		this.heat = heat;
	}
}
