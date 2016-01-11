package com.cout970.magneticraft.block;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.tabs.CreativeTabsMg;
import com.cout970.magneticraft.tileentity.TileCopperTank;
import com.cout970.magneticraft.util.IBlockWithData;
import com.cout970.magneticraft.util.multiblock.MB_Block;
import com.cout970.magneticraft.util.multiblock.MB_Tile;
import com.cout970.magneticraft.util.multiblock.MB_Watcher;
import com.cout970.magneticraft.util.multiblock.Multiblock;
import com.cout970.magneticraft.util.multiblock.types.MultiblockPolymerizer;
import com.cout970.magneticraft.util.multiblock.types.MultiblockTurbine;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;

public class BlockCopperTank extends BlockMg implements MB_Block {

    public BlockCopperTank() {
        super(Material.iron);
        setCreativeTab(CreativeTabsMg.SteamAgeTab);
        setLightOpacity(0);
    }

    @Override
    public TileEntity createNewTileEntity(World w, int m) {
        return new TileCopperTank();
    }

    @Override
    public String[] getTextures() {
        return new String[]{"mg_tank"};
    }

    @Override
    public String getName() {
        return "mg_tank";
    }

    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {
        ItemStack current = entityplayer.inventory.getCurrentItem();
        if (current != null) {

            FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(current);

            TileCopperTank tank = (TileCopperTank) world.getTileEntity(i, j, k);

            // Handle filled containers
            if (liquid != null) {
                int qty = tank.fillMg(MgDirection.UP, liquid, true);

                if (qty != 0 && !entityplayer.capabilities.isCreativeMode) {
                    entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, consumeItem(current));
                }
                return true;

                // Handle empty containers
            } else {

                FluidStack available = tank.getTankInfoMg(MgDirection.UP)[0].fluid;
                if (available != null) {
                    ItemStack filled = FluidContainerRegistry.fillFluidContainer(available, current);

                    liquid = FluidContainerRegistry.getFluidForFilledItem(filled);

                    if (liquid != null) {
                        if (!entityplayer.capabilities.isCreativeMode) {
                            if (current.stackSize > 1) {
                                if (!entityplayer.inventory.addItemStackToInventory(filled))
                                    return false;
                                else {
                                    entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, consumeItem(current));
                                }
                            } else {
                                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, consumeItem(current));
                                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, filled);
                            }
                        }
                        tank.drainMg(MgDirection.UP, liquid.amount, true);
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static ItemStack consumeItem(ItemStack stack) {
        if (stack.stackSize == 1) {
            if (stack.getItem().hasContainerItem(stack)) {
                return stack.getItem().getContainerItem(stack);
            } else {
                return null;
            }
        } else {
            stack.splitStack(1);

            return stack;
        }
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public void breakBlock(World w, BlockPos pos, IBlockState state) {
        if (!w.isRemote) {
            TileEntity t = w.getTileEntity(pos);
            if (t instanceof MB_Tile) {
                if (((MB_Tile) t).getControlPos() != null && ((MB_Tile) t).getMultiblock() != null)
                    MB_Watcher.destroyStructure(w, ((MB_Tile) t).getControlPos(), ((MB_Tile) t).getMultiblock(), ((MB_Tile) t).getDirection());
            }
        }
        super.breakBlock(w, pos, state);
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

    @Override
    public void mutates(World w, VecInt p, Multiblock c, MgDirection e) {
        if (c instanceof MultiblockPolymerizer || c instanceof MultiblockTurbine) {
            w.setBlockMetadataWithNotify(p.getX(), p.getY(), p.getZ(), 2, 2);
        } else
            w.setBlockMetadataWithNotify(p.getX(), p.getY(), p.getZ(), 1, 2);
    }

    @Override
    public void destroy(World w, VecInt p, Multiblock c, MgDirection e) {
        w.setBlockMetadataWithNotify(p.getX(), p.getY(), p.getZ(), 0, 2);
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess w, int x, int y, int z, int side) {
        MgDirection d = MgDirection.getDirection(side);
        return (w.getBlockMetadata(x - d.getOffsetX(), y - d.getOffsetY(), z - d.getOffsetZ()) != 2)
                && super.shouldSideBeRendered(w, x, y, z, side);
    }
}
