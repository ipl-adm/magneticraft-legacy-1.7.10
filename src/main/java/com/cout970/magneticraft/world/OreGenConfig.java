package com.cout970.magneticraft.world;

public class OreGenConfig {

    public boolean active;
    public int amount_per_chunk;
    public int amount_per_vein;
    public int max_height;
    public int min_height;

    public OreGenConfig(boolean active, int amount_per_chunk, int amount_per_vein, int max_height, int min_height) {
        this.active = active;
        this.amount_per_chunk = amount_per_chunk;
        this.amount_per_vein = amount_per_vein;
        this.max_height = max_height;
        this.min_height = min_height;
    }
}
