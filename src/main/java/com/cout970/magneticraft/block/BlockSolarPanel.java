package com.cout970.magneticraft.block;

import com.cout970.magneticraft.tabs.CreativeTabsMg;
import com.cout970.magneticraft.tileentity.TileSolarPanel;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockSolarPanel extends BlockMg {

    public BlockSolarPanel() {
        super(Material.iron);
        setBlockBounds(0, 0, 0, 1f, 4 / 16f, 1f);
        setCreativeTab(CreativeTabsMg.ElectricalAgeTab);
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World w, int m) {
        return new TileSolarPanel();
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (side == 0) return icons[2];
        if (side == 1) return icons[0];
        return icons[1];
    }

    @Override
    public String[] getTextures() {
        return new String[]{"solarpanel", "solarpanelside", "solarpanelbottom"};
    }

    @Override
    public String getName() {
        return "solarpanel";
    }

}
