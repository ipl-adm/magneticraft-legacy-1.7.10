package com.cout970.magneticraft.world;

import java.util.LinkedList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.TempCategory;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fluids.FluidRegistry;

import com.cout970.magneticraft.ManagerBlocks;
import com.cout970.magneticraft.api.util.BlockInfo;
import com.cout970.magneticraft.util.Log;

import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenManagerMg implements IWorldGenerator{

	public static OreGenConfig GenCopper;
	public static OreGenConfig GenTungsten;
	public static OreGenConfig GenUranium;
	public static OreGenConfig GenSulfur;
	public static OreGenConfig GenThorium;
	public static OreGenConfig GenSalt;
	public static boolean GenOil;
	public static int GenOilProbability;
	public static int GenOilMaxHeight;
	public static int GenOilMinHeight;
	public static int GenOilMaxAmount;
	
	public WorldGenMinable Copper;
	public WorldGenMinable Tungsten;
	public WorldGenMinable Uranium;
	public WorldGenMinable Sulfur;
	public WorldGenMinable Thorium;
	public WorldGenMinable Salt;
	
	public WorldGenManagerMg(){
		Copper = new WorldGenMinable(ManagerBlocks.oreCopper, 0, GenCopper.amount_per_vein, Blocks.stone);
		Tungsten = new WorldGenMinable(ManagerBlocks.oreTungsten, 0, GenTungsten.amount_per_vein, Blocks.stone);
		Uranium = new WorldGenMinable(ManagerBlocks.oreUranium, 0, GenUranium.amount_per_vein, Blocks.stone);
		Sulfur = new WorldGenMinable(ManagerBlocks.oreSulfur, 0, GenSulfur.amount_per_vein, Blocks.stone);
		Thorium = new WorldGenMinable(ManagerBlocks.oreThorium, 0, GenThorium.amount_per_vein, Blocks.stone);
		Salt = new WorldGenMinable(ManagerBlocks.oreSalt, 0, GenSalt.amount_per_vein, Blocks.stone);
	}
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
	
		if (world.provider.dimensionId != 1 && world.provider.dimensionId != -1) {
			useOreGenConfig(random, world, chunkX, chunkZ, GenCopper, Copper);
			useOreGenConfig(random, world, chunkX, chunkZ, GenTungsten, Tungsten);
			useOreGenConfig(random, world, chunkX, chunkZ, GenSulfur, Sulfur);
			useOreGenConfig(random, world, chunkX, chunkZ, GenUranium, Uranium);
			useOreGenConfig(random, world, chunkX, chunkZ, GenSalt, Salt);
			useOreGenConfig(random, world, chunkX, chunkZ, GenThorium, Thorium);
			
			if (GenOil) {
				int run = GenOilProbability;
				BiomeGenBase base = world.getBiomeGenForCoords(chunkX << 4, chunkZ << 4);
				if(base != null){
					if(base.getIntRainfall() < 327680 || base.getTempCategory() == TempCategory.WARM) run *= 0.5;
					if(base.getTempCategory() == TempCategory.OCEAN) run *= 0.8;
					if(base.getTempCategory() == TempCategory.COLD) run *= 0.5;
				}
				if(random.nextInt(run) == 0){
					for(int d = 0; d < GenOilMaxAmount;d++){
						int cX = chunkX-3+random.nextInt(6);
						int cZ = chunkZ-3+random.nextInt(6);
						int y = world.getHeightValue(cX << 4, cZ << 4);
						generateSpere(random, world, cX << 4, y+10, cZ << 4, FluidRegistry.getFluid("oil").getBlock(), 0, false, 2.5f+random.nextInt(3));
						generateSpere_ore(random, world, cX << 4, GenOilMinHeight+random.nextInt(GenOilMaxHeight-GenOilMinHeight), cZ << 4, ManagerBlocks.oilSource, 15, false, 2.5f+random.nextInt(4));
					}
				}
			}
		}
	}
	
	public void useOreGenConfig(Random random,World world,int chunkX,int chunkZ, OreGenConfig conf, WorldGenMinable mine){
		if (conf.active) {
			genChunk(random, world, chunkX, chunkZ, conf.amount_per_chunk, conf.max_height, conf.min_height, mine);
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
	
	private void generateSpere(Random random, World world, int x, int y, int z, Block b, int meta, boolean flag, float rad){
		int max_it = (int) (Math.ceil(rad)+1);
		float rad_square = rad*rad;
		float rad_square_2 = (rad+1)*(rad+1);
		boolean ore = true;
		int offX = 8,offZ = 8,level = max_it,count = 0,water = 0,extension = 1;
		LinkedList<BlockInfo> list = new LinkedList<BlockInfo>();

		for(;y > 0;y--){
			if(canRemplace(world.getBlock(x, y, z)))break;
			if(Block.isEqualTo(Blocks.water, world.getBlock(x, y, z)))return;
		}
		if(y == 0)return;

		for(int j = -extension; j<= extension; j++){
			for(int i = -max_it; i<= max_it; i++){
				for(int k = -max_it; k<= max_it; k++){
					if(i*i+(j*j*8)+k*k < rad_square){//spere
						Block bl = world.getBlock(x+i+offX, y+j, z+k+offZ);
						if(!Block.isEqualTo(bl, Blocks.air) || flag){
							if(canRemplace(bl)){
								if(ore){
									list.add(new BlockInfo(b, meta, i, j, k));
									count++;
								}else{
									if(Block.isEqualTo(bl, Blocks.water))water++;
									list.add(new BlockInfo(Blocks.air, 0, i, j, k));
								}
							}else if(shouldVoid(bl)){
								list.add(new BlockInfo(Blocks.air, 0, i, j, k));
							}
						}else{
							list.add(new BlockInfo(Blocks.air, 0, i, j, k));
						}
					}else if(i*i+(j*j*8)+k*k < rad_square_2){//exterior
						Block bl = world.getBlock(x+i+offX, y+j, z+k+offZ);
						if(Block.isEqualTo(bl, Blocks.water)){
							water++;
							//list.add(new BlockInfo(Blocks.stone, 0, i, j, k));
						}
						if(Block.isEqualTo(bl, Blocks.air)){
							level = Math.min(j, level);
							ore = false;
						}
					}
				}
			}
		}

		if(count < 3)return;
		if(level < 1)return;
		if(water >= count)return;
		for(BlockInfo pos : list){
			if(pos.getBlock() == b){
				if(level > pos.getY()){
					world.setBlock(x+pos.getX()+offX, y+pos.getY(), z+pos.getZ()+offZ, pos.getBlock(), pos.getMeta(), 2);
				}else{
					world.setBlock(x+pos.getX()+offX, y+pos.getY(), z+pos.getZ()+offZ, Blocks.air, 0, 2);
				}
			}else{
				world.setBlock(x+pos.getX()+offX, y+pos.getY(), z+pos.getZ()+offZ, pos.getBlock(), pos.getMeta(), 2);
			}
		}
	}

	private void generateSpere_ore(Random random, World world, int x, int y, int z, Block b, int meta, boolean flag, float rad){
		int max_it = (int) (Math.ceil(rad)+1);
		float rad_square = rad*rad;
		float rad_square_2 = (rad+1)*(rad+1);
		LinkedList<BlockInfo> list = new LinkedList<BlockInfo>();

		for(int j = -max_it; j<= max_it; j++){
			for(int i = -max_it; i<= max_it; i++){
				for(int k = -max_it; k<= max_it; k++){
					if(i*i+j*j+k*k < rad_square){//spere
						Block bl = world.getBlock(x+i+8, y+j, z+k+8);
						if(!Block.isEqualTo(bl, Blocks.air) || flag){
							if(canRemplace(bl)){
								list.add(new BlockInfo(b, meta, i, j, k));
							}
						}
					}else if(i*i+j*j+k*k < rad_square_2){//exterior
						list.add(new BlockInfo(Blocks.stone, 0, i, j, k));
					}
				}
			}
		}

		for(BlockInfo pos : list){
			world.setBlock(x+pos.getX()+8, y+pos.getY(), z+pos.getZ()+8, pos.getBlock(), pos.getMeta(), 2);
		}
	}

	private boolean shouldVoid(Block bl) {
		if(Block.isEqualTo(bl, Blocks.deadbush))return true;
		if(Block.isEqualTo(bl, Blocks.cactus))return true;
		if(Block.isEqualTo(bl, Blocks.yellow_flower))return true;
		if(Block.isEqualTo(bl, Blocks.tallgrass))return true;
		return false;
	}

	public boolean canRemplace(Block b){
		if(Block.isEqualTo(b, Blocks.dirt))return true;
		if(Block.isEqualTo(b, Blocks.stone))return true;
		if(Block.isEqualTo(b, Blocks.grass))return true;
		if(Block.isEqualTo(b, Blocks.sand))return true;
		if(Block.isEqualTo(b, Blocks.gravel))return true;
		if(Block.isEqualTo(b, Blocks.sandstone))return true;
		if(Block.isEqualTo(b, Blocks.clay))return true;
		return false;
	}
}
