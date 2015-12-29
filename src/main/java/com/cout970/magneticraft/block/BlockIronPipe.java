package com.cout970.magneticraft.block;

import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Vector3;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.tileentity.TileIronPipe;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Created by Cypher121 on 12/27/2015.
 */
public class BlockIronPipe extends BlockMg {

    public BlockIronPipe() {
        super(Material.iron);
        setBlockBounds(0.3f, 0.3f, 0.3f, 0.7f, 0.7f, 0.7f);
    }

    public static boolean isHit(Cuboid6 c, Vector3 v) {
        if (c == null || v == null) return false;
        if ((float) c.max.y == (float) v.y || (float) c.min.y == (float) v.y) {
            if ((c.min.x <= v.x) && (c.max.x >= v.x)) {
                if ((c.min.z <= v.z) && (c.max.z >= v.z)) {
                    return true;
                }
            }
        }
        if ((float) c.max.x == (float) v.x || (float) c.min.x == (float) v.x) {
            if ((c.min.y <= v.y) && (c.max.y >= v.y)) {
                if ((c.min.z <= v.z) && (c.max.z >= v.z)) {
                    return true;
                }
            }
        }
        if ((float) c.max.z == (float) v.z || (float) c.min.z == (float) v.z) {
            if ((c.min.x <= v.x) && (c.max.x >= v.x)) {
                if ((c.min.y <= v.y) && (c.max.y >= v.y)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String[] getTextures() {
        return new String[]{"void"};
    }

    @Override
    public String getName() {
        return "iron_pipe";
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean getUseNeighborBrightness() {
        return true;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileIronPipe();
    }

    @Override
    public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer p, int side, float hitx, float hity, float hitz) {

        setBlockBounds(0.3f, 0.3f, 0.3f, 0.7f, 0.7f, 0.7f);
        if (MgUtils.isWrench(p.getCurrentEquippedItem())) {
            Vector3 v = new Vector3(hitx, hity, hitz);
            TileIronPipe tile = (TileIronPipe) w.getTileEntity(x, y, z);
            if (tile == null) return false;
            MgDirection d = MgDirection.values()[side];
            if (tile.connections[d.ordinal()]) {
                tile.side[d.ordinal()] = TileIronPipe.ConnectionMode.getNext(tile.side[d.ordinal()]);
                tile.locked[d.ordinal()] = true;
                if (!w.isRemote) tile.sendUpdateToClient();
            }
        }
        return false;
    }
}
