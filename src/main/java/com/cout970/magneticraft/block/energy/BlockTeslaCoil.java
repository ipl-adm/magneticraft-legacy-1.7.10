package com.cout970.magneticraft.block.energy;

import com.cout970.magneticraft.block.BlockMg;
import com.cout970.magneticraft.tabs.CreativeTabsMg;
import com.cout970.magneticraft.tileentity.TileTeslaCoil;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTeslaCoil extends BlockMg {
    public PropertyInteger HEIGHT;

    public BlockTeslaCoil() {
        super(Material.iron);
        setCreativeTab(CreativeTabsMg.ElectricalAgeTab);
        HEIGHT = PropertyInteger.create("height", 0, 2);
}

    @Override
    public TileEntity createNewTileEntity(World w, int m) {
        if (m == 0)
            return new TileTeslaCoil();
        return null;
    }

    @Override
    public String[] getTextures() {
        return new String[]{"void"};
    }

    @Override
    public String getName() {
        return "teslacoil";
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess w, int x, int y, int z, int side) {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public void onBlockAdded(World w, BlockPos pos, IBlockState state) {
        super.onBlockAdded(w, pos, state);
        int meta = state.getValue(HEIGHT);
        if (meta == 0)
            for (int d = 1; d <= 2; d++) {
                w.setBlockState(pos.up(d), state.withProperty(HEIGHT, d), 2);
            }
    }

    @Override
    public boolean canPlaceBlockAt(World w, BlockPos pos) {
        for (int d = 0; d <= 2; d++) {
            Block block = w.getBlockState(pos.up(d)).getBlock();
            if (block != null && !block.isReplaceable(w, pos.up(d))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void breakBlock(World w, BlockPos pos, IBlockState state) {
        super.breakBlock(w, pos, state);
        int meta = state.getValue(HEIGHT);
        if (meta == 0) {
            for (int d = 1; d <= 2; d++) {
                w.setBlockToAir(pos.up(d));
            }
        } else {
            if (meta == 1) {
                w.setBlockToAir(pos.down());
            } else {
                w.setBlockToAir(pos.down(2));
            }
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getBlockState().getBaseState().withProperty(HEIGHT, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(HEIGHT);
    }
}
