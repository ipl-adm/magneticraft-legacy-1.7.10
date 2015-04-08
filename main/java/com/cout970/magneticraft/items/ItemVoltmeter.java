package com.cout970.magneticraft.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyHandler;

import com.cout970.magneticraft.api.electricity.CableCompound;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.api.util.VecInt;

public class ItemVoltmeter extends ItemBasic{

	public ItemVoltmeter(String unlocalizedname) {
		super(unlocalizedname);
	}

	public boolean onItemUse(ItemStack item, EntityPlayer p, World w, int x, int y, int z, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_)
	{
		if(w.isRemote)return false;
		TileEntity t = w.getTileEntity(x, y, z);
		CableCompound comp = MgUtils.getConductor(t, MgDirection.getDirection(side).getVecInt(), -1);
		if(comp != null){
			for(IElectricConductor cond:comp.list()){
				double I = cond.getIntensity()*0.5;
				String s = String.format("Reading %.2fV %.3fA (%.2fW)", new Object[] {Double.valueOf(cond.getVoltage()), Double.valueOf(I), Double.valueOf(cond.getVoltage() * I)});
				p.addChatMessage(new ChatComponentText(s));
				return false;
			}
		}
		
		comp = MgUtils.getConductor(t, VecInt.NULL_VECTOR, -1);
		if(comp != null){
			IElectricConductor cond = comp.getCond(0);
			double I = cond.getIntensity()*0.5;
			String s = String.format("Reading %.2fV %.2fA (%.2fW)", new Object[] {Double.valueOf(cond.getVoltage()), Double.valueOf(I), Double.valueOf(cond.getVoltage() * I)});
			p.addChatMessage(new ChatComponentText(s));
			return false;
		}
		
		if(t instanceof IEnergyHandler){
			IEnergyHandler h = (IEnergyHandler) t;
			int stored = h.getEnergyStored(ForgeDirection.getOrientation(side));
			int max = h.getMaxEnergyStored(ForgeDirection.getOrientation(side));
			String s = "Energy Stored: "+stored+"/"+max+"RF";
			p.addChatMessage(new ChatComponentText(s));
			return false;
		}
		return false;
	}
}
