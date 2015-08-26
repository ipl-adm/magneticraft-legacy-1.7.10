package com.cout970.magneticraft.block;

import com.cout970.magneticraft.tabs.CreativeTabsMg;
import com.cout970.magneticraft.tileentity.TileReactorWall;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockReactorWall extends BlockMg {

    public BlockReactorWall() {
        super(Material.iron);
        setCreativeTab(CreativeTabsMg.InformationAgeTab);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileReactorWall();
    }

    @Override
    public String[] getTextures() {
        return new String[]{"reactor_wall"};
    }

    @Override
    public String getName() {
        return "reactor_wall";
    }
}
