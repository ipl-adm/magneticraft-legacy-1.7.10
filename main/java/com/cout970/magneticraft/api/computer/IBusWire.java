package com.cout970.magneticraft.api.computer;

import com.cout970.magneticraft.api.util.VecInt;

public interface IBusWire {
	
	/**
	 * @return possibles connections that the IBusWire can have.
	 */
	public VecInt[] getValidConnections();
}
