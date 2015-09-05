package com.cout970.magneticraft.compat.minetweaker;

import com.cout970.magneticraft.api.access.MgRecipeRegister;
import com.cout970.magneticraft.api.access.RecipeHammerTable;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.magneticraft.HammerTable")
public class HammerTable {

    @ZenMethod
    public static void addRecipe(IItemStack in, IItemStack out) {

        ItemStack a = MgMinetweaker.toStack(in), b = MgMinetweaker.toStack(out);

        if (a == null || b == null) return;
        RecipeHammerTable r = new RecipeHammerTable(a, b);
        MineTweakerAPI.apply(new AddRecipe(r));
    }

    @ZenMethod
    public static void removeRecipe(IItemStack input) {
        RecipeHammerTable r = RecipeHammerTable.getRecipe(MgMinetweaker.toStack(input));
        MineTweakerAPI.apply(new RemoveRecipe(r));
    }

    public static class AddRecipe implements IUndoableAction {

        private final RecipeHammerTable r;

        public AddRecipe(RecipeHammerTable r) {
            this.r = r;
        }

        @Override
        public void apply() {
            MgRecipeRegister.hammer_table.add(r);
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
            MgRecipeRegister.hammer_table.remove(r);
        }
    }

    public static class RemoveRecipe implements IUndoableAction {

        private final RecipeHammerTable r;

        public RemoveRecipe(RecipeHammerTable r) {
            this.r = r;
        }

        @Override
        public void apply() {
            MgRecipeRegister.hammer_table.remove(r);
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
            MgRecipeRegister.hammer_table.add(r);
        }
    }
}

