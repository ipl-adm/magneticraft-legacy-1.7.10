package com.cout970.magneticraft.api.conveyor;

public interface IHitBoxArray {

    int size();

    boolean hasSpace(int pos);

    void setOccupied(int pos, boolean occupied);

    void clear();
}
