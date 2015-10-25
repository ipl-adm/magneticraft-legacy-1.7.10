package com.cout970.magneticraft.util;

import com.cout970.magneticraft.api.steel.AttributeRegistry;
import com.cout970.magneticraft.api.steel.ISteelAttribute;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.HashMap;
import java.util.Map;

/**
 * An NBT Utility class
 *
 * @author minecreatr
 */
public class NBTHelper {

    /**
     * NBT Number Constants
     */
    public static final int END = 0;
    public static final int BYTE = 1;
    public static final int SHORT = 2;
    public static final int INT = 3;
    public static final int LONG = 4;
    public static final int FLOAT = 5;
    public static final int DOUBLE = 6;
    public static final int BYTE_ARRAY = 7;
    public static final int STRING = 8;
    public static final int LIST = 9;
    public static final int COMPOUND = 10;
    public static final int INT_ARRAY = 11;

    /**
     * NBT tag constants
     */
    public static final String STEEL_ATTRIBUTES = "steelAttributes";

    /**
     * Gets the NBTTagList for steel attributes
     *
     * @param stack The ItemStack
     * @return The NBTTagList of steel attributes
     */
    public static NBTTagList getSteelAttributes(ItemStack stack) {
        return stack.getTagCompound().getTagList(STEEL_ATTRIBUTES, COMPOUND);
    }

    /**
     * Gets the map of steel attributes to integer values on an item
     *
     * @param stack The Item
     * @return The Map of attributes
     */
    public static Map<ISteelAttribute, Integer> getSteelAttributeMap(ItemStack stack) {
        NBTTagList list = getSteelAttributes(stack);
        Map<ISteelAttribute, Integer> map = new HashMap<>();
        for (int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound compound = list.getCompoundTagAt(i);
            int id = compound.getInteger("id");
            int value = compound.getInteger("value");
            ISteelAttribute attribute = AttributeRegistry.getAttribute(id);
            map.put(attribute, value);
        }
        return map;
    }

    /**
     * Sets the attribute map to the specified ItemStack
     *
     * @param map   The Attribute map
     * @param stack The ItemStack
     */
    public static void setAttributeMap(Map<ISteelAttribute, Integer> map, ItemStack stack) {
        NBTTagList list = new NBTTagList();
        for (ISteelAttribute attribute : map.keySet()) {
            NBTTagCompound compound = new NBTTagCompound();
            compound.setInteger("id", AttributeRegistry.getAttributeID(attribute));
            compound.setInteger("value", map.get(attribute));
            list.appendTag(compound);
        }
        stack.getTagCompound().setTag(STEEL_ATTRIBUTES, list);
    }
}
