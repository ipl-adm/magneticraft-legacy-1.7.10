package com.cout970.magneticraft.block;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.tabs.CreativeTabsMg;
import com.cout970.magneticraft.tileentity.TilePumpJack;
import com.cout970.magneticraft.tileentity.TilePumpjackEnergyLink;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockPumpJack extends BlockMg {

    public BlockPumpJack() {
        super(Material.iron);
        setCreativeTab(CreativeTabsMg.IndustrialAgeTab);
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
        return false;
    }

    public Item getItemDropped(int meta, Random rand, int fort) {
        return Item.getItemFromBlock(this);
    }

    public void breakBlock(World w, int x, int y, int z, Block b, int meta) {
        if (meta >= MgDirection.AXIX_Y.length) {
            meta -= 4;
            if (meta >= MgDirection.AXIX_Y.length) {
                meta -= 4;
                if (meta >= MgDirection.AXIX_Y.length) {
                    w.setBlockToAir(x, y - 1, z);
                } else {
                    MgDirection dir = MgDirection.AXIX_Y[meta];
                    w.setBlockToAir(x + dir.getOffsetX(), y, z + dir.getOffsetZ());
                }
            } else {
                MgDirection dir = MgDirection.AXIX_Y[meta];
                w.setBlockToAir(x + dir.getOffsetX(), y, z + dir.getOffsetZ());
            }
        } else {
            MgDirection dir = MgDirection.AXIX_Y[meta];
            VecInt pos = new VecInt(x, y, z);
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (i == 0 && j == 0) continue;
                    VecInt check = pos.copy().add(dir.toVecInt().multiply(i).add(0, j, 0));
                    w.setBlockToAir(check.getX(), check.getY(), check.getZ());
                }
            }
        }
    }

    @Override
    public TileEntity createNewTileEntity(World w, int m) {
        if (m <= 3)
            return new TilePumpJack();
        if (m >= 8 && m < 12)
            return new TilePumpjackEnergyLink();
        return null;
    }

    @Override
    public String[] getTextures() {
        return new String[]{"void"};
    }

    @Override
    public String getName() {
        return "pumpjack_1";
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public boolean isOpaqueCube() {
        return false;
    }
}
