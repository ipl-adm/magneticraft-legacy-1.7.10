package com.cout970.magneticraft.util;

import net.minecraft.block.Block;

public class NamedBlock {

    public Block block;
    public String name;

    public NamedBlock(Block i, String n) {
        name = n;
        block = i;
    }
}
