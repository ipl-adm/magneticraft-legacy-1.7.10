package com.cout970.magneticraft.items.block;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBlockPumpjack extends ItemBlockMg {

	public ItemBlockPumpjack(Block b) {
		super(b);
	}

	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {

		if (!canBePlaced(world, x, y, z, player)) {
			return false;
		}

		placeBlock(world, x, y, z, player);

		if (world.getBlock(x, y, z) == field_150939_a) {
			field_150939_a.onBlockPlacedBy(world, x, y, z, player, stack);
			field_150939_a.onPostBlockPlaced(world, x, y, z, metadata);
		}

		return true;
	}

	private void placeBlock(World w, int x, int y, int z, EntityPlayer player) {
		MgDirection dir = MgDirection.getDirectionFromRotation(player.rotationYaw);
		
		VecInt pos = new VecInt(x, y, z);
		VecInt check;

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				int meta = j != 0 ? 12 : i == 0 ? getMetaFromDir(dir) : i == 2 ? getMetaFromDir(dir.opposite()) + 8 : getMetaFromDir(dir.opposite()) + 4;
				check = pos.copy().add(dir.toVecInt().multiply(i).add(0, j, 0).add(dir.opposite()));
				w.setBlock(check.getX(), check.getY(), check.getZ(), field_150939_a, meta, 3);
			}
		}
	}

	private int getMetaFromDir(MgDirection dir) {
		if(dir == MgDirection.NORTH)return 0;
		if(dir == MgDirection.SOUTH)return 1;
		if(dir == MgDirection.WEST)return 2;
		if(dir == MgDirection.EAST)return 3;
		return 0;
	}

	private boolean canBePlaced(World w, int x, int y, int z, EntityPlayer player) {
		MgDirection dir = MgDirection.getDirectionFromRotation(player.rotationYaw);
		if(dir == null)return false;
		
		VecInt pos = new VecInt(x, y, z);
		VecInt check;

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				check = pos.copy().add(dir.toVecInt().multiply(i).add(0, j, 0)).add(dir.opposite());
				if (check.getBlock(w) == null || !check.getBlock(w).isReplaceable(w, check.getX(),
						check.getY(), check.getZ())) {
					return false;
				}
			}
		}
		return true;
	}
}
