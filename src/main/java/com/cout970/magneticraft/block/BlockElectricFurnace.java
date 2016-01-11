package com.cout970.magneticraft.block;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.tabs.CreativeTabsMg;
import com.cout970.magneticraft.tileentity.TileElectricFurnace;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockElectricFurnace extends BlockMg {

    public BlockElectricFurnace() {
        super(Material.iron);
        setCreativeTab(CreativeTabsMg.ElectricalAgeTab);
    }

    @Override
    public TileEntity createNewTileEntity(World w, int m) {
        return new TileElectricFurnace();
    }

    public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer p, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if (p.isSneaking()) return false;
        p.openGui(Magneticraft.INSTANCE, 0, w, x, y, z);
        return true;
    }

    @Override
    public String[] getTextures() {
        return new String[]{"electricfurnace", "electricfurnace_head", "electricfurnace_top", "electricfurnace_on"};
    }

    @Override
    public String getName() {
        return "electricfurnace";
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (meta == 0) {
            return side == 3 ? icons[1] : icons[0];
        } else if (meta > 5) {
            return side == 1 ? this.icons[2] : (side == 0 ? this.icons[0] : (side != meta % 6 ? this.icons[0] : this.icons[3]));
        }
        return side == 1 ? this.icons[2] : (side == 0 ? this.icons[0] : (side != meta ? this.icons[0] : this.icons[1]));
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
