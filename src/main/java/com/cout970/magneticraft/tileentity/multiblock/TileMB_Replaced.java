package com.cout970.magneticraft.tileentity.multiblock;

import com.cout970.magneticraft.util.multiblock.MB_Tile_Replaced;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;
import java.util.List;

public class TileMB_Replaced extends TileMB_Base implements MB_Tile_Replaced {

    public List<ItemStack> drops = new ArrayList<>();

    @Override
    public void setDrops(List<ItemStack> drop) {
        drops.clear();
        if (drop != null) {
            drops.addAll(drop);
        }
    }

    @Override
    public List<ItemStack> getDrops() {
        return drops;
    }

    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        NBTTagList list = new NBTTagList();
        nbt.setInteger("dropSize", drops.size());
        for (ItemStack drop : drops) {
            NBTTagCompound t = new NBTTagCompound();
            if (drop != null) {
                drop.writeToNBT(t);
            }
            list.appendTag(t);
        }
        nbt.setTag("Drops", list);
    }

    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        drops.clear();
        int dropSize = nbt.getInteger("dropSize");
        ItemStack item;
        NBTTagList list = nbt.getTagList("Drops", 10);
        for (int i = 0; i < dropSize; i++) {
            NBTTagCompound t = list.getCompoundTagAt(i);
            item = ItemStack.loadItemStackFromNBT(t);
            drops.add(item);
        }
    }
}
