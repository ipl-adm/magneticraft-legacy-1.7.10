package com.cout970.magneticraft.api.access;

import com.cout970.magneticraft.api.util.MgUtils;
import net.minecraft.item.ItemStack;

public class RecipeHammerTable {
    protected final ItemStack input;
    protected final ItemStack output;

    public RecipeHammerTable(ItemStack input, ItemStack output) {
        this.input = input;
        this.output = output;
    }

    public ItemStack getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return output;
    }

    public boolean matches(ItemStack i) {
        return MgUtils.areEqual(input, i, true);
    }

    public static RecipeHammerTable getRecipe(ItemStack i) {
        for (RecipeHammerTable r : MgRecipeRegister.hammer_table) {
            if (r.matches(i)) {
                return r;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Crushing Table Recipe, Input: " + input.getDisplayName() + ", Output: " + output.getDisplayName();
    }
}