package com.cout970.magneticraft.util.multiblock;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class MB_Watcher {

    public static void watchStructure(World w, BlockPos p, Multiblock c, EnumFacing e, EntityPlayer player) {
        if (c == null) return;
        int[] q = c.getDimensions(e);
        int meta = w.getBlockMetadata(p.getX(), p.getY(), p.getZ());
        for (int y = 0; y < q[1]; y++) {
            for (int z = 0; z < q[2]; z++) {
                for (int x = 0; x < q[0]; x++) {
                    Mg_Component mut = c.matrix[x][y][z];
                    if (!mut.isCorrect(w, p, x, y, z, c, e, meta)) {
                        String s = mut.getErrorMessage(w, p, x, y, z, c, e, meta);
                        player.addChatMessage(new ChatComponentText(s));
                        return;
                    }
                }
            }
        }

        establishStructure(w, p, c, e);
        String s = "Successful activation";
        player.addChatMessage(new ChatComponentText(s));
    }

    public static void establishStructure(World w, BlockPos p, Multiblock c, EnumFacing e) {
        int[] q = c.getDimensions(e);
        int meta = w.getBlockMetadata(p.getX(), p.getY(), p.getZ());
        for (int y = 0; y < q[1]; y++) {
            for (int z = 0; z < q[2]; z++) {
                for (int x = 0; x < q[0]; x++) {
                    Mg_Component mut = c.matrix[x][y][z];
                    mut.establish(w, p, x, y, z, c, e, meta);
                }
            }
        }
    }

    public static void destroyStructure(World w, BlockPos p, Multiblock c, EnumFacing e) {
        // Log.debug("Breaking "+e);
        if (c == null) return;
        int[] q = c.getDimensions(e);
        int meta = w.getBlockMetadata(p.getX(), p.getY(), p.getZ());
        for (int y = 0; y < q[1]; y++) {
            for (int z = 0; z < q[2]; z++) {
                for (int x = 0; x < q[0]; x++) {
                    Mg_Component mut = c.matrix[x][y][z];
                    mut.destroy(w, p, x, y, z, c, e, meta);
                }
            }
        }
        // Log.debug("Breaking Successful: "+e+" "+p);
    }
}
