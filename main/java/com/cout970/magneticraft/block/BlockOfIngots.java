package com.cout970.magneticraft.block;

import ic2.core.coremod.Setup;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.cout970.magneticraft.ManagerBlocks;
import com.cout970.magneticraft.ManagerItems;
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
