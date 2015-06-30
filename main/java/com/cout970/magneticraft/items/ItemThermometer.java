package com.cout970.magneticraft.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import com.cout970.magneticraft.api.heat.CompoundHeatCables;
import com.cout970.magneticraft.api.heat.IHeatConductor;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.tabs.CreativeTabsMg;

public class ItemThermometer extends ItemBasic{

	public ItemThermometer(String unlocalizedname) {
		super(unlocalizedname);
		setCreativeTab(CreativeTabsMg.SteamAgeTab);
		setMaxStackSize(1);
	}
	
	public boolean onItemUse(ItemStack item, EntityPlayer p, World w, int x, int y, int z, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_)
	{
		if(w.isRemote)return false;
		TileEntity t = w.getTileEntity(x, y, z);
		CompoundHeatCables comp = MgUtils.getHeatCond(t, VecInt.NULL_VECTOR);
		if(comp != null){
			for(IHeatConductor heat : comp.list()){
				if(heat != null){
					p.addChatMessage(new ChatComponentText("Temperature: "+String.format("%.2f",heat.getTemperature())));
				}
			}
		}
		return false;
	}

}
