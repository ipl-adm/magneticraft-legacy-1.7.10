package com.cout970.magneticraft.api.util;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;

/**
 * An Object to store all possible data from a single block
 *
 * @author Cout970
 */
public class BlockInfo {

    private IBlockState state;
    private BlockPos pos;

    public BlockInfo(IBlockState state) {
        this.state = state;
    }

    public BlockInfo(IBlockState state, int x, int y, int z) {
        this(state, new BlockPos(x, y, z));
    }

    public BlockInfo(IBlockState state, BlockPos a) {
        this(state);
        pos = a;
    }

    public Block getBlock() {
        return state.getBlock();
    }

    public IBlockState getState() {
        return state;
    }

    public BlockPos getPosition() {
        return pos;
    }

    public boolean equals(BlockInfo info) {
        return (pos.equals(info.getPosition()) && state.equals(info.getState()));
    }

    public String toString() {
        return "Block: " + state.getBlock().getUnlocalizedName() + " State: " + state.toString() + " Pos: " + pos.toString();
    }

    public int getX() {
        return pos.getX();
    }

    public int getY() {
        return pos.getY();
    }

    public int getZ() {
        return pos.getZ();
    }
}
