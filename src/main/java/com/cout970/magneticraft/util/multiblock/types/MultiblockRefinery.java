package com.cout970.magneticraft.util.multiblock.types;

import com.cout970.magneticraft.ManagerBlocks;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.util.multiblock.MB_Register;
import com.cout970.magneticraft.util.multiblock.Multiblock;
import com.cout970.magneticraft.util.multiblock.SimpleComponent;

public class MultiblockRefinery extends Multiblock {

    @Override
    public int getID() {
        return MB_Register.ID_REFINERY;
    }

    @Override
    public void init() {
        SimpleComponent r = new SimpleComponent(ManagerBlocks.refinery);
        SimpleComponent v = new SimpleComponent(ManagerBlocks.refinery_gap);
        SimpleComponent h = new SimpleComponent(ManagerBlocks.refinery_tank);
        SimpleComponent b = new SimpleComponent(ManagerBlocks.chassis);

        SimpleComponent[][][] m =
                {//     {{z2,z1,z0}x2,{z2,z1,z0}x1,{z2,z1,z0}x0}y0
                        {{b, b, b}, {b, b, b}, {b, b, b}},
                        {{v, r, v}, {v, v, v}, {b, h, b}},
                        {{v, v, v}, {v, v, v}, {b, b, b}},
                        {{v, v, v}, {v, v, v}, {b, h, b}},
                        {{v, v, v}, {v, v, v}, {b, b, b}},
                        {{v, v, v}, {v, v, v}, {b, h, b}},
                        {{v, v, v}, {v, v, v}, {b, b, b}},
                        {{v, v, v}, {v, v, v}, {b, h, b}}
                };
        VecInt p = new VecInt(-1, -1, 0);
        x = m.length;
        y = m[0].length;
        z = m[0][0].length;
        matrix = m;
        tran = p;
    }

}
