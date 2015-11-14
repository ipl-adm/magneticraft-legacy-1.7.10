package com.cout970.magneticraft.util;

import com.cout970.magneticraft.api.access.IThermophileDecay;
import com.cout970.magneticraft.api.util.BlockInfo;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class ThermophileDecay implements IThermophileDecay {

    @Override
    public void onCheck(World w, List<BlockInfo> b, double tempHot, double tempCold) {
        if (tempHot > 100 && tempCold > 100) {
            if (b.contains(new BlockInfo(Blocks.lava, -1))) {
                Random r = new Random();
                b.stream().filter(i -> i.getBlock() == Blocks.lava).filter(i -> r.nextInt(300) == 0).forEach(i -> w.setBlock(i.getPosition()[0], i.getPosition()[1], i.getPosition()[2], Blocks.obsidian));
            }
        }
    }

}
