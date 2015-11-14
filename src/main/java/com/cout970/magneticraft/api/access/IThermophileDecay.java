package com.cout970.magneticraft.api.access;

import com.cout970.magneticraft.api.util.BlockInfo;
import net.minecraft.world.World;

import java.util.List;

/**
 * @author Cout970
 */
public interface IThermophileDecay {

    void onCheck(World w, List<BlockInfo> b, double tempHot, double tempCold);
}
