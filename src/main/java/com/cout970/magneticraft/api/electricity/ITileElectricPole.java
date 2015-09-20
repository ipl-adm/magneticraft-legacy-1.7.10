package com.cout970.magneticraft.api.electricity;


public interface ITileElectricPole {

    IElectricPole getPoleConnection();

    ITileElectricPole getMainTile();
}
