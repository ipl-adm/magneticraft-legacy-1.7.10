package com.cout970.magneticraft.api.electricity;

import net.minecraft.tileentity.TileEntity;

/**
 * 
 * @author Cout970
 *
 */
public interface IEnergyInterface{

	/**
	 * @param wats
	 * @return energy accepted from the other energy system
	 */
	public double applyWatts(double watts);
	
	/**
	 * @param watts
	 * @return energy extracted from the other energy system
	 */
	public double drainWatts(double watts);
	
	/**
	 * the capacity to store energy
	 * @return
	 */
	public double getCapacity();
	
	/**
	 * amount of energy stored
	 * @return
	 */
	public double getEnergyStored();
	
	/**
	 * max amount of energy per tick, should be in Watts
	 * @return
	 */
	public double getMaxFlow();

	/**
	 * if the block can accept, emit both or nothing
	 * @return
	 */
	public EnumAcces getBehavior();
	
	/**
	 * the tileEntity that has the block 
	 * @return
	 */
	public TileEntity getParent();
	
}
