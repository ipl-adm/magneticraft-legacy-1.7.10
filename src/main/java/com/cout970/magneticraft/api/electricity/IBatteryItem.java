package com.cout970.magneticraft.api.electricity;

import net.minecraft.item.ItemStack;

/**
 * @author Cout970
 */
public interface IBatteryItem {

    /**
     * the amount of energy in the battery in RF
     *
     * @param it
     * @return
     */
    int getCharge(ItemStack it);

    /**
     * the amount of energy to drain from the battery, must always positive
     *
     * @param it
     * @param energy
     */
    void discharge(ItemStack it, int energy);

    /**
     * the amount of energy to add to the battery
     *
     * @param stack
     * @param energy
     * @return
     */
    int charge(ItemStack stack, int energy);

    /**
     * the max amount of charge that the battery can store
     *
     * @return
     */
    int getMaxCharge(ItemStack stack);

    boolean canAcceptCharge(ItemStack stack);

    boolean canExtractCharge(ItemStack stack);

    boolean canProvideEnergy(ItemStack stack);
}