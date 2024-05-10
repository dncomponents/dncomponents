/*
 * Copyright 2024 dncomponents
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dncomponents.client.components.core;

import java.util.ArrayList;
import java.util.List;


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
