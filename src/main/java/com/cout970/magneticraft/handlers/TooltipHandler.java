package com.cout970.magneticraft.handlers;

import com.cout970.magneticraft.api.steel.ISteelAttribute;
import com.cout970.magneticraft.api.steel.ISteelItem;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Handler for steel tooltip
 */
public class TooltipHandler {

    @SubscribeEvent
    public void onTooltip(ItemTooltipEvent event) {
        if (event.itemStack.getItem() instanceof ISteelItem) {
            ItemStack stack = event.itemStack;
            ISteelItem steel = (ISteelItem) stack.getItem();
            Map<ISteelAttribute, Integer> map = steel.getAttributeMap(stack);
            event.toolTip.addAll(map.keySet().stream().map(attribute -> attribute.getDisplayText(map.get(attribute))).collect(Collectors.toList()));
        }
    }
}
