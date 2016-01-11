package com.cout970.magneticraft.block.energy;

import com.cout970.magneticraft.block.BlockMg;
import com.cout970.magneticraft.tabs.CreativeTabsMg;
import com.cout970.magneticraft.tileentity.TileTransformerLow_Medium;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTransformerLow_Medium extends BlockMg {

    public BlockTransformerLow_Medium() {
        super(Material.iron);
        setCreativeTab(CreativeTabsMg.ElectricalAgeTab);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileTransformerLow_Medium();
    }

    @Override
    public String[] getTextures() {
        return new String[]{"void"};
    }


    @Override
    public String getName() {
        return "transformer_lm";
    }

    public void onBlockPlacedBy(World w, BlockPos pos, IBlockState state, EntityLivingBase p, ItemStack i) {
        //TODO vertical placement
        rotate(w, pos, state, p);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, BlockPos pos, EnumFacing facing) {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }
}
