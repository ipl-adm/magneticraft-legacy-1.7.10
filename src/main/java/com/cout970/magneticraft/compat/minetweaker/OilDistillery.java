package com.cout970.magneticraft.compat.minetweaker;

import com.cout970.magneticraft.api.access.MgRecipeRegister;
import com.cout970.magneticraft.api.access.RecipeOilDistillery;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.liquid.ILiquidStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.magneticraft.OilDistillery")
public class OilDistillery {

    @ZenMethod
    public static void addRecipe(ILiquidStack in, ILiquidStack out, double cost) {
        FluidStack a = MgMinetweaker.toFluid(in),
                b = MgMinetweaker.toFluid(out);
        if (a != null && b != null) {
            RecipeOilDistillery r = new RecipeOilDistillery(a, b, cost);
            MineTweakerAPI.apply(new AddRecipe(r));
        }
    }

    @ZenMethod
    public static void removeRecipe(ILiquidStack in) {
        FluidStack f = MgMinetweaker.toFluid(in);
        if (f == null) return;
        RecipeOilDistillery r = RecipeOilDistillery.getRecipe(f);
        if (r == null) return;
        MineTweakerAPI.apply(new RemoveRecipe(r));
    }

    public static class AddRecipe implements IUndoableAction {

        private final RecipeOilDistillery r;

        public AddRecipe(RecipeOilDistillery r) {
            this.r = r;
        }

        @Override
        public void apply() {
            MgRecipeRegister.oilDistillery.add(r);
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
            MgRecipeRegister.oilDistillery.remove(r);
        }
    }

    public static class RemoveRecipe implements IUndoableAction {

        private final RecipeOilDistillery r;

        public RemoveRecipe(RecipeOilDistillery r) {
            this.r = r;
        }

        @Override
        public void apply() {
            MgRecipeRegister.oilDistillery.remove(r);
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
            MgRecipeRegister.oilDistillery.add(r);
        }
    }
}
