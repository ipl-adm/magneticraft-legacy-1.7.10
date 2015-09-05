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

public class ItemThoriumRod extends ItemBasic implements IRadiactiveItem {

    public static final double INITIAL_NUMBER_OF_GRAMES = 10000;//10Kg

    public ItemThoriumRod(String unlocalizedname) {
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
        return 1.56E-18;//Lambda(232Th) = 4,93E-11 year = 1.5633E-18 sec -- HalfLife(232Th) = 1.405E10 years = 4.4337E17 sec //divided by 10 because is to hight
    }

    @Override
    public double getEnergyPerFision(ItemStack itemStack) {
        return 6.5E-13;//232Th 4.081Mev = 6.4E-13J //multiply by 10 because is to low
    }

    @Override
    public ResourceLocation getResourceLocation() {
        return ModelTextures.ROD_THORIUM;
    }
}
