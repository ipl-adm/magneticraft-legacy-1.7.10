package com.cout970.magneticraft.client.gui.component;

public interface IProductor {

	//production in watts
	public float getProductionInTheLastTick();
	public float getProductionInTheLastSecond();
	public float getMaxProduction();//per tick
}
