package com.cout970.magneticraft.compat.minetweaker;

import com.cout970.magneticraft.api.access.MgRecipeRegister;
import com.cout970.magneticraft.api.access.RecipeCrushingTable;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.magneticraft.CrushingTable")
public class CrushingTable {

    @ZenMethod
    public static void addRecipe(IItemStack in, IItemStack out) {

        ItemStack a = MgMinetweaker.toStack(in), b = MgMinetweaker.toStack(out);

        if (a == null || b == null) return;
        RecipeCrushingTable r = new RecipeCrushingTable(a, b);
        MineTweakerAPI.apply(new AddRecipe(r));
    }

    @ZenMethod
    public static void removeRecipe(IItemStack input) {
        RecipeCrushingTable r = RecipeCrushingTable.getRecipe(MgMinetweaker.toStack(input));
        if (r != null) {
            MineTweakerAPI.apply(new RemoveRecipe(r));
        }
    }

    public static class AddRecipe implements IUndoableAction {

        private final RecipeCrushingTable r;

        public AddRecipe(RecipeCrushingTable r) {
            this.r = r;
        }

        @Override
        public void apply() {
            MgRecipeRegister.crushing_table.add(r);
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
            MgRecipeRegister.crushing_table.remove(r);
        }
    }

    public static class RemoveRecipe implements IUndoableAction {

        private final RecipeCrushingTable r;

        public RemoveRecipe(RecipeCrushingTable r) {
            this.r = r;
        }

        @Override
        public void apply() {
            MgRecipeRegister.crushing_table.remove(r);
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
            MgRecipeRegister.crushing_table.add(r);
        }
    }
}

