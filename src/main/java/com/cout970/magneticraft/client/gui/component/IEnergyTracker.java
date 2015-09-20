package com.cout970.magneticraft.client.gui.component;

public interface IEnergyTracker {

    //production in watts
    float getChangeInTheLastTick();

    float getChangeInTheLastSecond();

    float getMaxChange();//per tick

    boolean isConsume();
}
