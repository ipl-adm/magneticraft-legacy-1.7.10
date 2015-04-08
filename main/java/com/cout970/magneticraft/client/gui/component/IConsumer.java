package com.cout970.magneticraft.client.gui.component;

public interface IConsumer {

	//consumption in watts
	public float getConsumptionInTheLastTick();
	public float getConsumptionInTheLastSecond();
	public float getMaxConsumption();//per tick
}
