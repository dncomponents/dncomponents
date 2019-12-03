package com.dncomponents.client.components.core;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nikolasavic
 */
public class KeyValue {

    String key;
    String value;
    //null is default keyValue
    String rule;
    List<KeyValue> items = new ArrayList<>();

    public KeyValue() {
    }

    public KeyValue(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public KeyValue(String key, String value, String rule) {
        this.key = key;
        this.value = value;
        this.rule = rule;
    }

    KeyValue getRootKeyValue() {
        if (!items.isEmpty()) {
            for (KeyValue item : items) {
                if (item.rule == null)
                    return item;
            }
        }
        return this;
    }

    public List<KeyValue> getAllItems() {
        final ArrayList<KeyValue> keyValues = new ArrayList<>(items);
        keyValues.add(this);
        return keyValues;
    }
}