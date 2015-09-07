package com.cout970.magneticraft.items.block;

import com.cout970.magneticraft.ManagerBlocks;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.block.slabs.BlockMgSlab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBlockMgSlab extends ItemSlab {
	BlockSlab singleBlock;
	BlockSlab doubleBlock;

	public ItemBlockMgSlab(Block par1) {
		super(par1, ((BlockMgSlab) par1).getSingleBlock(), ((BlockMgSlab) par1).getFullBlock(), false);
		singleBlock = ((BlockMgSlab) par1).getSingleBlock();
		doubleBlock = ((BlockMgSlab) par1).getFullBlock();
	}

	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack) {
		return field_150939_a.getUnlocalizedName();
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float clickX, float clickY, float clickZ) {

		if (ManagerBlocks.slabRoofTileSingle == singleBlock) {
			VecInt vec = new VecInt(x, y, z);
			Block block = vec.getBlock(world);
			int metadata = vec.getBlockMetadata(world);
			boolean flag = (metadata & 8) != 0;
			int orientation = metadata & 7;

			if (stack.stackSize == 0) {
				return false;
			} else if (!player.canPlayerEdit(x, y, z, side, stack)) {
				return false;
			} else {

				if ((side == 1 && !flag || side == 0 && flag) && block == singleBlock) {
					if (world.checkNoEntityCollision(
							doubleBlock.getCollisionBoundingBoxFromPool(world, x, y, z)) && world.setBlock(x,
									y, z, doubleBlock, orientation, 3)) {
						world.playSoundEffect((double) ((float) x + 0.5F), (double) ((float) y + 0.5F),
								(double) ((float) z + 0.5F), doubleBlock.stepSound.func_150496_b(),
								(doubleBlock.stepSound.getVolume() + 1.0F) / 2.0F,
								doubleBlock.stepSound.getPitch() * 0.8F);
						--stack.stackSize;
					}

					return true;
				} else {
					return this.placeDoubleSlab(stack, world, x, y, z, side) || placeNewBlock(
                            stack, player, world, x, y, z, side, clickX, clickY, clickZ);
				}
			}
		}
		return super.onItemUse(stack, player, world, x, y, z, side, clickX, clickY, clickZ);
	}

	private boolean placeDoubleSlab(ItemStack stack, World world, int x, int y, int z, int side) {
		if (side == 0) {
			--y;
		}

		if (side == 1) {
			++y;
		}

		if (side == 2) {
			--z;
		}

		if (side == 3) {
			++z;
		}

		if (side == 4) {
			--x;
		}

		if (side == 5) {
			++x;
		}

		Block block = world.getBlock(x, y, z);
		int i1 = world.getBlockMetadata(x, y, z);
		int j1 = i1 & 7;

		if (block == singleBlock) {
			if (world.checkNoEntityCollision(
					doubleBlock.getCollisionBoundingBoxFromPool(world, x, y, z)) && world.setBlock(x, y, z,
							doubleBlock, j1, 3)) {
				world.playSoundEffect((double) ((float) x + 0.5F), (double) ((float) y + 0.5F),
						(double) ((float) z + 0.5F), doubleBlock.stepSound.func_150496_b(),
						(doubleBlock.stepSound.getVolume() + 1.0F) / 2.0F,
						doubleBlock.stepSound.getPitch() * 0.8F);
				--stack.stackSize;
			}

			return true;
		} else {
			return false;
		}
	}

	public boolean placeNewBlock(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float clickX, float clickY, float clickZ) {
		Block block = world.getBlock(x, y, z);

		if (block == Blocks.snow_layer && (world.getBlockMetadata(x, y, z) & 7) < 1) {
			side = 1;
		} else if (block != Blocks.vine && block != Blocks.tallgrass && block != Blocks.deadbush && !block
				.isReplaceable(world, x, y, z)) {
			if (side == 0) {
				--y;
			}

			if (side == 1) {
				++y;
			}

			if (side == 2) {
				--z;
			}

			if (side == 3) {
				++z;
			}

			if (side == 4) {
				--x;
			}

			if (side == 5) {
				++x;
			}
		}

		if (stack.stackSize == 0) {
			return false;
		} else if (!player.canPlayerEdit(x, y, z, side, stack)) {
			return false;
		} else if (y == 255 && this.field_150939_a.getMaterial().isSolid()) {
			return false;
		} else if (world.canPlaceEntityOnSide(this.field_150939_a, x, y, z, false, side, player, stack)) {
			int i1 = this.getMetadata(stack.getItemDamage());
			int j1 = this.field_150939_a.onBlockPlaced(world, x, y, z, side, clickX, clickY, clickZ, i1);

			if (placeBlockAt(stack, player, world, x, y, z, side, clickX, clickY, clickZ, j1)) {
				world.playSoundEffect((double) ((float) x + 0.5F), (double) ((float) y + 0.5F),
						(double) ((float) z + 0.5F), this.field_150939_a.stepSound.func_150496_b(),
						(this.field_150939_a.stepSound.getVolume() + 1.0F) / 2.0F,
						this.field_150939_a.stepSound.getPitch() * 0.8F);
				--stack.stackSize;
			}

			return true;
		} else {
			return false;
		}
	}

	/**
	 * this function checks if the player can place a block here, more or less
	 */
	@SideOnly(Side.CLIENT)
	public boolean func_150936_a(World world, int x, int y, int z, int side, EntityPlayer player, ItemStack stack) {
		int i = x;
		int j = y;
		int k = z;
		Block block = world.getBlock(x, y, z);
		int metadata = world.getBlockMetadata(x, y, z);
		boolean flag = (metadata & 8) != 0;

		if ((side == 1 && !flag || side == 0 && flag) && block == this.singleBlock) {
			return true;
		} else {
			if (side == 0) {
				--y;
			}

			if (side == 1) {
				++y;
			}

			if (side == 2) {
				--z;
			}

			if (side == 3) {
				++z;
			}

			if (side == 4) {
				--x;
			}

			if (side == 5) {
				++x;
			}

			Block block1 = world.getBlock(x, y, z);
			return block1 == this.singleBlock || super.func_150936_a(world, i, j, k, side, player,
                    stack);
		}
	}
}
