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
