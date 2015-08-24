package com.cout970.magneticraft.util.multiblock.types;

import com.cout970.magneticraft.ManagerBlocks;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.util.multiblock.MB_Register;
import com.cout970.magneticraft.util.multiblock.Multiblock;
import com.cout970.magneticraft.util.multiblock.SimpleComponent;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class MultiblockCrusher extends Multiblock {

    @Override
    public int getID() {
        return MB_Register.ID_CRUSHER;
    }

    @Override
    public void init() {
        SimpleComponent a = new SimpleComponent(Blocks.air) {
            public boolean isCorrect(World w, VecInt p, int x, int y, int z, Multiblock c, MgDirection e, int meta) {
                return true;
            }
        };
        SimpleComponent i = new SimpleComponent(ManagerBlocks.multi_io);
        SimpleComponent e = new SimpleComponent(ManagerBlocks.multi_energy_low);
        SimpleComponent b = new SimpleComponent(ManagerBlocks.chassis);
        SimpleComponent c = new SimpleComponent(ManagerBlocks.crusher);

        SimpleComponent[][][] m =
                {// 	{{z2,z1,z0}x2,{z2,z1,z0}x1,{z2,z1,z0}x0}y0
                        {{a, a, b, b, b}, {b, b, b, b, b}, {b, b, b, b, b}, {b, b, b, b, b}},
                        {{a, a, b, c, b}, {b, b, b, a, b}, {i, a, a, a, i}, {b, b, b, e, b}},
                        {{a, a, b, b, b}, {b, b, b, b, b}, {b, b, b, b, b}, {b, b, b, b, b}}
                };

        VecInt p = new VecInt(-3, -1, 0);
        x = m.length;
        y = m[0].length;
        z = m[0][0].length;
        matrix = m;
        tran = p;
    }

    public VecInt translate(World w, VecInt p, int x, int y, int z, Multiblock c, MgDirection e, int meta) {//yzx
        if (meta % 8 < 4) {
            if (e == MgDirection.SOUTH) return new VecInt(-z, x, -y).add(-tran.getX(), tran.getY(), tran.getZ());
            if (e == MgDirection.WEST) return new VecInt(y, x, -z).add(tran.getZ(), tran.getY(), -tran.getX());
            if (e == MgDirection.EAST) return new VecInt(-y, x, z).add(tran.getZ(), tran.getY(), tran.getX());
            return new VecInt(z, x, y).add(tran.getX(), tran.getY(), tran.getZ());
        } else {
            if (e == MgDirection.NORTH) return new VecInt(-z, x, y).add(-tran.getX(), tran.getY(), tran.getZ());
            if (e == MgDirection.EAST) return new VecInt(-y, x, -z).add(tran.getZ(), tran.getY(), -tran.getX());
            if (e == MgDirection.WEST) return new VecInt(y, x, z).add(tran.getZ(), tran.getY(), tran.getX());
            return new VecInt(z, x, -y).add(tran.getX(), tran.getY(), tran.getZ());
        }
    }
}
