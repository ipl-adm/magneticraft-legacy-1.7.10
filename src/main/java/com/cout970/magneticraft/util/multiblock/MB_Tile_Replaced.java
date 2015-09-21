package com.cout970.magneticraft.util.multiblock;


import net.minecraft.item.ItemStack;

import java.util.List;

public interface MB_Tile_Replaced {

    void setDrops(List<ItemStack> drops);

    List<ItemStack> getDrops();

}
