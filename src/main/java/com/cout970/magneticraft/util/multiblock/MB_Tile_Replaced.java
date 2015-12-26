package com.cout970.magneticraft.util.multiblock;


import net.minecraft.item.ItemStack;

import java.util.List;

public interface MB_Tile_Replaced {

    List<ItemStack> getDrops();

    void setDrops(List<ItemStack> drops);

}
