package com.cout970.magneticraft.util.multiblock;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleComponent extends Mg_Component {

    public List<Block> blocks = new ArrayList<>();

    public SimpleComponent(Block b) {
        blocks.add(b);
    }

    public SimpleComponent(Block a, Block b) {
        blocks.add(a);
        blocks.add(b);
    }

    public SimpleComponent(Block... a) {
        Collections.addAll(blocks, a);
    }

    public boolean isCorrect(World w, VecInt p, int x, int y, int z, Multiblock c, MgDirection e, int meta) {
        VecInt te = c.translate(w, p, x, y, z, c, e, meta);
        Block t = w.getBlock(te.getX() + p.getX(), te.getY() + p.getY(), te.getZ() + p.getZ());
        if (!blocks.contains(t)) {
            if (Magneticraft.DEBUG)
                w.setBlock(te.getX() + p.getX(), te.getY() + p.getY(), te.getZ() + p.getZ(), blocks.get(0));
            return false;
        }
        return true;
    }

    public void establish(World w, VecInt p, int x, int y, int z, Multiblock c, MgDirection e, int meta) {
//		Log.debug("Creating: "+p+", x: "+x+", y: "+y+", z: "+z+", MgDir: "+e+", meta: "+meta);
        VecInt te = c.translate(w, p, x, y, z, c, e, meta);
//		Log.debug("Offset: "+te);
        Block t = w.getBlock(te.getX() + p.getX(), te.getY() + p.getY(), te.getZ() + p.getZ());
        if (t instanceof MB_Block) {
            ((MB_Block) t).mutates(w, new VecInt(te.getX() + p.getX(), te.getY() + p.getY(), te.getZ() + p.getZ()), c, e);
        }
        TileEntity tile = w.getTileEntity(te.getX() + p.getX(), te.getY() + p.getY(), te.getZ() + p.getZ());
        if (tile instanceof MB_Tile) {
            ((MB_Tile) tile).setControlPos(p);
            ((MB_Tile) tile).setDirection(e);
            ((MB_Tile) tile).setMultiblock(c);
            ((MB_Tile) tile).onActivate(w, p, c, e);
        }
    }

    public void destroy(World w, VecInt p, int x, int y, int z, Multiblock c, MgDirection e, int meta) {
//		Log.debug("Destroying: "+p+", x: "+x+", y: "+y+", z: "+z+", MgDir: "+e+", meta: "+meta);
        VecInt te = c.translate(w, p, x, y, z, c, e, meta);
//		Log.debug("Offset: "+te);
        Block t = w.getBlock(te.getX() + p.getX(), te.getY() + p.getY(), te.getZ() + p.getZ());
        if (t instanceof MB_Block) {
            ((MB_Block) t).destroy(w, new VecInt(te.getX() + p.getX(), te.getY() + p.getY(), te.getZ() + p.getZ()), c, e);
        }
        TileEntity tile = w.getTileEntity(te.getX() + p.getX(), te.getY() + p.getY(), te.getZ() + p.getZ());
        if (tile instanceof MB_Tile) {
            ((MB_Tile) tile).onDestroy(w, p, c, e);
            ((MB_Tile) tile).setControlPos(null);
            ((MB_Tile) tile).setMultiblock(null);
            ((MB_Tile) tile).setDirection(null);
        }
    }

    public String getErrorMessage(World w, VecInt p, int x, int y, int z, Multiblock c, MgDirection e, int meta) {
        VecInt k = c.translate(w, p, x, y, z, c, e, meta).add(p);
        return "Error in " + k.getX() + " " + k.getY() + " " + k.getZ() + " with the block: " + blocks.get(0).getLocalizedName();
    }

}
