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

package com.dncomponents.client.components.table.header.filter;


import com.dncomponents.client.components.core.FieldGetter;

import java.util.function.BiPredicate;

/**
 * Tests {@link FieldGetter#getField(Object)} with
 * <p>
 * o2 is user entered value and it is tested only when it's not null!
 * <p>
 * First argument of test method is result of FieldGetter of entity object.
 * Second parameter is userEntered object
 */
public abstract class Comparator<T, U> implements BiPredicate<T, U> {
    private final String comparatorName;

    public Comparator(String comparatorName) {
        this.comparatorName = comparatorName;
    }

    public String getComparatorName() {
        return comparatorName;
    }

    @Override
    public String toString() {
        return getComparatorName();
    }
}


