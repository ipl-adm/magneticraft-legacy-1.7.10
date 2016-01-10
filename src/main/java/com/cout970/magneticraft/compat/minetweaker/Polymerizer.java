package com.cout970.magneticraft.compat.minetweaker;

import com.cout970.magneticraft.api.access.MgRecipeRegister;
import com.cout970.magneticraft.api.access.RecipePolymerizer;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.magneticraft.Polymerizer")
public class Polymerizer {

    @ZenMethod
    public static void addRecipe(ILiquidStack f, IItemStack in, IItemStack out0, double temp) {

        ItemStack a = MgMinetweaker.toStack(in), b = MgMinetweaker.toStack(out0);
        FluidStack fluid = MgMinetweaker.toFluid(f);

        if (a == null || b == null) return;
        RecipePolymerizer r = new RecipePolymerizer(fluid, a, b, temp);
        MineTweakerAPI.apply(new AddRecipe(r));
    }

    @ZenMethod
    public static void removeRecipe(IItemStack input) {
        RecipePolymerizer r = RecipePolymerizer.getRecipe(MgMinetweaker.toStack(input));
        if (r != null) {
            MineTweakerAPI.apply(new RemoveRecipe(r));
        }
    }

    public static class AddRecipe implements IUndoableAction {

        private final RecipePolymerizer r;

        public AddRecipe(RecipePolymerizer r) {
            this.r = r;
        }

        @Override
        public void apply() {
            MgRecipeRegister.polymerizer.add(r);
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
            MgRecipeRegister.polymerizer.remove(r);
        }
    }

    public static class RemoveRecipe implements IUndoableAction {

        private final RecipePolymerizer r;

        public RemoveRecipe(RecipePolymerizer r) {
            this.r = r;
        }

        @Override
        public void apply() {
            MgRecipeRegister.polymerizer.remove(r);
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
            MgRecipeRegister.polymerizer.add(r);
        }
    }
}
