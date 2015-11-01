package com.cout970.magneticraft.block.multiblock.controllers;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.block.BlockMg;
import com.cout970.magneticraft.tabs.CreativeTabsMg;
import com.cout970.magneticraft.tileentity.multiblock.controllers.TileCrusher;
import com.cout970.magneticraft.util.multiblock.*;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCrusher extends BlockMg implements MB_ControlBlock {

    public BlockCrusher() {
        super(Material.iron);
        setCreativeTab(CreativeTabsMg.IndustrialAgeTab);
        setLightOpacity(0);
    }

    @Override
    public TileEntity createNewTileEntity(World w, int meta) {
        return new TileCrusher();
    }

    @Override
    public String[] getTextures() {
        return new String[]{"crusher", "chassis", "crusher_inv"};
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public String getName() {
        return "crusher_control";
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return side == meta + 2 ? icons[0] : side == meta % 4 + 2 ? icons[2] : icons[1];
    }

    public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer p, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if (p.isSneaking()) return false;
        if ((p.getCurrentEquippedItem() != null) && MgUtils.isWrench(p.getCurrentEquippedItem().getItem())) {
            TileEntity t = w.getTileEntity(x, y, z);
            if (!w.isRemote) {
                int meta = w.getBlockMetadata(x, y, z);

                if (meta % 8 >= 4) {
                    w.setBlockMetadataWithNotify(x, y, z, meta % 4, 2);
                } else {
                    w.setBlockMetadataWithNotify(x, y, z, meta % 4 + 4, 2);
                }

                if (t instanceof MB_Tile) {
                    if (((MB_Tile) t).getControlPos() != null && ((MB_Tile) t).getMultiblock() != null)
                        MB_Watcher.destroyStructure(w, ((MB_Tile) t).getControlPos(), ((MB_Tile) t).getMultiblock(), ((MB_Tile) t).getDirection());
                }
            }
            if (t instanceof TileCrusher) {
                ((TileCrusher) t).onNeigChange();
            }
            return false;
        }
        TileEntity t = w.getTileEntity(x, y, z);
        if (t instanceof TileCrusher) {
            if (!((TileCrusher) t).isActive()) {
                if (!w.isRemote)
                    MB_Watcher.watchStructure(w, new VecInt(x, y, z), MB_Register.getMBbyID(MB_Register.ID_CRUSHER), getDirection(w, new VecInt(x, y, z)), p);
                else ((TileCrusher) t).drawCounter = 200;
            } else {
                p.openGui(Magneticraft.INSTANCE, 0, w, x, y, z);
            }
        }
        return true;
    }

    public void breakBlock(World w, int x, int y, int z, Block b, int side) {
        if (!w.isRemote) {
            TileEntity t = w.getTileEntity(x, y, z);
            if (t instanceof MB_Tile) {
                if (((MB_Tile) t).getControlPos() != null && ((MB_Tile) t).getMultiblock() != null)
                    MB_Watcher.destroyStructure(w, ((MB_Tile) t).getControlPos(), ((MB_Tile) t).getMultiblock(), ((MB_Tile) t).getDirection());
            }
        }
        super.breakBlock(w, x, y, z, b, side);
    }

    public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase p, ItemStack i) {
        int l = MathHelper.floor_double((double) (p.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        if (l == 0) {
            w.setBlockMetadataWithNotify(x, y, z, 0, 2);
        }

        if (l == 1) {
            w.setBlockMetadataWithNotify(x, y, z, 3, 2);
        }

        if (l == 2) {
            w.setBlockMetadataWithNotify(x, y, z, 1, 2);
        }

        if (l == 3) {
            w.setBlockMetadataWithNotify(x, y, z, 2, 2);
        }
    }

    @Override
    public MgDirection getDirection(World w, VecInt p) {
        int meta = w.getBlockMetadata(p.getX(), p.getY(), p.getZ());
        return MgDirection.getDirection(meta % 4 + 2);
    }

    @Override
    public Multiblock getStructure() {
        return MB_Register.getMBbyID(MB_Register.ID_CRUSHER);
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess w, int x, int y, int z, int side) {
        MgDirection d = MgDirection.getDirection(side);
        return (w.getBlockMetadata(x - d.getOffsetX(), y - d.getOffsetY(), z - d.getOffsetZ()) < 8)
                && super.shouldSideBeRendered(w, x, y, z, side);
    }

    @Override
    public void mutates(World w, VecInt p, Multiblock c, MgDirection e) {
        int meta = w.getBlockMetadata(p.getX(), p.getY(), p.getZ());
        w.setBlockMetadataWithNotify(p.getX(), p.getY(), p.getZ(), meta % 8 + 8, 2);
    }

    @Override
    public void destroy(World w, VecInt p, Multiblock c, MgDirection e) {
        int meta = w.getBlockMetadata(p.getX(), p.getY(), p.getZ());
        w.setBlockMetadataWithNotify(p.getX(), p.getY(), p.getZ(), meta % 8, 2);
    }
}
