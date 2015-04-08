package com.cout970.magneticraft.api.electricity;

public interface IPartConductor {

	/**used only for microparts
	 * 
	 * @return
	 */
	public IElectricConductor getCond(int tier);
}
