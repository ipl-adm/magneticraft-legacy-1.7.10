package com.cout970.magneticraft.api.electricity;

import java.util.List;

import com.cout970.magneticraft.api.util.MgDirection;

/**
 * 
 * @author Cout970
 *
 */
public interface IElectricPole extends IElectricTile{

	public boolean connectWire(MgDirection dir, int tier, IElectricConductor to);
	
	public List<IElectricConductor> getConnectedConductors();
}
