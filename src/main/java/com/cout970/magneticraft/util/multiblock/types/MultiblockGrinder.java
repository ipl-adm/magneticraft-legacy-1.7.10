package com.cout970.magneticraft.util.multiblock.types;

import com.cout970.magneticraft.ManagerBlocks;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.util.multiblock.MB_Register;
import com.cout970.magneticraft.util.multiblock.Mg_Component;
import com.cout970.magneticraft.util.multiblock.Multiblock;
import com.cout970.magneticraft.util.multiblock.SimpleComponent;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class MultiblockGrinder extends Multiblock {

    @Override
    public void init() {
        SimpleComponent a = new SimpleComponent(Blocks.air) {
            @Override
            public boolean isCorrect(World w, VecInt p, int x, int y, int z, Multiblock c, MgDirection e, int meta) {
                return true;
            }
        };
        SimpleComponent i = new SimpleComponent(ManagerBlocks.multi_io);
        SimpleComponent e = new SimpleComponent(ManagerBlocks.multi_energy_low);
        SimpleComponent b = new SimpleComponent(ManagerBlocks.chassis);
        SimpleComponent gc = new SimpleComponent(ManagerBlocks.grinder);

        Mg_Component[][][] m =
                {//     {{z2,z1,z0}x2,{z2,z1,z0}x1,{z2,z1,z0}x0}y0
                        {{b, b, b}, {b, b, b}, {b, b, b}},
                        {{b, gc, b}, {e, a, e}, {b, i, b}},
                        {{b, b, b}, {b, a, b}, {b, b, b}},
                        {{b, b, b}, {b, a, b}, {b, b, b}},
                        {{b, b, b}, {b, i, b}, {b, b, b}},
                };

        VecInt p = new VecInt(-1, -1, 0);
        x = m.length;
        y = m[0].length;
        z = m[0][0].length;
        matrix = m;
        tran = p;
    }

    @Override
    public int getID() {
        return MB_Register.ID_GRINDER;
    }

}
