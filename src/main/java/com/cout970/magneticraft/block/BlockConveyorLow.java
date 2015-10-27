package com.cout970.magneticraft.block;

import buildcraft.api.tools.IToolWrench;
import cofh.api.item.IToolHammer;
import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.api.conveyor.IConveyorBelt;
import com.cout970.magneticraft.api.conveyor.prefab.ItemBox;
import com.cout970.magneticraft.api.tool.IWrench;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.api.util.Orientation;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.tabs.CreativeTabsMg;
import com.cout970.magneticraft.tileentity.TileConveyorBelt;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockConveyorLow extends BlockMg {

    public static IIcon conveyor_low;

    public BlockConveyorLow() {
        super(Material.iron);
        setBlockBounds(0, 0, 0, 1, 5 / 16f, 1);
        setCreativeTab(CreativeTabsMg.IndustrialAgeTab);
    }

    public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer p, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if (p.isSneaking()) return false;
        Item item;
        if ((p.getCurrentEquippedItem() != null) && (Block.getBlockFromItem(item = p.getCurrentEquippedItem().getItem()) != this)) {
            if (MgUtils.isWrench(item)) {
                Orientation or = Orientation.fromMeta(w.getBlockMetadata(x, y, z) + 1);
                w.setBlockMetadataWithNotify(x, y, z, or.toMeta(), 2);
            } else {
                TileEntity t = w.getTileEntity(x, y, z);
                if (t instanceof IConveyorBelt) {
                    if (((IConveyorBelt) t).addItem(MgDirection.SOUTH, 0, new ItemBox(p.getCurrentEquippedItem()), false)) {
                        p.setCurrentItemOrArmor(0, null);
                        ((IConveyorBelt) t).onChange();
                    }
                }
            }
        }
        return true;
    }

    public int getRenderType() {
        return -1;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int side) {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileConveyorBelt();
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return conveyor_low;
    }

    @Override
    public String[] getTextures() {
        return new String[]{"belts/conveyor"};
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister IR) {
        super.registerBlockIcons(IR);
        conveyor_low = icons[0];
    }

    public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase p, ItemStack i) {
        int l = MathHelper.floor_double((double) (p.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        MgDirection dir = null;
        if (l == 0) {
            dir = MgDirection.SOUTH;
        }
        if (l == 1) {
            dir = MgDirection.WEST;
        }
        if (l == 2) {
            dir = MgDirection.NORTH;
        }
        if (l == 3) {
            dir = MgDirection.EAST;
        }
        Orientation or;
        VecInt vec = new VecInt(x, y, z).add(dir.toVecInt());
        VecInt opp = new VecInt(x, y, z).add(dir.toVecInt().getOpposite());

        if (w.getBlock(vec.getX(), vec.getY() + 1, vec.getZ()) == this) {
            or = Orientation.find(1, dir);
        } else if (w.getBlock(opp.getX(), opp.getY() + 1, opp.getZ()) == this) {
            or = Orientation.find(-1, dir);
        } else {
            if (w.getBlock(opp.getX(), opp.getY() - 1, opp.getZ()) == this) {
                TileEntity t = w.getTileEntity(opp.getX(), opp.getY() - 1, opp.getZ());
                if (t instanceof IConveyorBelt) {
                    Orientation fac = ((IConveyorBelt) t).getOrientation();
                    if (fac.getDirection() == dir && fac.getLevel() == 0) {
                        fac = Orientation.find(1, fac.getDirection());
                        w.setBlockMetadataWithNotify(opp.getX(), opp.getY() - 1, opp.getZ(), fac.toMeta(), 2);
                    }
                }
            }
            if (w.getBlock(vec.getX(), vec.getY() - 1, vec.getZ()) == this) {
                TileEntity t = w.getTileEntity(vec.getX(), vec.getY() - 1, vec.getZ());
                if (t instanceof IConveyorBelt) {
                    Orientation fac = ((IConveyorBelt) t).getOrientation();
                    if (fac.getDirection() == dir && fac.getLevel() == 0) {
                        fac = Orientation.find(-1, fac.getDirection());
                        w.setBlockMetadataWithNotify(vec.getX(), vec.getY() - 1, vec.getZ(), fac.toMeta(), 2);
                    }
                }
            }
            or = Orientation.find(0, dir);
        }
        w.setBlockMetadataWithNotify(x, y, z, or.toMeta(), 2);
    }

    @Override
    public String getName() {
        return "conveyor_low";
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public boolean isOpaqueCube() {
        return false;
    }

}
