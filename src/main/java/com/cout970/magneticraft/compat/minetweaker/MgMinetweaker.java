package com.cout970.magneticraft.compat.minetweaker;

import minetweaker.MineTweakerAPI;
import minetweaker.api.block.IBlock;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class MgMinetweaker {

    public static void init() {
        MineTweakerAPI.registerClass(Crusher.class);
        MineTweakerAPI.registerClass(Grinder.class);
        MineTweakerAPI.registerClass(Thermopile.class);
        MineTweakerAPI.registerClass(BiomassBurner.class);
        MineTweakerAPI.registerClass(OilDistillery.class);
        MineTweakerAPI.registerClass(Refinery.class);
        MineTweakerAPI.registerClass(Sifter.class);
        MineTweakerAPI.registerClass(Polymerizer.class);
        MineTweakerAPI.registerClass(CrushingTable.class);
    }

    public static ItemStack toStack(IItemStack iStack) {
        return MineTweakerMC.getItemStack(iStack);
    }

    public static Block getBlock(IBlock block) {
        return MineTweakerMC.getBlock(block);
    }

    public static FluidStack toFluid(ILiquidStack in) {
        return MineTweakerMC.getLiquidStack(in);
    }
}
