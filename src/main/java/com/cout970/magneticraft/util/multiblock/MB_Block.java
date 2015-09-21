package com.cout970.magneticraft.util.multiblock;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import net.minecraft.world.World;

public interface MB_Block {

    void mutates(World w, VecInt blockPosition, Multiblock c, MgDirection e);

    void destroy(World w, VecInt blockPosition, Multiblock c, MgDirection e);

}
