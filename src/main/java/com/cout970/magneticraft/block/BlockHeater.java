package com.cout970.magneticraft.block;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.tabs.CreativeTabsMg;
import com.cout970.magneticraft.tileentity.TileHeater;
import com.cout970.magneticraft.util.multiblock.MB_Block;
import com.cout970.magneticraft.util.multiblock.Multiblock;
import com.cout970.magneticraft.util.multiblock.types.MultiblockPolymerizer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockHeater extends BlockMg implements MB_Block {

    public BlockHeater() {
        super(Material.iron);
        setCreativeTab(CreativeTabsMg.ElectricalAgeTab);
        setLightOpacity(0);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileHeater();
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (side == 0 || side == 1) return icons[2];
        if (meta == 0) return icons[0];
        return icons[1];
    }

    @Override
    public String[] getTextures() {
        return new String[]{"heater_off", "heater_on", "heater_top"};
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public String getName() {
        return "heater";
    }

    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public void mutates(World w, VecInt p, Multiblock c, MgDirection e) {
        if (c instanceof MultiblockPolymerizer) {
            w.setBlockMetadataWithNotify(p.getX(), p.getY(), p.getZ(), 2, 2);
        } else
            w.setBlockMetadataWithNotify(p.getX(), p.getY(), p.getZ(), 1, 2);
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess w, int x, int y, int z, int side) {
        MgDirection d = MgDirection.getDirection(side);
        return (w.getBlockMetadata(x - d.getOffsetX(), y - d.getOffsetY(), z - d.getOffsetZ()) != 2)
                && super.shouldSideBeRendered(w, x, y, z, side);
    }

    @Override
    public void destroy(World w, VecInt p, Multiblock c, MgDirection e) {
        w.setBlockMetadataWithNotify(p.getX(), p.getY(), p.getZ(), 0, 2);
    }
}
