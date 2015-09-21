package com.cout970.magneticraft.block;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.tabs.CreativeTabsMg;
import com.cout970.magneticraft.tileentity.TileGeothermalPump;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockGeothermalPump extends BlockMg {

    public BlockGeothermalPump() {
        super(Material.iron);
        setCreativeTab(CreativeTabsMg.ElectricalAgeTab);
    }

    public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer p, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if (p.isSneaking()) return false;
        p.openGui(Magneticraft.INSTANCE, 0, w, x, y, z);
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileGeothermalPump();
    }

    @Override
    public String[] getTextures() {
        return new String[]{"geothermal_pump", "geothermal_pump_top", "geothermal_pump_front_on", "geothermal_pump_front_off"};
    }

    @Override
    public String getName() {
        return "geothermal_pump";
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (side == 0) return icons[0];
        if (side == 1) return icons[1];
        if (meta == 0) return side == 3 ? icons[3] : icons[0];
        if (meta % 6 == side) return meta > 5 ? icons[2] : icons[3];
        return icons[0];
    }

    public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase p, ItemStack i) {
        int l = MathHelper.floor_double((double) (p.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        if (l == 0) {
            w.setBlockMetadataWithNotify(x, y, z, 2, 2);
        }
        if (l == 1) {
            w.setBlockMetadataWithNotify(x, y, z, 5, 2);
        }
        if (l == 2) {
            w.setBlockMetadataWithNotify(x, y, z, 3, 2);
        }
        if (l == 3) {
            w.setBlockMetadataWithNotify(x, y, z, 4, 2);
        }
    }
}
