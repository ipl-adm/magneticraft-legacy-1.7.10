package com.cout970.magneticraft.api.steel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A Registry that holds a list of all ISteelAttribute's
 *
 * @author minecreatr
 */
public class AttributeRegistry {

    private static List<ISteelAttribute> unbakedList = new ArrayList<>();
    private static Map<Integer, ISteelAttribute> attributeMap;
    private static Map<ISteelAttribute, Integer> reverseAttributeMap;

    static {

    }

    public static void registerAttribute(ISteelAttribute attribute) {
        unbakedList.add(attribute);
    }

    public static void bake() {
        for (int i = 0; i < unbakedList.size(); i++) {
            ISteelAttribute attribute = unbakedList.get(i);
            attributeMap.put(i, attribute);
            reverseAttributeMap.put(attribute, i);
        }
    }

    public static ISteelAttribute getAttribute(int id) {
        return attributeMap.get(id);
    }

    public static int getAttributeID(ISteelAttribute attribute) {
        return reverseAttributeMap.get(attribute);
    }
}
