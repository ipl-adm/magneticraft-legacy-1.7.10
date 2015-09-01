package com.cout970.magneticraft.block;

import com.cout970.magneticraft.tabs.CreativeTabsMg;
import com.cout970.magneticraft.tileentity.TileHandCrankGenerator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockHandCrankGenerator extends BlockMg {

    public BlockHandCrankGenerator() {
        super(Material.wood);
        setCreativeTab(CreativeTabsMg.MedievalAgeTab);
    }

    public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer p, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        TileEntity t = w.getTileEntity(x, y, z);
        if (t instanceof TileHandCrankGenerator) {
            ((TileHandCrankGenerator) t).tickCounter = 20;
            return true;
        }
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileHandCrankGenerator();
    }

    @Override
    public String[] getTextures() {
        return new String[]{"void"};
    }

    @Override
    public String getName() {
        return "hand_crank_generator";
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess w, int x, int y, int z, int side) {
        return false;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase p, ItemStack i) {
        w.setBlockMetadataWithNotify(x, y, z, Facing.oppositeSide[determineOrientation(w, x, y, z, p)], 2);
    }

    public static int determineOrientation(World w, int x, int y, int z, EntityLivingBase p) {
        if (MathHelper.abs((float) p.posX - (float) x) < 2.0F && MathHelper.abs((float) p.posZ - (float) z) < 2.0F) {
            double d0 = p.posY + 1.82D - (double) p.yOffset;

            if (d0 - (double) y > 2.0D) {
                return 1;
            }

            if ((double) y - d0 > 0.0D) {
                return 0;
            }
        }
        int l = MathHelper.floor_double((double) (p.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        return l == 0 ? 2 : (l == 1 ? 5 : (l == 2 ? 3 : (l == 3 ? 4 : 0)));
    }
}
