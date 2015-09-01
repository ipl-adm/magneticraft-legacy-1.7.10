package com.cout970.magneticraft.util.multiblock.types;

import com.cout970.magneticraft.ManagerBlocks;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.util.multiblock.MB_Register;
import com.cout970.magneticraft.util.multiblock.Multiblock;
import com.cout970.magneticraft.util.multiblock.SimpleComponent;

public class MultiblockTurbine extends Multiblock {

    @Override
    public void init() {
        SimpleComponent c = new SimpleComponent(ManagerBlocks.copper_tank);
        SimpleComponent b = new SimpleComponent(ManagerBlocks.multi_energy_medium);
        SimpleComponent d = new SimpleComponent(ManagerBlocks.chassis);
        SimpleComponent e = new SimpleComponent(ManagerBlocks.steam_turbine);

        SimpleComponent[][][] m =
                {//     {{z2,z1,z0}x2,{z2,z1,z0}x1,{z2,z1,z0}x0}y0
                        {{d, d, d}, {d, d, d}, {d, d, d}, {d, d, d}, {d, d, d}},
                        {{d, e, d}, {c, d, c}, {c, d, c}, {d, d, d}, {d, d, d}},
                        {{d, d, d}, {d, b, d}, {d, d, d}, {d, d, d}, {d, d, d}},
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
        return MB_Register.ID_TURBINE;
    }

}
