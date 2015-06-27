package com.cout970.magneticraft.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import com.cout970.magneticraft.api.electricity.CompoundElectricCables;
import com.cout970.magneticraft.api.electricity.IElectricPole;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.NBTUtils;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.tabs.CreativeTabsMg;
import com.cout970.magneticraft.util.Log;

public class ItemHeavyCopperCoil extends ItemBasic{

	public ItemHeavyCopperCoil(String unlocalizedname) {
		super(unlocalizedname);
		setCreativeTab(CreativeTabsMg.IndustrialAgeTab);
	}

	public boolean onItemUse(ItemStack item, EntityPlayer p, World w, int x, int y, int z, int side, float p_77648_8_, float p_77648_9_, float p_77648_10_)
	{
		TileEntity t = w.getTileEntity(x, y, z);
		if(t instanceof IElectricPole){
			IElectricPole pole1 = (IElectricPole) t;
			if(NBTUtils.getBoolean("Connected", item)){
				TileEntity tile = w.getTileEntity(NBTUtils.getInteger("xCoord", item), NBTUtils.getInteger("yCoord", item), NBTUtils.getInteger("zCoord", item));
				if(tile instanceof IElectricPole){
					IElectricPole pole2 = (IElectricPole) tile;
					if(pole1 == pole2){
						if(!w.isRemote)
						p.addChatMessage(new ChatComponentText("You cannot attach this wire in the same block"));
						return false;
					}
					MgDirection dir = MgDirection.getDirection(NBTUtils.getInteger("Side", item));
					CompoundElectricCables comp = pole2.getConds(VecInt.NULL_VECTOR, 0);
					CompoundElectricCables comp2 = pole1.getConds(VecInt.NULL_VECTOR, 0);
					if(comp != null && comp2 != null){
						pole1.connectWire(MgDirection.getDirection(side), 0, comp.getCond(0));
						pole2.connectWire(dir, 0, comp2.getCond(0));
						NBTUtils.setBoolean("Connected", item, false);
						if(!w.isRemote)
						p.addChatMessage(new ChatComponentText("Successfully attached wire betwenn poles"));
						return true;
					}
				}
			}else{
				CompoundElectricCables comp = pole1.getConds(VecInt.NULL_VECTOR, 0);
				if(comp != null && comp.count() > 0){
					NBTUtils.setBoolean("Connected", item, true);
					NBTUtils.setInteger("xCoord", item, x);
					NBTUtils.setInteger("yCoord", item, y);
					NBTUtils.setInteger("zCoord", item, z);
					NBTUtils.setInteger("Side", item, side);
					if(!w.isRemote)
					p.addChatMessage(new ChatComponentText("Attached wire on this pole"));
					return true;
				}
			}
		}
		return false;
	}
}
