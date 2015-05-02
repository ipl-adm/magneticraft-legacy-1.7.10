package com.cout970.magneticraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.cout970.magneticraft.api.util.BlockPosition;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.tileentity.TileMgTank;
import com.cout970.magneticraft.util.multiblock.MB_Block;
import com.cout970.magneticraft.util.multiblock.MB_Tile;
import com.cout970.magneticraft.util.multiblock.MB_Watcher;
import com.cout970.magneticraft.util.multiblock.Multiblock;
import com.cout970.magneticraft.util.multiblock.types.MultiblockCrusher;
import com.cout970.magneticraft.util.multiblock.types.MultiblockPolymerizer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMgTank extends BlockMg implements MB_Block{

	public BlockMgTank() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World w, int m) {
		return new TileMgTank();
	}

	@Override
	public String[] getTextures() {
		return new String[]{"mg_tank"};
	}

	@Override
	public String getName() {
		return "mg_tank";
	}
	
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)
    {
		ItemStack current = entityplayer.inventory.getCurrentItem();
		if (current != null) {

			FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(current);

			TileMgTank tank = (TileMgTank) world.getTileEntity(i, j, k);

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

	public boolean isOpaqueCube()
	{
		return false;
	}

	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	public void breakBlock(World w,int x,int y,int z,Block b,int side){
		if(!w.isRemote){
			TileEntity t = w.getTileEntity(x, y, z);
			if(t instanceof MB_Tile){
				if(((MB_Tile) t).getControlPos() != null && ((MB_Tile) t).getMultiblock() != null)
				MB_Watcher.destroyStructure(w, ((MB_Tile) t).getControlPos(), ((MB_Tile) t).getMultiblock(),((MB_Tile) t).getDirection());
			}
		}
		super.breakBlock(w, x, y, z, b, side);
	}
	
	
	@Override
	public void mutates(World w, BlockPosition p, Multiblock c, MgDirection e) {
		if(c instanceof MultiblockPolymerizer){
			w.setBlockMetadataWithNotify(p.getX(), p.getY(), p.getZ(), 2, 2);
		}else
		w.setBlockMetadataWithNotify(p.getX(), p.getY(), p.getZ(), 1, 2);
	}
	
	@Override
	public void destroy(World w, BlockPosition p, Multiblock c, MgDirection e) {
		w.setBlockMetadataWithNotify(p.getX(), p.getY(), p.getZ(), 0, 2);
	}
	
	@SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess w, int x, int y, int z, int side)
    {
		MgDirection d = MgDirection.getDirection(side);
		if(w.getBlockMetadata(x-d.getOffsetX(), y-d.getOffsetY(), z-d.getOffsetZ()) == 2)return false;
        return super.shouldSideBeRendered(w, x, y, z, side);
    }
}
