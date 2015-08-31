package com.cout970.magneticraft.block.compat;

import com.cout970.magneticraft.block.BlockMg;
import com.cout970.magneticraft.tileentity.TileEUAlternator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockEUAlternator extends BlockMg {

    public BlockEUAlternator() {
        super(Material.iron);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileEUAlternator();
    }

    @Override
    public String[] getTextures() {
        return new String[]{"void"};
    }

    @Override
    public String getName() {
        return "eu_alternator";
    }

    public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase p, ItemStack i) {
        int l = MathHelper.floor_double((double) (p.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        if (l == 0) {
            w.setBlockMetadataWithNotify(x, y, z, 3, 2);
        }
        if (l == 1) {
            w.setBlockMetadataWithNotify(x, y, z, 4, 2);
        }
        if (l == 2) {
            w.setBlockMetadataWithNotify(x, y, z, 2, 2);
        }
        if (l == 3) {
            w.setBlockMetadataWithNotify(x, y, z, 5, 2);
        }
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
        return false;
    }
}
