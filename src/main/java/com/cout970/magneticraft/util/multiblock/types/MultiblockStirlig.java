package com.cout970.magneticraft.util.multiblock.types;

import com.cout970.magneticraft.ManagerBlocks;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.util.multiblock.MB_Register;
import com.cout970.magneticraft.util.multiblock.Multiblock;
import com.cout970.magneticraft.util.multiblock.SimpleComponent;

public class MultiblockStirlig extends Multiblock {

    @Override
    public void init() {
        SimpleComponent c = new SimpleComponent(ManagerBlocks.multi_heat);
        SimpleComponent b = new SimpleComponent(ManagerBlocks.multi_energy_low);
        SimpleComponent d = new SimpleComponent(ManagerBlocks.chassis);
        SimpleComponent e = new SimpleComponent(ManagerBlocks.stirling);

        SimpleComponent[][][] m =
                {//     {{z2,z1,z0}x2,{z2,z1,z0}x1,{z2,z1,z0}x0}y0
                        {{d, d, d}, {d, d, d}, {d, d, d}},
                        {{d, e, d}, {d, c, d}, {d, b, d}},
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
        return MB_Register.ID_STIRLING;
    }

}
