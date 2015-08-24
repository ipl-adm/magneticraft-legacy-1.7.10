package com.cout970.magneticraft.util.multiblock.types;

import com.cout970.magneticraft.ManagerBlocks;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.util.multiblock.MB_Register;
import com.cout970.magneticraft.util.multiblock.Multiblock;
import com.cout970.magneticraft.util.multiblock.SimpleComponent;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class MultiblockPolymerizer extends Multiblock {

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
        SimpleComponent t = new SimpleComponent(ManagerBlocks.copper_tank);
        SimpleComponent ht = new SimpleComponent(ManagerBlocks.heater);
        SimpleComponent d = new SimpleComponent(ManagerBlocks.polimerizer);

        SimpleComponent[][][] m =
                {
                        {{b, b, b}, {b, b, b}, {b, b, b}, {b, b, b}, {b, b, b}},
                        {{b, d, b}, {i, a, i}, {b, a, b}, {e, ht, e}, {b, t, b}},
                        {{b, b, b}, {b, b, b}, {b, b, b}, {b, b, b}, {b, b, b}}
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
        return MB_Register.ID_POLIMERIZER;
    }

}
