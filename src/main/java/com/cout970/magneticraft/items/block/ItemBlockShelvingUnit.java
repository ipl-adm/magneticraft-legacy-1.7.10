package com.cout970.magneticraft.items.block;

import com.cout970.magneticraft.ManagerBlocks;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.api.util.VecIntUtil;

import com.cout970.magneticraft.tabs.CreativeTabsMg;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemBlockShelvingUnit extends ItemBlockMg {
    public ItemBlockShelvingUnit(Block b) {
        super(ManagerBlocks.shelving_unit);
        setCreativeTab(CreativeTabsMg.MainTab);
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
        int l = MathHelper.floor_double((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        MgDirection facing = MgDirection.getDirectionFromCardinal(l);
        for (int r = -2; r <= 2; r++) {
            for (int b = 0; b < 2; b++) {
                for (int u = 0; u < 3; u++) {
                    VecInt coords = VecIntUtil.getRotatedOffset(facing, r, u, b).add(x, y, z);
                    Block block = coords.getBlock(world);
                    if ((block != null) && !block.isReplaceable(world, x, y, z)) {
                        return false;
                    }
                }
            }
        }
        return super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata);
    }
}
