package com.cout970.magneticraft.block;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.tabs.CreativeTabsMg;
import com.cout970.magneticraft.tileentity.TileWindTurbine;
import com.cout970.magneticraft.tileentity.TileWindTurbineGap;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockWindTurbine extends BlockMg {

    public BlockWindTurbine() {
        super(Material.iron);
        setCreativeTab(CreativeTabsMg.ElectricalAgeTab);
    }

    public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer p, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if (p.isSneaking()) return false;
        p.openGui(Magneticraft.INSTANCE, 0, w, x, y, z);
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int m) {
        if (m <= 5) return new TileWindTurbine();
        return new TileWindTurbineGap();
    }

    @Override
    public String[] getTextures() {
        return new String[]{"windturbine", "windturbine_head"};
    }

    @Override
    public String getName() {
        return "windturbine";
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess w, int x, int y, int z, int s) {
        MgDirection d = MgDirection.getDirection(s);
        return w.getBlockMetadata(x - d.getOffsetX(), y - d.getOffsetY(), z - d.getOffsetZ()) != 15;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase p, ItemStack i) {
        int l = MathHelper.floor_double((double) (p.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        if (l == 0) {
            w.setBlockMetadataWithNotify(x, y, z, 3, 2);
        } else if (l == 1) {
            w.setBlockMetadataWithNotify(x, y, z, 4, 2);
        } else if (l == 2) {
            w.setBlockMetadataWithNotify(x, y, z, 2, 2);
        } else if (l == 3) {
            w.setBlockMetadataWithNotify(x, y, z, 5, 2);
        }
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (meta == 0) {
            return side == 3 ? icons[1] : icons[0];
        }
        return (side == 0 ? this.icons[0] : (side != meta ? this.icons[0] : this.icons[1]));
    }

}
