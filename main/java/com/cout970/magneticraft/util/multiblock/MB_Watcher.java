package com.cout970.magneticraft.util.multiblock;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import com.cout970.magneticraft.api.util.BlockPosition;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;

public class MB_Watcher {

	public static void watchStructure(World w, BlockPosition p, Multiblock c, MgDirection e) {
//		Log.debug("Creating "+e+" "+c.getID());
		int[] q = c.getDimensions(e);
		for (int y = 0; y < q[1]; y++) {
			for (int z = 0; z < q[2]; z++) {
				for (int x = 0; x < q[0]; x++) {
					MutableComponent mut = c.matrix[x][y][z];
					if (!mut.isCorrect(w, p, x,y,z, c, e)) {
						VecInt k = c.translate(x, y, z, e).add(p);
						String s = "Error in " + k.getX() + " " + k.getY() + " " + k.getZ() +" "+ mut.getErrorMesage();
						Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(s));
						return;
					}
				}
			}
		}
//		Log.debug("Exito");
		establishStructure(w, p, c, e);
		String s = "Successful activation";
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(s));
	}

	public static void establishStructure(World w, BlockPosition p, Multiblock c,
			MgDirection e) {
		int[] q = c.getDimensions(e);
		for (int y = 0; y < q[1]; y++) {
			for (int z = 0; z < q[2]; z++) {
				for (int x = 0; x < q[0]; x++) {
					MutableComponent mut = c.matrix[x][y][z];
					mut.establish(w, p, x, y, z, c, e);
				}
			}
		}
	}

	public static void destroyStructure(World w, BlockPosition p, Multiblock c,MgDirection e) {
		// Log.debug("Breaking "+e);
		int[] q = c.getDimensions(e);
		for (int y = 0; y < q[1]; y++) {
			for (int z = 0; z < q[2]; z++) {
				for (int x = 0; x < q[0]; x++) {
					MutableComponent mut = c.matrix[x][y][z];
					mut.destroy(w, p, x, y, z, c, e);
				}
			}
		}
		// Log.debug("Breaking exito: "+e+" "+p);
	}
}
