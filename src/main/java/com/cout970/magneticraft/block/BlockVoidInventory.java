package com.cout970.magneticraft.block;

import com.cout970.magneticraft.tileentity.TileVoidInventory;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockVoidInventory extends BlockMg {

    public BlockVoidInventory() {
        super(Material.iron);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileVoidInventory();
    }

    @Override
    public String[] getTextures() {
        return new String[]{"void_inventory"};
    }

    @Override
    public String getName() {
        return "void_inventory";
    }

}
