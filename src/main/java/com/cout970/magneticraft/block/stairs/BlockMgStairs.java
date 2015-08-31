package com.cout970.magneticraft.block.stairs;

import com.cout970.magneticraft.tabs.CreativeTabsMg;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;

public class BlockMgStairs extends BlockStairs {

        public BlockMgStairs(Block source, String name) {
            super(source, 0);
            setBlockName(name);
            setCreativeTab(CreativeTabsMg.MainTab);
            setLightOpacity(0);
        }
}
