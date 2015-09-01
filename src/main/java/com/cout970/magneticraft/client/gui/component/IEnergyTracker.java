package com.cout970.magneticraft.client.gui.component;

public interface IEnergyTracker {

    //production in watts
    public float getChangeInTheLastTick();

    public float getChangeInTheLastSecond();

    public float getMaxChange();//per tick

    public boolean isConsume();
}
