package com.cout970.magneticraft.util;

import java.util.EnumMap;

public class DefaultEnumMap<K extends Enum<K>, V> extends EnumMap<K, V> {
    private V defaultValue;
    public DefaultEnumMap(Class<K> keyType, V defaultValue) {
        super(keyType);
        setDefaultValue(defaultValue);
    }

    public V getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(V defaultValue) {
        this.defaultValue = defaultValue;
    }

    public V getOrDefault(Object key) {
        return getOrDefault(key, defaultValue);
    }
}
