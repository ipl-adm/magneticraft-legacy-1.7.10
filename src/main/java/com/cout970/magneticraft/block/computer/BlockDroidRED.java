package com.cout970.magneticraft.block.computer;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.api.util.Orientation;
import com.cout970.magneticraft.block.BlockMg;
import com.cout970.magneticraft.handlers.GuiHandler;
import com.cout970.magneticraft.tabs.CreativeTabsMg;
import com.cout970.magneticraft.tileentity.TileDroidRED;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDroidRED extends BlockMg {

    public BlockDroidRED() {
        super(Material.iron);
        setCreativeTab(CreativeTabsMg.InformationAgeTab);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int m) {
        if (m == 15) return null;
        return new TileDroidRED();
    }

    @Override
    public boolean onBlockActivated(World w, BlockPos pos, IBlockState state, EntityPlayer p, EnumFacing facing, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if (p.isSneaking()) return false;
        GuiHandler.open(p, w, pos);
        return true;
    }

    @Override
    public String[] getTextures() {
        return new String[]{"void"};
    }

    @Override
    public String getName() {
        return "droid_red";
    }

    public void onBlockPlacedBy(World w, BlockPos pos, IBlockState state, EntityLivingBase p, ItemStack i) {
        rotate(w, pos, state, p);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public AxisAlignedBB getSelectedBoundingBox(World w, BlockPos pos) {
        double desp = 0.0625 * 4;
        Orientation o = Orientation.fromMeta(w.getBlockState(pos).getBlock().getMetaFromState(w.getBlockState(pos)));
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        if (o.getLevel() == 1 || o.getLevel() == -1)
            return AxisAlignedBB.fromBounds(x + desp, y, z + desp, x + 1 - desp, y + 1, z + 1 - desp);
        switch (o) {
            case NORTH:
            case SOUTH:
                return AxisAlignedBB.fromBounds(x + desp, y + desp, z, x + 1 - desp, y + 1 - +desp, z + 1);
            case WEST:
            case EAST:
                return AxisAlignedBB.fromBounds(x, y + desp, z + desp, x + 1, y + 1 - +desp, z + 1 - desp);
            default:
                break;
        }
        return AxisAlignedBB.fromBounds((double) x + this.minX, (double) y + this.minY, (double) z + this.minZ, (double) x + this.maxX, (double) y + this.maxY, (double) z + this.maxZ);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(World w, BlockPos pos, IBlockState state) {
        return getSelectedBoundingBox(w, pos);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, BlockPos pos, EnumFacing facing) {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }
}
