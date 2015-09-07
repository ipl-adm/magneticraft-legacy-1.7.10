package com.cout970.magneticraft.compat.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.ManagerBlocks;
import com.cout970.magneticraft.client.gui.GuiCrafter;
import net.minecraft.item.ItemStack;

public class NEIMagneticraftConfig implements IConfigureNEI {

    @Override
    public String getName() {
        return "Magneticraft";
    }

    @Override
    public String getVersion() {
        return Magneticraft.VERSION;
    }

    @Override
    public void loadConfig() {
        API.registerRecipeHandler(new CraftingCrusher());
        API.registerUsageHandler(new CraftingCrusher());
        API.registerRecipeHandler(new CraftingGrinder());
        API.registerUsageHandler(new CraftingGrinder());
        API.registerRecipeHandler(new CraftingPolymerizer());
        API.registerUsageHandler(new CraftingPolymerizer());
        API.registerRecipeHandler(new CraftingBiomassBurner());
        API.registerUsageHandler(new CraftingBiomassBurner());
        API.registerRecipeHandler(new CraftingSifter());
        API.registerUsageHandler(new CraftingSifter());
        API.registerRecipeHandler(new CraftingCrushingTable());
        API.registerUsageHandler(new CraftingCrushingTable());

        API.hideItem(new ItemStack(ManagerBlocks.slabOreLimeDouble));
        API.hideItem(new ItemStack(ManagerBlocks.slabBurntLimeDouble));
        API.hideItem(new ItemStack(ManagerBlocks.slabBrickLimeDouble));
        API.hideItem(new ItemStack(ManagerBlocks.slabRoofTileDouble));
        API.hideItem(new ItemStack(ManagerBlocks.slabBurntBrickLimeDouble));
        API.hideItem(new ItemStack(ManagerBlocks.slabTileLimeDouble));
        API.hideItem(new ItemStack(ManagerBlocks.slabCobbleLimeDouble));
        API.hideItem(new ItemStack(ManagerBlocks.slabBurntCobbleLimeDouble));

        API.registerGuiOverlayHandler(GuiCrafter.class, new CrafterOverlayHandler(), "crafting");
    }

}
