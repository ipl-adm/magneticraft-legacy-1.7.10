package com.cout970.magneticraft.util.multiblock;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import net.minecraft.world.World;

public interface MB_ControlBlock extends MB_Block {

    MgDirection getDirection(World w, VecInt p);

    Multiblock getStructure();
}
