package com.cout970.magneticraft.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockClayTile extends BlockSimple {
    public static String base = "magneticraft:";
    public IIcon[] icons;

    public BlockClayTile() {
        super("roofTile");
    }

    public String[] getTextures() {
        return new String[]{"roofTile_0", "roofTile_1", "roofTile_2", "roofTile_3"};
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister IR) {
        int pos = 0;
        icons = new IIcon[getTextures().length];
        for (String name : getTextures())
            icons[pos++] = IR.registerIcon(base + name);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (side > 1) {
            return icons[0];
        }
        if (meta > 3) {
            return icons[0];
        }
        return icons[meta];
    }

    @Override
    public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase p, ItemStack i) {
        int l = MathHelper.floor_double((double) (p.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        if (l == 0) {
            w.setBlockMetadataWithNotify(x, y, z, 2, 2);
        }
        if (l == 1) {
            w.setBlockMetadataWithNotify(x, y, z, 3, 2);
        }
        if (l == 2) {
            w.setBlockMetadataWithNotify(x, y, z, 0, 2);
        }
        if (l == 3) {
            w.setBlockMetadataWithNotify(x, y, z, 1, 2);
        }
    }

}
