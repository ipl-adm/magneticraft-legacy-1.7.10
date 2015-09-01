package com.cout970.magneticraft.world;

public class GaussOreGenConfig extends OreGenConfig {
    public int max_deviation;
    public float deviation;

    public GaussOreGenConfig(boolean active, int amount_per_chunk, float deviation, int max_deviation, int amount_per_vein, int max_height, int min_height) {
        super(active, amount_per_chunk, amount_per_vein, max_height, min_height);
        this.deviation = deviation;
        this.max_deviation = max_deviation;
    }
}
