package com.cout970.magneticraft.block;

import com.cout970.magneticraft.tabs.CreativeTabsMg;
import com.cout970.magneticraft.tileentity.TileBase;
import com.cout970.magneticraft.tileentity.shelf.TileShelf;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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
    public IIcon[] icons;

    public BlockMg(Material m) {
        super(m);
        setCreativeTab(CreativeTabsMg.MainTab);
        setHardness(2.0f);
    }

    public static void dropItem(ItemStack item, Random rand, int x, int y, int z, World w) {
        if (item != null && item.stackSize > 0) {
            float rx = rand.nextFloat() * 0.8F + 0.1F;
            float ry = rand.nextFloat() * 0.8F + 0.1F;
            float rz = rand.nextFloat() * 0.8F + 0.1F;
            EntityItem entityItem = new EntityItem(w,
                    x + rx, y + ry, z + rz,
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

    public void onNeighborBlockChange(World w, int x, int y, int z, Block b) {
        TileEntity t = w.getTileEntity(x, y, z);
        if (t instanceof TileBase) {
            ((TileBase) t).onNeigChange();
            ((TileBase) t).sendUpdateToClient();
        }
    }

    public void breakBlock(World w, int x, int y, int z, Block b, int meta) {
        TileEntity t = w.getTileEntity(x, y, z);
        if (t instanceof TileBase) {
            ((TileBase) t).onBlockBreaks();
        }
        super.breakBlock(w, x, y, z, b, meta);
    }

    public String getUnlocalizedName() {
        return getName();
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return icons[0];
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister IR) {
        int pos = 0;
        icons = new IIcon[getTextures().length];
        for (String name : getTextures())
            icons[pos++] = IR.registerIcon(base + name);
    }

    public abstract String[] getTextures();

    public abstract String getName();

    public void onBlockPreDestroy(World w, int x, int y, int z, int meta) {
        super.onBlockPreDestroy(w, x, y, z, meta);
        if (w.isRemote) return;
        TileEntity tileEntity = w.getTileEntity(x, y, z);
        if ((tileEntity instanceof IInventory) && !(tileEntity instanceof TileShelf)) {
            IInventory inventory = (IInventory) tileEntity;
            Random rand = w.rand;
            for (int i = 0; i < inventory.getSizeInventory(); i++) {
                dropItem(inventory.getStackInSlot(i), rand, x, y, z, w);
            }
        }
    }
}
