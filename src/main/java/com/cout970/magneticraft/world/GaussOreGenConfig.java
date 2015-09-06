package com.cout970.magneticraft.world;

public class GaussOreGenConfig extends OreGenConfig {
    public int min_chunk;
    public int max_chunk;
    public float deviation;

    public GaussOreGenConfig(boolean active, int amount_per_chunk, float deviation, int min_chunk, int max_chunk, int amount_per_vein, int max_height, int min_height) {
        super(active, amount_per_chunk, amount_per_vein, max_height, min_height);
        this.deviation = deviation;
        this.min_chunk = min_chunk;
        this.max_chunk = max_chunk;
    }
}
