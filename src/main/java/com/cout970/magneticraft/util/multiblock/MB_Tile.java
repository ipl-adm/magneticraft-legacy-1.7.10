package com.cout970.magneticraft.util.multiblock;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import net.minecraft.world.World;

public interface MB_Tile {

    void setControlPos(VecInt blockPosition);

    VecInt getControlPos();

    void onDestroy(World w, VecInt p, Multiblock c, MgDirection e);

    void onActivate(World w, VecInt p, Multiblock c, MgDirection e);

    Multiblock getMultiblock();

    void setMultiblock(Multiblock m);

    void setDirection(MgDirection e);

    MgDirection getDirection();
}
