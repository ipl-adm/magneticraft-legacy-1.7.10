package com.cout970.magneticraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import com.cout970.magneticraft.tabs.CreativeTabsMg;

public class BlockOfIngots extends Block{

	public String name;
	
	public BlockOfIngots(String n) {
		super(Material.iron);
		name = n;
		setHardness(1.5f);
		setBlockTextureName("magneticraft:"+name);
		setCreativeTab(CreativeTabsMg.MainTab);
		
	}
	
	public String getUnlocalizedName(){
        return name;
    }
}
