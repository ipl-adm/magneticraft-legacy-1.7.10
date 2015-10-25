package com.cout970.magneticraft.block;

import com.cout970.magneticraft.api.access.RecipeCrushingTable;
import com.cout970.magneticraft.api.tool.IHammer;
import com.cout970.magneticraft.tileentity.TileCrushingTable;

import com.cout970.magneticraft.util.InventoryUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCrushingTable extends BlockMg {

    public BlockCrushingTable() {
        super(Material.rock);
        setBlockBounds(0, 0, 0, 1, 0.875f, 1);
    }

    public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer p, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if (p != null) {
            TileEntity t = w.getTileEntity(x, y, z);
            if (!(t instanceof TileCrushingTable)) {
                return true;
            }
            TileCrushingTable tile = (TileCrushingTable) w.getTileEntity(x, y, z);

            ItemStack i = p.getCurrentEquippedItem();
            if (i != null) {
                if (i.getItem() instanceof IHammer) {
                    IHammer hammer = (IHammer) i.getItem();
                    if (tile.canWork()) {
                        if (hammer.canHammer(i, w, x, y, z)) {
                            tile.tick(hammer.getMaxHits(i, w, x, y, z));
                            ItemStack stack = hammer.tick(i, w, x, y, z);
                            p.setCurrentItemOrArmor(0, stack);
                            return true;
                        }
                    } else {
                        if (tile.getInput() == null) {
                            for (int j = 0; j < p.inventory.getSizeInventory(); j++) {
                                ItemStack stack = p.inventory.getStackInSlot(j);
                                if (stack != null && stack.stackSize > 0 && RecipeCrushingTable.getRecipe(stack) != null) {
                                    tile.setInput(p.inventory.decrStackSize(j, 1));

                                    tile.getWorldObj().markBlockForUpdate(x, y, z);
                                    tile.markDirty();

                                    return true;
                                }
                            }
                        } else {
                            if (InventoryUtils.giveToPlayer(tile.getInput(), p.inventory)) {
                                tile.setInput(null);
                            }
                        }


                        tile.getWorldObj().markBlockForUpdate(x, y, z);
                        tile.markDirty();

                        return true;
                    }

                } else if ((tile.getInput() == null) && (i.stackSize > 0)) {
                    ItemStack split = i.splitStack(1);
                    p.setCurrentItemOrArmor(0, (i.stackSize > 0) ? i : null);
                    tile.setInput(split);

                    tile.getWorldObj().markBlockForUpdate(x, y, z);
                    tile.markDirty();

                    return true;
                }
            }
            if (tile.getInput() != null) {
                if (p.inventory.addItemStackToInventory(tile.getInput())) {

                    tile.setInput(null);
                }
            }
            tile.getWorldObj().markBlockForUpdate(x, y, z);
            tile.markDirty();
        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileCrushingTable();
    }

    @Override
    public String[] getTextures() {
        return new String[]{"void"};
    }

    @Override
    public String getName() {
        return "crushing_table";
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public boolean isOpaqueCube() {
        return false;
    }

}
