package com.cout970.magneticraft.compat.minetweaker;

import com.cout970.magneticraft.api.access.MgRecipeRegister;
import com.cout970.magneticraft.api.access.RecipeSifter;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.magneticraft.Sifter")
public class Sifter {

    @ZenMethod
    public static void addRecipe(IItemStack in, IItemStack out0, IItemStack out1, float prob1) {

        ItemStack a = MgMinetweaker.toStack(in), b = MgMinetweaker.toStack(out0), c = MgMinetweaker.toStack(out1);

        if (a == null || b == null) return;
        RecipeSifter r = new RecipeSifter(a, b, c, prob1);
        MineTweakerAPI.apply(new AddRecipe(r));
    }

    @ZenMethod
    public static void removeRecipe(IItemStack input) {
        RecipeSifter r = RecipeSifter.getRecipe(MgMinetweaker.toStack(input));
        if (r != null) {
            MineTweakerAPI.apply(new RemoveRecipe(r));
        }
    }

    public static class AddRecipe implements IUndoableAction {

        private final RecipeSifter r;

        public AddRecipe(RecipeSifter r) {
            this.r = r;
        }

        @Override
        public void apply() {
            MgRecipeRegister.sifter.add(r);
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
            MgRecipeRegister.sifter.remove(r);
        }
    }

    public static class RemoveRecipe implements IUndoableAction {

        private final RecipeSifter r;

        public RemoveRecipe(RecipeSifter r) {
            this.r = r;
        }

        @Override
        public void apply() {
            MgRecipeRegister.sifter.remove(r);
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
            MgRecipeRegister.sifter.add(r);
        }
    }
}