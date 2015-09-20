package com.cout970.magneticraft.block;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.tabs.CreativeTabsMg;
import com.cout970.magneticraft.tileentity.TileCrafter;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockCrafter extends BlockMg {

    public BlockCrafter() {
        super(Material.iron);
        setCreativeTab(CreativeTabsMg.ElectricalAgeTab);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileCrafter();
    }

    public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer p, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if (p.isSneaking()) return false;
        p.openGui(Magneticraft.INSTANCE, 0, w, x, y, z);
        return true;
    }

    @Override
    public String[] getTextures() {
        return new String[]{"crafter", "crafter_top", "crafter_bottom"};
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return side == 1 ? icons[1] : side == 0 ? icons[2] : icons[0];
    }

    @Override
    public String getName() {
        return "crafter";
    }
}
