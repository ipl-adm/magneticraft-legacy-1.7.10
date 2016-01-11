package com.cout970.magneticraft.block.energy;

import com.cout970.magneticraft.block.BlockMg;
import com.cout970.magneticraft.tabs.CreativeTabsMg;
import com.cout970.magneticraft.tileentity.TileTransformerMedium_High;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTransformerMedium_High extends BlockMg {

    public BlockTransformerMedium_High() {
        super(Material.iron);
        setCreativeTab(CreativeTabsMg.ElectricalAgeTab);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileTransformerMedium_High();
    }

    @Override
    public String[] getTextures() {
        return new String[]{"void"};
    }

    @Override
    public String getName() {
        return "transformer_mh";
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public void onBlockPlacedBy(World w, BlockPos pos, IBlockState state, EntityLivingBase p, ItemStack i) {
        //TODO vertical placement
        rotate(w, pos, state, p);
    }
}
