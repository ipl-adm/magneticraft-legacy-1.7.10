package com.cout970.magneticraft.block;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.tabs.CreativeTabsMg;
import com.cout970.magneticraft.tileentity.TileCombustionEngine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCombustionEngine extends BlockMg {

    public BlockCombustionEngine() {
        super(Material.iron);
        setCreativeTab(CreativeTabsMg.IndustrialAgeTab);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileCombustionEngine();
    }

    public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer p, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if (p.isSneaking()) return false;
        p.openGui(Magneticraft.INSTANCE, 0, w, x, y, z);
        return true;
    }

    @Override
    public String[] getTextures() {
        return new String[]{"void"};
    }

    @Override
    public String getName() {
        return "combustion_engine";
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase p, ItemStack i) {
        int l = MathHelper.floor_double((double) (p.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        if (l == 0) {
            w.setBlockMetadataWithNotify(x, y, z, 3, 2);
        } else if (l == 1) {
            w.setBlockMetadataWithNotify(x, y, z, 4, 2);
        } else if (l == 2) {
            w.setBlockMetadataWithNotify(x, y, z, 2, 2);
        } else if (l == 3) {
            w.setBlockMetadataWithNotify(x, y, z, 5, 2);
        }
    }

}
