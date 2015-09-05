package com.cout970.magneticraft.compat.nei;

import codechicken.nei.api.IOverlayHandler;
import codechicken.nei.recipe.IRecipeHandler;
import com.cout970.magneticraft.ManagerNetwork;
import com.cout970.magneticraft.client.gui.GuiCrafter;
import com.cout970.magneticraft.messages.MessageClientCrafterUpdate;
import com.cout970.magneticraft.messages.MessageGuiClick;
import com.cout970.magneticraft.tileentity.TileCrafter;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;


public class CrafterOverlayHandler implements IOverlayHandler {

    @Override
    public void overlayRecipe(GuiContainer firstGui, IRecipeHandler recipe,
                              int recipeIndex, boolean shift) {

        if (firstGui instanceof GuiCrafter) {
            GuiCrafter crafter = (GuiCrafter) firstGui;

            TileCrafter entity = (TileCrafter) crafter.tile;


            MessageGuiClick msg = new MessageGuiClick(entity, 0, 1);
            ManagerNetwork.INSTANCE.sendToServer(msg);

            for (int z = 0; z < recipe.getIngredientStacks(recipeIndex).size(); z++) {

                int x, y;
                x = recipe.getIngredientStacks(recipeIndex).get(z).relx;
                y = recipe.getIngredientStacks(recipeIndex).get(z).rely;

                int slot = 0;
                if (x == 25) {
                    if (y == 6) {
                        slot = 0;
                    }
                    if (y == 24) {
                        slot = 3;
                    }
                    if (y == 42) {
                        slot = 6;
                    }
                }
                if (x == 43) {
                    if (y == 6) {
                        slot = 1;
                    }
                    if (y == 24) {
                        slot = 4;
                    }
                    if (y == 42) {
                        slot = 7;
                    }
                }
                if (x == 61) {
                    if (y == 6) {
                        slot = 2;
                    }
                    if (y == 24) {
                        slot = 5;
                    }
                    if (y == 42) {
                        slot = 8;
                    }
                }
                if (recipe.getIngredientStacks(recipeIndex).get(z).items.length > 0) {
                    ItemStack i = recipe.getIngredientStacks(recipeIndex).get(z).items[0];
                    if (i != null) {
                        MessageClientCrafterUpdate msg2 = new MessageClientCrafterUpdate(entity, slot, i);
                        ManagerNetwork.INSTANCE.sendToServer(msg2);
                    }
                }
            }

            entity.refreshItemMatches();
        }
    }
}
