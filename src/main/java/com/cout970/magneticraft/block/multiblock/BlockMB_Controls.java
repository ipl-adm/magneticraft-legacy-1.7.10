package com.cout970.magneticraft.block.multiblock;

import codechicken.lib.raytracer.IndexedCuboid6;
import codechicken.lib.raytracer.RayTracer;
import codechicken.lib.vec.BlockCoord;
import codechicken.lib.vec.Vector3;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.block.BlockMg;
import com.cout970.magneticraft.tileentity.multiblock.TileMB_Controls;
import com.cout970.magneticraft.util.multiblock.MB_Block;
import com.cout970.magneticraft.util.multiblock.MB_Tile;
import com.cout970.magneticraft.util.multiblock.MB_Watcher;
import com.cout970.magneticraft.util.multiblock.Multiblock;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockMB_Controls extends BlockMg implements MB_Block {

    public BlockMB_Controls() {
        super(Material.iron);
        setLightOpacity(0);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileMB_Controls();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitx, float hity, float hitz) {
        MovingObjectPosition hit = RayTracer.retraceBlock(world, player, x, y, z);
        if (hit == null) return true;
        TileEntity t = world.getTileEntity(x, y, z);
        if (t instanceof TileMB_Controls) {
            ((TileMB_Controls) t).onClick(hit);
        }
        return true;
    }

    @Override
    public MovingObjectPosition collisionRayTrace(World w, int x, int y, int z, Vec3 start, Vec3 end) {
        TileEntity t = w.getTileEntity(x, y, z);
        if (!(t instanceof TileMB_Controls)) return null;
        List<IndexedCuboid6> list = ((TileMB_Controls) t).getBoundingBoxes();
        return RayTracer.instance().rayTraceCuboids(new Vector3(start), new Vector3(end), list, new BlockCoord(x, y, z), this);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_) {
        setBlockBounds(0, 0, 0, 1, 1, 1);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_) {
        setBlockBoundsBasedOnState(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
        return super.getCollisionBoundingBoxFromPool(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
    }

    @Override
    public void breakBlock(World w, int x, int y, int z, Block b, int side) {
        if (!w.isRemote) {
            TileEntity t = w.getTileEntity(x, y, z);
            if (t instanceof MB_Tile) {
                if (((MB_Tile) t).getControlPos() != null && ((MB_Tile) t).getMultiblock() != null)
                    MB_Watcher.destroyStructure(w, ((MB_Tile) t).getControlPos(), ((MB_Tile) t).getMultiblock(), ((MB_Tile) t).getDirection());
            }
        }
        super.breakBlock(w, x, y, z, b, side);
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public String[] getTextures() {
        return new String[]{"mb_controls"};
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public String getName() {
        return "mb_controls";
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return icons[0];
    }

    @Override
    public void mutates(World w, VecInt p, Multiblock c, MgDirection e) {
        w.setBlockMetadataWithNotify(p.getX(), p.getY(), p.getZ(), 2, 2);
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