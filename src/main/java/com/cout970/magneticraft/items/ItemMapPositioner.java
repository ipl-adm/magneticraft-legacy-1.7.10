package com.cout970.magneticraft.items;

import com.cout970.magneticraft.tabs.CreativeTabsMg;
import com.cout970.magneticraft.tileentity.TileMirror;
import com.cout970.magneticraft.tileentity.TileSolarTowerCore;

import codechicken.lib.vec.BlockCoord;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ItemMapPositioner extends ItemBasic {

    public ItemMapPositioner(String unlocalizedname) {
        super(unlocalizedname);
        setCreativeTab(CreativeTabsMg.SteamAgeTab);
    }

    public boolean onItemUse(ItemStack item, EntityPlayer p, World w, int x, int y, int z, int side, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
        TileEntity t = w.getTileEntity(x, y, z);
        if (t instanceof TileSolarTowerCore) {
            item.stackTagCompound = new NBTTagCompound();
            int[] i = {x, y, z};
            item.stackTagCompound.setIntArray("coords", i);
        } else if (t instanceof TileMirror) {
            if (item.stackTagCompound != null) {
                int[] i = item.stackTagCompound.getIntArray("coords");
                if (i.length == 3) {
                    if (p.isSneaking()) {
                        for (int h = -5; h <= 5; h++) {
                            for (int j = -5; j <= 5; j++) {
                                TileEntity e = w.getTileEntity(x + h, y, z + j);
                                if (e instanceof TileMirror) {
                                    ((TileMirror) e).setTarget(new BlockCoord(i));
                                }
                            }
                        }
                    } else {
                        ((TileMirror) t).setTarget(new BlockCoord(i));
                    }

                }
            }
        }
        return false;
    }
}
