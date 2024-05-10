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

package com.dncomponents.client.dom;

import java.util.*;

public class MultiMap<K, V> {
    private final Map<K, Set<V>> multimap = new HashMap<>();

    public void put(K key, V value) {
        this.multimap.computeIfAbsent(key, k -> new HashSet<>()).add(value);
    }

    public Set<V> get(K key) {
        return this.multimap.getOrDefault(key, Collections.emptySet());
    }

    public void remove(K key, V value) {
        this.multimap.computeIfPresent(key,
                (k, set) -> set.remove(value) && set.isEmpty() ? null : set);
    }

    public boolean contains(K key, V value) {
        return this.multimap.getOrDefault(key, Collections.emptySet()).contains(value);
    }

    public int size() {
        return this.multimap.values().stream().mapToInt(Set::size).sum();
    }

    public Set<V> values() {
        return (Set<V>) multimap.values().stream().flatMap(Set::stream);
    }

    public Set<Map.Entry<K, Set<V>>> entrySet() {
        return multimap.entrySet();
    }
}
