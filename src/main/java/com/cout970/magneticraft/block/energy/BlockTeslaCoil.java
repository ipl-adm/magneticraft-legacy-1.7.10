package com.cout970.magneticraft.block.energy;

import com.cout970.magneticraft.block.BlockMg;
import com.cout970.magneticraft.tabs.CreativeTabsMg;
import com.cout970.magneticraft.tileentity.TileTeslaCoil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTeslaCoil extends BlockMg {

    public BlockTeslaCoil() {
        super(Material.iron);
        setCreativeTab(CreativeTabsMg.ElectricalAgeTab);
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
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public void onBlockAdded(World w, int x, int y, int z) {
        super.onBlockAdded(w, x, y, z);
        int meta = w.getBlockMetadata(x, y, z);
        if (meta == 0)
            for (int d = 1; d <= 2; d++) {
                w.setBlock(x, y + d, z, this, d, 2);
            }
    }

    @Override
    public boolean canPlaceBlockAt(World w, int x, int y, int z) {
        for (int d = 0; d <= 2; d++) {
            Block block = w.getBlock(x, y + d, z);
            if (block != null && !block.isReplaceable(w, x, y + d, z)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void breakBlock(World w, int x, int y, int z, Block b, int meta) {
        super.breakBlock(w, x, y, z, b, meta);
        if (meta == 0) {
            for (int d = 1; d <= 2; d++) {
                w.setBlockToAir(x, y + d, z);
            }
        } else {
            if (meta == 1) {
                w.setBlockToAir(x, y - 1, z);
            } else {
                w.setBlockToAir(x, y - 2, z);
            }
        }
    }
}
