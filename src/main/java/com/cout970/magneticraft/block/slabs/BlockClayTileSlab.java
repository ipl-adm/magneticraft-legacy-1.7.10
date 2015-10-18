package com.cout970.magneticraft.block.slabs;

import com.cout970.magneticraft.ManagerBlocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockSlab;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockClayTileSlab extends BlockMgSlab {

    public BlockClayTileSlab(boolean full) {
        super(full, ManagerBlocks.roofTile.getMaterial(), ManagerBlocks.roofTile.getUnlocalizedName() + "Slab" + (full ? "Full" : ""));
        setHardness(1.5F);
        setStepSound(soundTypeStone);
        setHarvestLevel(ManagerBlocks.roofTile.getHarvestTool(0), ManagerBlocks.roofTile.getHarvestLevel(0));
    }

    @Override
    public BlockSlab getFullBlock() {
        return ManagerBlocks.slabRoofTileDouble;
    }

    @Override
    public BlockSlab getSingleBlock() {
        return ManagerBlocks.slabRoofTileSingle;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return ManagerBlocks.roofTile.getIcon(side, meta & 7);
    }

    @Override
    public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase p, ItemStack i) {
        super.onBlockPlacedBy(w, x, y, z, p, i);
        int meta = w.getBlockMetadata(x, y, z);
        int l = MathHelper.floor_double((double) (p.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        if (l == 0) {
            w.setBlockMetadataWithNotify(x, y, z, meta | 2, 2);
        }
        if (l == 1) {
            w.setBlockMetadataWithNotify(x, y, z, meta | 3, 2);
        }
        if (l == 3) {
            w.setBlockMetadataWithNotify(x, y, z, meta | 1, 2);
        }
    }
}