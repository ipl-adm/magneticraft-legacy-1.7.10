package com.cout970.magneticraft.block;

import com.cout970.magneticraft.tabs.CreativeTabsMg;
import com.cout970.magneticraft.tileentity.TileBase;
import com.cout970.magneticraft.tileentity.shelf.TileShelf;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.Random;

public abstract class BlockMg extends BlockContainer {
    public static String base = "magneticraft:";
    public PropertyDirection FACING;

    public BlockMg(Material m) {
        super(m);
        setCreativeTab(CreativeTabsMg.MainTab);
        setHardness(2.0f);
        FACING = PropertyDirection.create("facing");
        isBlockContainer = true;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    public static void dropItem(ItemStack item, Random rand, BlockPos pos, World w) {
        if (item != null && item.stackSize > 0) {
            float rx = rand.nextFloat() * 0.8F + 0.1F;
            float ry = rand.nextFloat() * 0.8F + 0.1F;
            float rz = rand.nextFloat() * 0.8F + 0.1F;
            EntityItem entityItem = new EntityItem(w,
                    pos.getX() + rx, pos.getY() + ry, pos.getZ() + rz,
                    new ItemStack(item.getItem(), item.stackSize, item.getItemDamage()));
            if (item.hasTagCompound()) {
                entityItem.getEntityItem().setTagCompound((NBTTagCompound) item.getTagCompound().copy());
            }
            float factor = 0.05F;
            entityItem.motionX = rand.nextGaussian() * factor;
            entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
            entityItem.motionZ = rand.nextGaussian() * factor;
            w.spawnEntityInWorld(entityItem);
            item.stackSize = 0;
        }
    }

    @Override
    public void onNeighborChange(IBlockAccess w, BlockPos pos, BlockPos neig) {
        TileEntity t = w.getTileEntity(pos);
        if (t instanceof TileBase) {
            ((TileBase) t).onNeigChange();
            t.markDirty();
        }
    }

    @Override
    public void breakBlock(World w, BlockPos pos, IBlockState state) {
        TileEntity t = w.getTileEntity(pos);
        if (t instanceof TileBase) {
            ((TileBase) t).onBlockBreaks();
        }

        if (w.isRemote) return;
        if ((t instanceof IInventory) && !(t instanceof TileShelf)) {
            IInventory inventory = (IInventory) t;
            Random rand = w.rand;
            for (int i = 0; i < inventory.getSizeInventory(); i++) {
                dropItem(inventory.getStackInSlot(i), rand, pos, w);
            }
        }

        super.breakBlock(w, pos, state);
    }

    public String getUnlocalizedName() {
        return getName();
    }

    public abstract String[] getTextures();

    public abstract String getName();

    @Override
    public int getMetaFromState(IBlockState state) {
        EnumFacing facing = (EnumFacing) state.getProperties().get(FACING);
        return facing.ordinal();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getBlockState().getBaseState().withProperty(FACING, EnumFacing.getFront(meta));
    }

    public void rotate(World w, BlockPos pos, IBlockState state, EntityLivingBase p) {
        w.setBlockState(pos, state.withProperty(FACING, EnumFacing.fromAngle(p.rotationYaw)));
    }
}
