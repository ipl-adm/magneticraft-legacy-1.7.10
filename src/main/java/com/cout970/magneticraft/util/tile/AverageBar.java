package com.cout970.magneticraft.util.tile;

/**
 * Created by cout970 on 04/12/2015.
 */
public class AverageBar {

    private float lastTime;
    private float temp;
    private int counter;
    private int maxCounter;
    private float storage;

    public AverageBar(int maxCounter){
        this.maxCounter = maxCounter;
    }

    public void tick(){
        counter++;
        if(counter >= maxCounter){
            lastTime = temp/counter;
            temp = 0;
            counter = 0;
        }
    }

    public void addValue(float val){
        temp += val;
    }

    public float getAverage(){
        return lastTime;
    }

    public float getStorage() {
        return storage;
    }

    public void setStorage(float storage) {
        this.storage = storage;
    }
}
