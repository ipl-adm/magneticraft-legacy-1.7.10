package com.cout970.magneticraft.util.multiblock;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import net.minecraft.world.World;

public abstract class Mg_Component {

    public abstract boolean isCorrect(World w, VecInt p, int x, int y, int z, Multiblock c, MgDirection e, int meta);

    public abstract void establish(World w, VecInt p, int x, int y, int z, Multiblock c, MgDirection e, int meta);

    public abstract void destroy(World w, VecInt p, int x, int y, int z, Multiblock c, MgDirection e, int meta);

    public abstract String getErrorMessage(World w, VecInt p, int x, int y, int z, Multiblock c, MgDirection e, int meta);
}
