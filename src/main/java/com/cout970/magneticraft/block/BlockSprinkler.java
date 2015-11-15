package com.cout970.magneticraft.block;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.tabs.CreativeTabsMg;
import com.cout970.magneticraft.tileentity.TileSprinkler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSprinkler extends BlockMg {

    public BlockSprinkler() {
        super(Material.iron);
        setCreativeTab(CreativeTabsMg.SteamAgeTab);
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
    public String[] getTextures() {
        return new String[]{"void"};
    }

    @Override
    public String getName() {
        return "sprinkler";
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean shouldSideBeRendered(IBlockAccess access, int x, int y, int z, int side) {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileSprinkler();
    }

    @Override
    public boolean canPlaceBlockOnSide(World w, int x, int y, int z, int side) {
        return (side < 2) && super.canPlaceBlockOnSide(w, x, y, z, side); //vertical placement only
    }

    @Override
    public int onBlockPlaced(World w, int x, int y, int z, int side, float hitx, float hity, float hitz, int meta) {
        return MgDirection.getDirection(side).opposite().ordinal();
    }
}
