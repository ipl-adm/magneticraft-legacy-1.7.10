package com.cout970.magneticraft.block.stairs;

import com.cout970.magneticraft.ManagerBlocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;

public class BlockClayTileStairs extends BlockMgStairs {
    public BlockClayTileStairs() {
        super(ManagerBlocks.roofTile, ManagerBlocks.roofTile.getUnlocalizedName() + "Stairs");
        setHardness(1.5F);
        setStepSound(soundTypeStone);
        setHarvestLevel(ManagerBlocks.roofTile.getHarvestTool(0), ManagerBlocks.roofTile.getHarvestLevel(0));
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        int block_meta = 0;
        meta %= 4;
        
        switch (meta) {
            case 0: {
                block_meta = side != 1 ? 3 : 1;
                break;
            }
            case 1: {
                block_meta = side != 1 ? 1 : 3;
                break;
            }
            case 2: {
                block_meta = side != 1 ? 0 : 2;
                break;
            }
            case 3: {
                block_meta = side != 1 ? 2 : 0;
                break;
            }
        }
        
        return ManagerBlocks.roofTile.getIcon(side, block_meta);
    }
}
