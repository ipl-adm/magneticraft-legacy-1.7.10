package com.cout970.magneticraft.items;

import com.cout970.magneticraft.api.electricity.item.IBatteryItem;
import com.cout970.magneticraft.api.util.EnergyConversor;
import com.cout970.magneticraft.tabs.CreativeTabsMg;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cofh.api.energy.IEnergyContainerItem;


public class ItemBattery extends ItemCharged{
	
	public ItemBattery(String unlocalizedname) {
		super(unlocalizedname,1000000);
		setMaxStackSize(1);
		setCreativeTab(CreativeTabsMg.ElectricalAgeTab);
	}
	
	public ItemStack onItemRightClick(ItemStack stack, World w, EntityPlayer p)
    {
		if(!p.isSneaking()){
			ItemStack[] i = p.inventory.mainInventory;
			for(ItemStack s : i){
				if(s != null){
					Item it = s.getItem();
					if(it instanceof IBatteryItem && !(it instanceof ItemBattery)){
						IBatteryItem st = (IBatteryItem) it;
						int space = (int) (st.getMaxCharge()-st.getCharge(s));
						int toMove = Math.min(space, getCharge(stack));
						if(toMove > 0){
							st.charge(s, toMove);
							discharge(stack, toMove);
						}
					}else if(it instanceof IEnergyContainerItem){//calcs in J
						IEnergyContainerItem st = (IEnergyContainerItem)it;
						int space = (int) (st.getMaxEnergyStored(s)-st.getEnergyStored(s));
						int toMove = (int) Math.min(EnergyConversor.RFtoJ(space), getCharge(stack));
						if(toMove > 0){
							st.receiveEnergy(s, EnergyConversor.JtoRF(toMove), false);
							discharge(stack, toMove);
						}
					}
						
				}
			}
		}
		return super.onItemRightClick(stack, w, p);
    }
}
