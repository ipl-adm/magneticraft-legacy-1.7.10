package com.cout970.magneticraft.util.network.pressure;

import com.cout970.magneticraft.util.network.BasicNetwork;
import com.cout970.magneticraft.util.network.NetworkNode;

public class PressureNetwork extends BasicNetwork{

	private NetworkPressureConductor pressure;
	
	public PressureNetwork(NetworkNode startPoint) {
		super(startPoint);
	}
	
	public NetworkPressureConductor getPressureCond(){
		if(pressure == null){
			pressure = new NetworkPressureConductor(this);
		}
		return pressure;
	}
}
