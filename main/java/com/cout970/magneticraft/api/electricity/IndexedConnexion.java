package com.cout970.magneticraft.api.electricity;

import com.cout970.magneticraft.api.util.VecInt;

/**
 * 
 * @author Cout970
 *
 */
public class IndexedConnexion {
	
	public VecInt con;
	public IElectricConductor cond;
	public IEnergyInterface inter;
	public int side;
	
	public IndexedConnexion(VecInt c,IEnergyInterface e, int side) {
		con = c;
		inter = e;
		this.side = side;
	}

	public IndexedConnexion(VecInt c, IElectricConductor e, int side) {
		con = c;
		cond = e;
		this.side = side;
	}
}
