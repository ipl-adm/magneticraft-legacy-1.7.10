package com.cout970.magneticraft.items;

import java.util.List;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.api.electricity.IBatteryItem;
import com.cout970.magneticraft.api.util.NBTUtils;
import com.cout970.magneticraft.items.block.ManagerTooltip;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemCharged extends ItemBasic implements IBatteryItem {

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
        int c = Math.max(getCharge(stack) - energy, 0);
        NBTUtils.setInteger("Charge", stack, c);
    }

    @Override
    public int charge(ItemStack stack, int energy) {
        int old = getCharge(stack);
        int c = Math.min(old + energy, getMaxCharge(stack));
        NBTUtils.setInteger("Charge", stack, c);
        return getCharge(stack) - old;
    }

    public int getMetadataByPercent(int energy, int capacity) {//inveted for the durability display
        return 101 - (100 * energy / capacity);
    }

    @SuppressWarnings("unchecked")
    public void getSubItems(Item unknown, CreativeTabs tab, @SuppressWarnings("rawtypes") List subItems) {
        ItemStack a = new ItemStack(this, 1, this.getMaxDamage());
        ((IBatteryItem) a.getItem()).charge(a, MAX_CHARGE);
        subItems.add(a);
        subItems.add(new ItemStack(this, 1, 0));
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public void addInformation(ItemStack i, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
    	par3List.add(ManagerTooltip.energy + (getCharge(i) / 1000f) + "k" + Magneticraft.ENERGY_STORED_NAME + " / " + (MAX_CHARGE / 1000f) + "k" + Magneticraft.ENERGY_STORED_NAME);
    	super.addInformation(i, par2EntityPlayer, par3List, par4);
    }


    @Override
    public int getDamage(ItemStack stack) {
        return getMetadataByPercent(getCharge(stack), MAX_CHARGE);
    }

    @Override
    public int getMaxCharge(ItemStack stack) {
        return MAX_CHARGE;
    }

    @Override
    public boolean canAcceptCharge(ItemStack stack) {
        return true;
    }

    @Override
    public boolean canExtractCharge(ItemStack stack) {
        return false;
    }

    @Override
    public boolean canProvideEnergy(ItemStack stack) {
        return false;
    }
}
