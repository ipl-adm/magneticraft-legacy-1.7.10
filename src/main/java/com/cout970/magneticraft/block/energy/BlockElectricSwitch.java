package com.cout970.magneticraft.block.energy;

import com.cout970.magneticraft.block.BlockMg;
import com.cout970.magneticraft.tileentity.TileElectricSwitch;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockElectricSwitch extends BlockMg {

    public BlockElectricSwitch() {
        super(Material.iron);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileElectricSwitch();
    }

    @Override
    public String[] getTextures() {
        return new String[]{"electric_switch"};
    }

    @Override
    public String getName() {
        return "electric_switch";
    }

}
