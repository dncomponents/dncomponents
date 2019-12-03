package com.dncomponents.client.views.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnumLookUp<E> implements Serializable {
    public EnumLookUp() {
    }

    private Map<String, E> stringValueMap = new HashMap<>();

    public EnumLookUp(E[] values) {
        for (E value : values) {
            stringValueMap.put(value.toString(), value);
        }
    }

    public E getValue(String str) {
        return stringValueMap.get(str);
    }

    public List<String> toStringList() {
        return new ArrayList<>(stringValueMap.keySet());
    }
}