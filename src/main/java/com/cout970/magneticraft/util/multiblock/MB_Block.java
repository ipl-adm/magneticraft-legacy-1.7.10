package com.cout970.magneticraft.util.multiblock;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public interface MB_Block {

    void mutates(World w, BlockPos blockPosition, Multiblock c, EnumFacing e);

    void destroy(World w, BlockPos blockPosition, Multiblock c, EnumFacing e);

}
