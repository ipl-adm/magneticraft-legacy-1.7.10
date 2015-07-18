package com.cout970.magneticraft.items;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cofh.api.energy.IEnergyContainerItem;

import com.cout970.magneticraft.api.electricity.item.IBatteryItem;
import com.cout970.magneticraft.api.util.EnergyConversor;
import com.cout970.magneticraft.tabs.CreativeTabsMg;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemToolCharger extends ItemCharged{

	public ItemToolCharger(String unlocalizedname) {
		super(unlocalizedname, (int)EnergyConversor.RFtoW(100000));
		setMaxStackSize(1);
		setCreativeTab(CreativeTabsMg.ElectricalAgeTab);
	}

	public void onUpdate(ItemStack item, World w, Entity p, int c, boolean flag) {
		super.onUpdate(item, w, p, c, flag);
		if(p instanceof EntityPlayer){
			ItemStack[] i = ((EntityPlayer)p).inventory.mainInventory;
			for(ItemStack s : i){
				if(s != null){
					Item it = s.getItem();
					if(it instanceof IBatteryItem){
						IBatteryItem st = (IBatteryItem) it;
						if(st.canProvideEnergy(item)){
							int space = (int) (getMaxCharge(item)-getCharge(item));
							int toMove = Math.min(space, st.getCharge(s));
							toMove = (int) Math.min(toMove, EnergyConversor.RFtoW(500));
							if(toMove > 0){
								st.discharge(s, toMove);
								charge(item, toMove);
							}
						}else if(st.canAcceptCharge(item)){
							int space = (int) (st.getMaxCharge(s)-st.getCharge(s));
							int toMove = Math.min(space, getCharge(item));
							toMove = (int) Math.min(toMove, EnergyConversor.RFtoW(500));
							if(toMove > 0){
								st.charge(s, toMove);
								discharge(item, toMove);
							}
						}
					}else if(it instanceof IEnergyContainerItem){//calcs in RF
						IEnergyContainerItem st = (IEnergyContainerItem)it;
						int space = (int) (st.getMaxEnergyStored(s)-st.getEnergyStored(s));
						int toMove = (int) Math.min(space, EnergyConversor.WtoRF(getCharge(item)));
						if(toMove > 0){
							st.receiveEnergy(s, toMove, false);
							discharge(item, (int)EnergyConversor.RFtoW(toMove));
						}
					}
				}
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack item, EntityPlayer player, List list, boolean flag) {
		super.addInformation(item, player, list, flag);
		list.add(ItemBlockMg.format+"Charges tools in the inventory using energy from batteries");
	}
}
