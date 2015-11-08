package com.cout970.magneticraft.block;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.api.util.VecIntUtil;
import com.cout970.magneticraft.tileentity.shelf.TileShelf;
import com.cout970.magneticraft.tileentity.shelf.TileShelfFiller;
import com.cout970.magneticraft.tileentity.shelf.TileShelvingUnit;
import com.cout970.magneticraft.util.InventoryUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockShelvingUnit extends BlockMg {

    public BlockShelvingUnit() {
        super(Material.iron);
    }


    @Override
    public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer p, int side, float hitX, float hitY, float hitZ) {
        TileEntity t = w.getTileEntity(x, y, z);
        if (!(t instanceof TileShelf)) {
            return true;
        }
        TileShelvingUnit shelf = ((TileShelf) t).getMainTile();

        if (shelf == null) {
            return super.onBlockActivated(w, x, y, z, p, side, hitX, hitY, hitZ);
        }

        if (p.isSneaking()) {
            if (p.getCurrentEquippedItem() == null) {
                if (shelf.removeCrate()) {
                    InventoryUtils.giveToPlayer(new ItemStack(Blocks.chest), p.inventory);

                    w.markBlockForUpdate(shelf.xCoord, shelf.yCoord, shelf.zCoord);
                    shelf.markDirty();
                    return true;
                } else if (!w.isRemote) {
                    if (shelf.getCrateCount() > 0) {
                        p.addChatComponentMessage(new ChatComponentText("Crate is not empty and cannot be removed!"));
                    } else {
                        p.addChatComponentMessage(new ChatComponentText("There are no crates on this Shelving Unit!"));
                    }
                }
            }
        } else {
            if ((p.getCurrentEquippedItem() != null) && (p.getCurrentEquippedItem().getItem() == Item.getItemFromBlock(Blocks.chest))) {
                if (shelf.addCrate()) {
                    if (!p.capabilities.isCreativeMode) {
                        p.inventory.decrStackSize(p.inventory.currentItem, 1);
                    }
                } else {
                    p.openGui(Magneticraft.INSTANCE, 0, w, shelf.xCoord, shelf.yCoord, shelf.zCoord);
                }

                w.markBlockForUpdate(shelf.xCoord, shelf.yCoord, shelf.zCoord);
                shelf.markDirty();
                return true;
            }
            if (MgUtils.isWrench(p.getCurrentEquippedItem())) {
                shelf.setPlacing(!shelf.isPlacing(), p);
            } else {
                p.openGui(Magneticraft.INSTANCE, 0, w, shelf.xCoord, shelf.yCoord, shelf.zCoord);
            }
            return true;
        }

        return super.onBlockActivated(w, x, y, z, p, side, hitX, hitY, hitZ);
    }

    @Override
    public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase p, ItemStack i) {
        int l = MathHelper.floor_double((double) (p.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        w.setBlockMetadataWithNotify(x, y, z, MgDirection.getDirectionFromCardinal(l).ordinal(), 2);

        for (int r = -2; r <= 2; r++) {
            for (int b = 0; b < 2; b++) {
                for (int u = 0; u < 3; u++) {
                    VecInt offset = VecIntUtil.getRotatedOffset(MgDirection.getDirection(w.getBlockMetadata(x, y, z)), r, u, b);
                    int xx, yy, zz;
                    if (!offset.equals(VecInt.NULL_VECTOR)) {
                        w.setBlock(xx = x + offset.getX(), yy = y + offset.getY(), zz = z + offset.getZ(), this, 9, 2);
                        TileShelfFiller bt = (TileShelfFiller) w.getTileEntity(xx, yy, zz);
                        bt.setOffset(offset);
                    }
                }
            }
        }
    }

    @Override
    public void breakBlock(World w, int x, int y, int z, Block bl, int meta) {
        if (w.isRemote) {
            super.breakBlock(w, x, y, z, bl, meta);
            return;
        }
        if (meta < 6) {
            TileShelvingUnit shelf = (TileShelvingUnit) w.getTileEntity(x, y, z);
            super.breakBlock(w, x, y, z, bl, meta);
            int height = (shelf.getCrateCount() > (TileShelvingUnit.MAX_CRATES - TileShelvingUnit.SHELF_CRATES)) ? 4 : 3;
            for (int r = -2; r <= 2; r++) {
                for (int b = 0; b < 2; b++) {
                    for (int u = 0; u < height; u++) {
                        VecInt offset = VecIntUtil.getRotatedOffset(MgDirection.getDirection(meta), r, u, b);
                        if (!offset.equals(VecInt.NULL_VECTOR)) {
                            w.setBlockToAir(x + offset.getX(), y + offset.getY(), z + offset.getZ());
                        }
                    }
                }
            }
        } else {
            TileShelfFiller bt = (TileShelfFiller) w.getTileEntity(x, y, z);
            if (!bt.silentRemoval) {
                VecInt offset = bt.getOffset();
                w.setBlockToAir(x - offset.getX(), y - offset.getY(), z - offset.getZ());
            }
            super.breakBlock(w, x, y, z, bl, meta);
        }
    }

    @Override
    public String[] getTextures() {
        return new String[]{"void"};
    }

    @Override
    public String getName() {
        return "ShelvingUnit";

    }

    public boolean shouldSideBeRendered(IBlockAccess w, int x, int y, int z, int side) {
        return false;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess access, int x, int y, int z) {
        if (access.getBlockMetadata(x, y, z) == 10) {
            setBlockBounds(0F, 0F, 0F, 1F, 0.625F, 1F);
        } else {
            setBlockBounds(0F, 0F, 0F, 1F, 1F, 1F);
        }
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        if (i > 6) {
            return new TileShelfFiller();
        }
        return new TileShelvingUnit();
    }
}
