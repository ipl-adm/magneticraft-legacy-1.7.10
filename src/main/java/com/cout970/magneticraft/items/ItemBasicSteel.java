package com.cout970.magneticraft.items;

import com.cout970.magneticraft.api.steel.ISteelAttribute;
import com.cout970.magneticraft.api.steel.ISteelItem;
import com.cout970.magneticraft.util.NBTHelper;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Basic implementation of ISteelItem
 */
public class ItemBasicSteel extends ItemBasic implements ISteelItem {

    public ItemBasicSteel(String unlocalizedName, String texture) {
        super(unlocalizedName, texture);
    }


    @Override
    public int getValue(ItemStack stack, ISteelAttribute attribute) {
        Map<ISteelAttribute, Integer> map = NBTHelper.getSteelAttributeMap(stack);
        if (map.containsKey(attribute)) {
            return map.get(attribute);
        } else {
            return 0;
        }
    }

    @Override
    public void setValue(ItemStack stack, ISteelAttribute attribute, int value) {
        Map<ISteelAttribute, Integer> map = NBTHelper.getSteelAttributeMap(stack);
        map.put(attribute, value);
        NBTHelper.setAttributeMap(map, stack);
    }

    @Override
    public List<ISteelAttribute> getAllApplicableAttributes(ItemStack stack) {
        return new ArrayList<>(NBTHelper.getSteelAttributeMap(stack).keySet());
    }

    @Override
    public Map<ISteelAttribute, Integer> getAttributeMap(ItemStack stack) {
        return NBTHelper.getSteelAttributeMap(stack);
    }


    @Override
    public boolean disableTooltip(ItemStack stack) {
        return false;
    }
}
