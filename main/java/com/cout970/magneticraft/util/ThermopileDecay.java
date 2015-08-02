package com.cout970.magneticraft.util;

import java.util.List;
import java.util.Random;

import com.cout970.magneticraft.api.acces.IThermopileDecay;
import com.cout970.magneticraft.api.util.BlockInfo;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class ThermopileDecay implements IThermopileDecay{

	@Override
	public void onCheck(World w,List<BlockInfo> b, double tempHot, double tempCold) {
		if(tempHot > 100 && tempCold > 100){
			if(b.contains(new BlockInfo(Blocks.lava,-1))){
				Random r = new Random();
				for(BlockInfo i : b){
					if(i.getBlock() == Blocks.lava){
						if(r.nextInt(300) == 0){
							w.setBlock(i.getPosition()[0], i.getPosition()[1], i.getPosition()[2], Blocks.obsidian);
						}
					}
				}
			}
		}
	}

}
