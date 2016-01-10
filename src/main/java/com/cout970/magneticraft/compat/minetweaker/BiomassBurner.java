package com.cout970.magneticraft.compat.minetweaker;

import com.cout970.magneticraft.api.access.MgRecipeRegister;
import com.cout970.magneticraft.api.access.RecipeBiomassBurner;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.magneticraft.BiomassBurner")
public class BiomassBurner {

    @ZenMethod
    public static void addFuel(IItemStack fuel, int burningTime) {
        ItemStack a = MgMinetweaker.toStack(fuel);
        if (a == null && burningTime <= 0) return;
        RecipeBiomassBurner r = new RecipeBiomassBurner(a, burningTime, true);
        MineTweakerAPI.apply(new AddRecipe(r));
    }

    @ZenMethod
    public static void removeFuel(IItemStack fuel) {
        ItemStack a = MgMinetweaker.toStack(fuel);
        RecipeBiomassBurner r = RecipeBiomassBurner.getRecipe(a);
        if (r != null) {
            MineTweakerAPI.apply(new RemoveRecipe(r));
        }
    }

    public static class AddRecipe implements IUndoableAction {

        private final RecipeBiomassBurner r;

        public AddRecipe(RecipeBiomassBurner r) {
            this.r = r;
        }

        @Override
        public void apply() {
            MgRecipeRegister.biomassBurner.add(r);
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
            MgRecipeRegister.biomassBurner.remove(r);
        }
    }

    public static class RemoveRecipe implements IUndoableAction {

        private final RecipeBiomassBurner r;

        public RemoveRecipe(RecipeBiomassBurner r) {
            this.r = r;
        }

        @Override
        public void apply() {
            MgRecipeRegister.biomassBurner.remove(r);
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
            MgRecipeRegister.biomassBurner.add(r);
        }
    }
}
