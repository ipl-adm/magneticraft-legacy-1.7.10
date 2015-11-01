package com.cout970.magneticraft.items;

import com.cout970.magneticraft.Magneticraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemGuideBook extends ItemBasic {

    public ItemGuideBook(String unlocalizedname) {
        super(unlocalizedname);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
    	if (player.isSneaking()) return item;
    	if(!world.isRemote)
    		player.openGui(Magneticraft.INSTANCE, 1, world, (int) player.posX, (int) player.posY, (int) player.posZ);
    	return item;
    }

}
