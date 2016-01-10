package com.cout970.magneticraft.compat.minetweaker;

import com.cout970.magneticraft.api.access.MgRecipeRegister;
import com.cout970.magneticraft.api.access.RecipeGrinder;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.magneticraft.Grinder")
public class Grinder {

    @ZenMethod
    public static void addRecipe(IItemStack in, IItemStack out0, IItemStack out1, float prob1, IItemStack out2, float prob2) {

        ItemStack a = MgMinetweaker.toStack(in), b = MgMinetweaker.toStack(out0), c = MgMinetweaker.toStack(out1), d = MgMinetweaker.toStack(out2);

        if (a == null || b == null) return;
        RecipeGrinder r = new RecipeGrinder(a, b, c, prob1, d, prob2);
        MineTweakerAPI.apply(new AddRecipe(r));
    }

    @ZenMethod
    public static void removeRecipe(IItemStack input) {
        RecipeGrinder r = RecipeGrinder.getRecipe(MgMinetweaker.toStack(input));
        if (r != null) {
            MineTweakerAPI.apply(new RemoveRecipe(r));
        }
    }

    public static class AddRecipe implements IUndoableAction {

        private final RecipeGrinder r;

        public AddRecipe(RecipeGrinder r) {
            this.r = r;
        }

        @Override
        public void apply() {
            MgRecipeRegister.grinder.add(r);
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public String describe() {
            return "Adding " + r;
        }

        @Override
        public String describeUndo() {
            return "Removing " + r;
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }

        @Override
        public void undo() {
            MgRecipeRegister.grinder.remove(r);
        }
    }

    public static class RemoveRecipe implements IUndoableAction {

        private final RecipeGrinder r;

        public RemoveRecipe(RecipeGrinder r) {
            this.r = r;
        }

        @Override
        public void apply() {
            MgRecipeRegister.grinder.remove(r);
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public String describe() {
            return "Removing " + r;
        }

        @Override
        public String describeUndo() {
            return "Re-Adding " + r;
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }

        @Override
        public void undo() {
            MgRecipeRegister.grinder.add(r);
        }
    }
}
