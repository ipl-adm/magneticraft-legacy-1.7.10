package com.cout970.magneticraft.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.TempCategory;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.structure.ComponentScatteredFeaturePieces.DesertPyramid;
import net.minecraftforge.fluids.FluidRegistry;

import com.cout970.magneticraft.ManagerBlocks;

import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenManagerMg implements IWorldGenerator{

	public static boolean GenCopper = true;
	public static boolean GenTungsten = true;
	public static boolean GenUranium = true;
	public static boolean GenSulfur = true;
	public static boolean GenThorium = true;
	public static boolean GenSalt = true;
	public static boolean GenOil = true;
	
	public WorldGenMinable Copper;
	public WorldGenMinable Tungsten;
	public WorldGenMinable Uranium;
	public WorldGenMinable Sulfur;
	public WorldGenMinable Thorium;
	public WorldGenMinable Salt;
	
	public WorldGenManagerMg(){
		Copper = new WorldGenMinable(ManagerBlocks.oreCopper, 0, 8, Blocks.stone);
		Tungsten = new WorldGenMinable(ManagerBlocks.oreTungsten, 0, 1, Blocks.stone);
		Uranium = new WorldGenMinable(ManagerBlocks.oreUranium, 0, 3, Blocks.stone);
		Sulfur = new WorldGenMinable(ManagerBlocks.oreSulfur, 0, 12, Blocks.stone);
		Thorium = new WorldGenMinable(ManagerBlocks.oreThorium, 0, 6, Blocks.stone);
		Salt = new WorldGenMinable(ManagerBlocks.oreSalt, 0, 8, Blocks.stone);
	}
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if (world.provider.dimensionId != 1 && world.provider.dimensionId != -1) {
			if (GenCopper) {
				genChunk(random, world, chunkX, chunkZ, 6, 80, 30, Copper);
			}
			if (GenTungsten) {
				genChunk(random, world, chunkX, chunkZ, 1, 10, 0, Tungsten);
			}
			if (GenSulfur) {
				genChunk(random, world, chunkX, chunkZ, 3, 12, 0, Sulfur);
			}
			if (GenUranium) {
				genChunk(random, world, chunkX, chunkZ, 3, 80, 0, Uranium);
			}
			if (GenSalt) {
				genChunk(random, world, chunkX, chunkZ, 6, 80, 0, Salt);
			}
			if (GenThorium) {
				genChunk(random, world, chunkX, chunkZ, 5, 20, 0, Thorium);
			}
			if (GenOil) {
				int run = 400;
				BiomeGenBase base = world.getBiomeGenForCoords(chunkX << 4, chunkZ << 4);
				if(base.getFloatRainfall() < 0.5 || base.getTempCategory() == TempCategory.WARM) run -= 200;
				if(base.getTempCategory() == TempCategory.OCEAN) run -= 150;
				if(base.getTempCategory() == TempCategory.COLD) run -= 100;
				
				if(random.nextInt(run) == 0){
					for(int i = -1;i<=1;i++)
						for(int j = -1;j<=1;j++){
							int x = (chunkX+i)*16 + random.nextInt(16);
							int y = random.nextInt(30);
							int z = (chunkZ+j)*16 + random.nextInt(16);
							generateLake(random, world, x,  world.getHeightValue(chunkX << 4, chunkZ << 4), z, FluidRegistry.getFluid("oil").getBlock(),0,false);
							generateLake(random, world, x,  random.nextInt(20)+10, z, ManagerBlocks.oilSource,15,true);
						}
				}
			}
		}
	}


	public void genChunk(Random r, World w, int cx, int cz, int a, int h,int hm,WorldGenMinable wgm){
		for(int k = 0; k < a; k++) {
			int x = cx*16 + r.nextInt(16);
			int y = hm + r.nextInt(h-hm);
			int z = cz*16 + r.nextInt(16);
			wgm.generate(w, r, x, y, z);
		}
	}
	
	private void generateLake(Random random, World world, int x, int y, int z, Block b, int meta,boolean flag) {
		int radius = 2+random.nextInt(3);
		int height = 1;
		int radsquared = radius*radius;
		for(int i = -radius; i <= radius;i++){
			for(int k = -radius; k <= radius;k++){
				for(int j = -height; j <= height;j++){
					if(i*i+j*j+k*k < radsquared){
						Block bl = world.getBlock(x+i, y+j, z+k);
						if(canRemplace(bl) && (flag || j < 0 || bl == Blocks.water)){
							world.setBlock(x+i, y+j, z+k, b, meta,2);
						}else{
							world.setBlock(x+i, y+j, z+k, Blocks.air, 0, 2);
						}
					}
				}
			}
		}
	}
	
	public boolean canRemplace(Block b){
		if(Block.isEqualTo(b, Blocks.dirt))return true;
		if(Block.isEqualTo(b, Blocks.stone))return true;
		if(Block.isEqualTo(b, Blocks.grass))return true;
		if(Block.isEqualTo(b, Blocks.sand))return true;
		if(Block.isEqualTo(b, Blocks.gravel))return true;
		if(Block.isEqualTo(b, Blocks.sandstone))return true;
		if(Block.isEqualTo(b, Blocks.water))return true;
		return false;
	}
}
