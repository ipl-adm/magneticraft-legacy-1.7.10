package com.cout970.magneticraft.block.computer;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.block.BlockMg;
import com.cout970.magneticraft.handlers.GuiHandler;
import com.cout970.magneticraft.tabs.CreativeTabsMg;
import com.cout970.magneticraft.tileentity.TileComputer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockComputer extends BlockMg {

    public BlockComputer() {
        super(Material.iron);
        setCreativeTab(CreativeTabsMg.InformationAgeTab);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileComputer();
    }


    @Override
    public boolean onBlockActivated(World w, BlockPos pos, IBlockState state, EntityPlayer p, EnumFacing facing, float hitx, float hity, float hitz) {
        if (p.isSneaking()) return false;
        GuiHandler.open(p, w, pos);
        return true;
    }

    @Override
    public String[] getTextures() {
        return new String[]{"cpu", "cpu_head"};
    }

    @Override
    public String getName() {
        return "cpu";
    }

    @Override
    public void onBlockPlacedBy(World w, BlockPos pos, IBlockState state, EntityLivingBase p, ItemStack i) {
        rotate(w, pos, state, p);
    }
}
