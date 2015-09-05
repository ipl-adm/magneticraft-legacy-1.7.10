package com.cout970.magneticraft.compat.minetweaker;

import com.cout970.magneticraft.api.access.MgRecipeRegister;
import com.cout970.magneticraft.api.access.RecipeRefinery;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.liquid.ILiquidStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.magneticraft.Refinery")
public class Refinery {

    @ZenMethod
    public static void addRecipe(ILiquidStack in, ILiquidStack out1, ILiquidStack out2, ILiquidStack out3) {
        FluidStack a = MgMinetweaker.toFluid(in),
                b = MgMinetweaker.toFluid(out1),
                c = MgMinetweaker.toFluid(out2),
                d = MgMinetweaker.toFluid(out3);
        if (a == null || b == null || c == null || d == null) return;
        RecipeRefinery r = new RecipeRefinery(a, b, c, d);
        MineTweakerAPI.apply(new AddRecipe(r));
    }

    @ZenMethod
    public static void removeRecipe(ILiquidStack in) {
        FluidStack f = MgMinetweaker.toFluid(in);
        if (f == null) return;
        RecipeRefinery r = RecipeRefinery.getRecipe(f);
        if (r == null) return;
        MineTweakerAPI.apply(new RemoveRecipe(r));
    }

    public static class AddRecipe implements IUndoableAction {

        private final RecipeRefinery r;

        public AddRecipe(RecipeRefinery r) {
            this.r = r;
        }

        @Override
        public void apply() {
            MgRecipeRegister.refinery.add(r);
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
            MgRecipeRegister.refinery.remove(r);
        }
    }

    public static class RemoveRecipe implements IUndoableAction {

        private final RecipeRefinery r;

        public RemoveRecipe(RecipeRefinery r) {
            this.r = r;
        }

        @Override
        public void apply() {
            MgRecipeRegister.refinery.remove(r);
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
            MgRecipeRegister.refinery.add(r);
        }
    }
}
