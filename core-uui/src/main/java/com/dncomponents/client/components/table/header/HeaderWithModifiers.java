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

package com.dncomponents.client.components.table.header;


import com.dncomponents.client.components.ColumnConfig;

/**
 * Base class for sorting, filtering and grouping columns.
 * It is uniquely determined by {@link #column} configuration and {@link #activeModifier}
 * Each column can have only one HeaderWithModifiers object for sorting and grouping and multiple objects for filtering.
 * All logic is done in {@link }
 * <p>
 * To filter, group or sort column add to cell holder {@link HeaderWithModifiers} with column
 * and {@link HeaderWithModifiers#activeModifier}
 * to remove it set {@link HeaderWithModifiers#activeModifier} to {@code null}
 */
public abstract class HeaderWithModifiers<T> {

    final ColumnConfig column;

    //Null activeModifier means remove it

    private T activeModifier;


    public HeaderWithModifiers(ColumnConfig column) {
        this.column = column;
    }

    public ColumnConfig getColumn() {
        return column;
    }


    public T getActiveModifier() {
        return activeModifier;
    }

    public void setActiveModifier(T activeModifier) {
        this.activeModifier = activeModifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HeaderWithModifiers)) return false;
        HeaderWithModifiers that = (HeaderWithModifiers) o;
        return getColumn().equals(that.getColumn());
    }

    @Override
    public int hashCode() {
        return getColumn().hashCode();
    }

    public abstract void copyFrom(HeaderWithModifiers<T> headerWithModifiers);
}
