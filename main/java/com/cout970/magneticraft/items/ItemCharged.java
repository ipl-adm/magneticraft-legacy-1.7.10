package com.cout970.magneticraft.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.api.electricity.item.IBatteryItem;
import com.cout970.magneticraft.api.util.NBTUtils;

public class ItemCharged extends ItemBasic implements IBatteryItem{

	public int MAX_CHARGE;

	public ItemCharged(String unlocalizedname, int charge) {
		super(unlocalizedname);
		MAX_CHARGE = charge;
		this.setHasSubtypes(true);
		this.setMaxDamage(100);
	}

	@Override
	public int getCharge(ItemStack it) {
		int charge = NBTUtils.getInteger("Charge", it);
		it.setItemDamage(getMetadataByPercent(charge, MAX_CHARGE));
		return charge;
	}

	@Override
	public void discharge(ItemStack stack, int energy) {
		int c = Math.max(getCharge(stack)-energy, 0);
		NBTUtils.setInteger("Charge", stack, c);
	}

	@Override
	public int charge(ItemStack stack, int energy) {
		int old = getCharge(stack);
		int c = Math.min(old+energy, getMaxCharge());
		NBTUtils.setInteger("Charge", stack, c);
		return getCharge(stack)-old;
	}

	@Override
	public int getMaxCharge() {
		return MAX_CHARGE;
	}

	public int getMetadataByPercent(int energy,int capacity){//inveted for the durability display 
		return 101-(100*energy/capacity);
	}

	@SuppressWarnings("unchecked")
	public void getSubItems(Item unknown, CreativeTabs tab, @SuppressWarnings("rawtypes") List subItems){
		ItemStack a = new ItemStack(this, 1, this.getMaxDamage());	
		((IBatteryItem)a.getItem()).charge(a, MAX_CHARGE);
		subItems.add(a);
		subItems.add(new ItemStack(this, 1, 0));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addInformation(ItemStack i, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		par3List.add((getCharge(i)/1000f)+"k"+Magneticraft.ENERGY_STORED_NAME+" / "+(MAX_CHARGE/1000f)+"k"+Magneticraft.ENERGY_STORED_NAME);
	}


	@Override
	public int getDamage(ItemStack stack) {
		return getMetadataByPercent(getCharge(stack), MAX_CHARGE);
	}
}
