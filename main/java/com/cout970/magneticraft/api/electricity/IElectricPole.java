package com.cout970.magneticraft.api.electricity;

import java.util.List;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecDouble;

/**
 * 
 * @author Cout970
 *
 */
public interface IElectricPole extends IElectricTile{

	public boolean connectWire(MgDirection dir, int tier, IElectricConductor to);
	
	public List<IElectricPole> getConnectedConductors();
	
	public VecDouble[] getWireConnector();
}
