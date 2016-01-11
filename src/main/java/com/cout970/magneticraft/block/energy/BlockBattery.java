package com.cout970.magneticraft.block.energy;

import com.cout970.magneticraft.block.BlockMg;
import com.cout970.magneticraft.handlers.GuiHandler;
import com.cout970.magneticraft.tabs.CreativeTabsMg;
import com.cout970.magneticraft.tileentity.TileBattery;
import com.cout970.magneticraft.util.IBlockWithData;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.ArrayList;

public class BlockBattery extends BlockMg {

    public BlockBattery() {
        super(Material.iron);
        setCreativeTab(CreativeTabsMg.ElectricalAgeTab);
    }

    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }

    @Override
    public int getComparatorInputOverride(World w, BlockPos pos) {
        TileEntity t = w.getTileEntity(pos);
        if (t instanceof TileBattery) {
            TileBattery b = (TileBattery) t;
            return Math.min(b.cond.getStorage() * 16 / b.cond.getMaxStorage(), 15);
        }
        return 0;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileBattery();
    }

    @Override
    public boolean onBlockActivated(World w, BlockPos pos, IBlockState state, EntityPlayer p, EnumFacing facing, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if (p.isSneaking()) return false;
        GuiHandler.open(p, w, pos);
        return true;
    }

    @Override
    public String[] getTextures() {
        return new String[]{"batterytop", "battery1", "battery2", "battery3", "battery4", "battery5", "battery6", "battery7", "battery8", "battery9", "battery10", "battery11", "battery12"};
    }

    @Override
    public String getName() {
        return "battery";
    }

    @Override
    public void onBlockHarvested(World w, BlockPos pos, IBlockState state, EntityPlayer p) {
        if (!p.capabilities.isCreativeMode)
            dropBlockAsItem(w, pos, state, 0);
        super.onBlockHarvested(w, pos, state, p);
    }

    @Override
    public ArrayList<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<>();
        if ((world instanceof World) && ((World) world).isRemote) {
            return ret;
        }

        TileEntity b = world.getTileEntity(pos);
        if (b instanceof IBlockWithData) {
            IBlockWithData d = (IBlockWithData) b;
            ItemStack drop = new ItemStack(this, 1, getMetaFromState(state));
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setBoolean(IBlockWithData.KEY, true);
            d.saveData(nbt);
            drop.setTagCompound(nbt);
            ret.add(drop);
        }
        return ret;
    }

    @Override
    public void onBlockPlacedBy(World w, BlockPos pos, IBlockState state, EntityLivingBase p, ItemStack item) {
        if (item.getTagCompound() != null) {
            if (item.getTagCompound().hasKey(IBlockWithData.KEY)) {
                TileEntity b = w.getTileEntity(pos);
                if (b instanceof IBlockWithData) {
                    ((IBlockWithData) b).loadData(item.getTagCompound());
                }
            }
        }
    }

}
