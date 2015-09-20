package com.cout970.magneticraft.api.radiation;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**
 * @author Cout970
 */
public interface IRadiactiveItem {

    String NBT_GRAMS_NAME = "gramos";//spanish word fro grams

    double getGrams(ItemStack itemStack);//amount of radioactive nuclei, correspond to the durability

    void setGrams(ItemStack itemStack, double n);//set durability

    double getDecayConstant(ItemStack itemStack);//decay rate

    double getEnergyPerFision(ItemStack itemStack);

    ResourceLocation getResourceLocation();//for reactor representation

}
