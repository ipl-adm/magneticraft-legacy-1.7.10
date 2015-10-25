package com.cout970.magneticraft.block.energy;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.block.BlockMg;
import com.cout970.magneticraft.tabs.CreativeTabsMg;
import com.cout970.magneticraft.tileentity.TileBattery;
import com.cout970.magneticraft.util.IBlockWithData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.ArrayList;

public class BlockBattery extends BlockMg {

    public BlockBattery() {
        super(Material.iron);
        setCreativeTab(CreativeTabsMg.ElectricalAgeTab);
    }

    public boolean hasComparatorInputOverride() {
        return true;
    }


    public int getComparatorInputOverride(World w, int x, int y, int z, int side) {
        TileEntity t = w.getTileEntity(x, y, z);
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

    public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer p, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if (p.isSneaking()) return false;
        p.openGui(Magneticraft.INSTANCE, 0, w, x, y, z);
        return true;
    }

    @Override
    public String[] getTextures() {
        return new String[]{"batterytop", "battery1", "battery2", "battery3", "battery4", "battery5", "battery6", "battery7", "battery8", "battery9", "battery10", "battery11", "battery12"};
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (side == 0 || side == 1) return icons[0];
        if (meta > 11) return icons[0];
        return icons[1 + meta];
    }

    @Override
    public String getName() {
        return "battery";
    }

    public void onBlockHarvested(World w, int x, int y, int z, int meta, EntityPlayer p) {
        if (!p.capabilities.isCreativeMode)
            dropBlockAsItem(w, x, y, z, meta, 0);
        super.onBlockHarvested(w, x, y, z, meta, p);
    }

    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<>();
        if (world.isRemote) return ret;
        if (!world.isRemote) {
            TileEntity b = world.getTileEntity(x, y, z);
            if (b instanceof IBlockWithData) {
                IBlockWithData d = (IBlockWithData) b;
                ItemStack drop = new ItemStack(this, 1, metadata);
                NBTTagCompound nbt = new NBTTagCompound();
                nbt.setBoolean(IBlockWithData.KEY, true);
                d.saveData(nbt);
                drop.stackTagCompound = nbt;
                ret.add(drop);
            }
        }
        return ret;
    }

    public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase p, ItemStack item) {
        if (item.stackTagCompound != null) {
            if (item.stackTagCompound.hasKey(IBlockWithData.KEY)) {
                TileEntity b = w.getTileEntity(x, y, z);
                if (b instanceof IBlockWithData) {
                    ((IBlockWithData) b).loadData(item.stackTagCompound);
                }
            }
        }
    }

}
