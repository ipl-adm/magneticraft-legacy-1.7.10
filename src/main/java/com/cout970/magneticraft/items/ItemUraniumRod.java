package com.cout970.magneticraft.items;

import java.util.List;

import com.cout970.magneticraft.api.radiation.IRadiactiveItem;
import com.cout970.magneticraft.api.util.NBTUtils;
import com.cout970.magneticraft.client.tilerender.ModelTextures;
import com.cout970.magneticraft.tabs.CreativeTabsMg;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemUraniumRod extends ItemBasic implements IRadiactiveItem {

    public static final double INITIAL_NUMBER_OF_GRAMES = 10000;//10Kg

    public ItemUraniumRod(String unlocalizedname) {
        super(unlocalizedname);
        setMaxDamage(1000);
        setMaxStackSize(1);
        setCreativeTab(CreativeTabsMg.InformationAgeTab);
    }

    @SuppressWarnings("unchecked")
    public void getSubItems(Item unknown, CreativeTabs tab, @SuppressWarnings("rawtypes") List subItems) {
        ItemStack a = new ItemStack(this, 1, 0);
        NBTUtils.setDouble(NBT_GRAMS_NAME, a, INITIAL_NUMBER_OF_GRAMES);
        subItems.add(a);
    }

    public void onCreated(ItemStack i, World par2World, EntityPlayer par3EntityPlayer) {
        NBTUtils.setDouble(NBT_GRAMS_NAME, i, INITIAL_NUMBER_OF_GRAMES);
    }

    public boolean onItemUse(ItemStack item, EntityPlayer p, World w, int x, int y, int z, int side, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
        NBTUtils.setDouble(NBT_GRAMS_NAME, item, 100);
        return false;
    }

    @Override
    public int getDamage(ItemStack stack) {
        return 1000 - (int) (NBTUtils.getDouble(NBT_GRAMS_NAME, stack) * 1000 / INITIAL_NUMBER_OF_GRAMES);
    }

    @Override
    public double getGrams(ItemStack stack) {
        return NBTUtils.getDouble(NBT_GRAMS_NAME, stack);
    }

    @Override
    public void setGrams(ItemStack stack, double n) {
        NBTUtils.setDouble(NBT_GRAMS_NAME, stack, n);
    }

    @Override
    public double getDecayConstant(ItemStack itemStack) {
        return 3.12E-17;//Lambda(235U) = 9.8486E-10 year = 3.1209E-17 sec -- HalfLife(235U) = 7.038E8 years = 2.2210E16 sec
    }

    @Override
    public double getEnergyPerFision(ItemStack itemStack) {
        return 3.2E-11;//235U 197.9Mev = 3.1707E-11J
    }

    @Override
    public ResourceLocation getResourceLocation() {
        return ModelTextures.ROD_URANIUM;
    }

}
