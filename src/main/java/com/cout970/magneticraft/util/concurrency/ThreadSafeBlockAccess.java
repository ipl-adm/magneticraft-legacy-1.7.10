package com.cout970.magneticraft.util.concurrency;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.util.ForgeDirection;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class ThreadSafeBlockAccess implements IBlockAccess {
    private static Map<World, ThreadSafeBlockAccess> INSTANCES = new HashMap<>();

    private final World world;

    private AtomicReference<Map<Pair<Integer, Integer>, Chunk>> cache;

    private ThreadSafeBlockAccess(World w) {
        world = w;
        cache = new AtomicReference<>();
        cache.set(new HashMap<>());
    }

    public static ThreadSafeBlockAccess getAccess(World w) {
        ThreadSafeBlockAccess access = INSTANCES.get(w);
        if (access == null) {
            access = new ThreadSafeBlockAccess(w);
            INSTANCES.put(w, access);
        }

        return access;
    }

    @Override
    public Block getBlock(int x, int y, int z) {
        return getChunk(x >> 4, z >> 4).getBlock(x & 15, y, z & 15);
    }

    @Override
    public TileEntity getTileEntity(int x, int y, int z) {
        Chunk chunk = getChunk(x >> 4, z >> 4);
        return (TileEntity) chunk.chunkTileEntityMap.get(new ChunkPosition(x & 15, y, z & 15));
    }

    @Override
    public int getLightBrightnessForSkyBlocks(int x, int y, int z, int p_72802_4_) {
        return 0;
    }

    @Override
    public int getBlockMetadata(int x, int y, int z) {
        return getChunk(x >> 4, z >> 4).getBlockMetadata(x, y, z);
    }

    @Override
    public int isBlockProvidingPowerTo(int x, int y, int z, int p_72879_4_) {
        return 0;
    }

    @Override
    public boolean isAirBlock(int x, int y, int z) {
        return getBlock(x, y, z).isAir(this, x, y, z);
    }

    @Override
    public BiomeGenBase getBiomeGenForCoords(int x, int y) {
        return null;
    }

    @Override
    public int getHeight() {
        return 256;
    }

    @Override
    public boolean extendedLevelsInChunkCache() {
        return false;
    }

    @Override
    public boolean isSideSolid(int x, int y, int z, ForgeDirection side, boolean _default) {
        return false;
    }

    public Chunk getChunk(int x, int z) {
        Chunk chunk = cache.get().get(Pair.of(x, z));

        if (chunk == null) {
            final Chunk chunk2 = loadChunk(x, z);
            cache.updateAndGet(m -> {m.put(Pair.of(x, z), chunk2); return m;});
            chunk = chunk2;
        }

        return chunk;
    }

    private synchronized Chunk loadChunk(int x, int z) {
        return world.getChunkFromChunkCoords(x, z);
    }
}
