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

import java.util.Comparator;


public class HeaderSorting extends HeaderWithModifiers<Comparator> implements HasDirection {

    private SortingDirection sortingDirection = SortingDirection.ASCENDING;

    public HeaderSorting(ColumnConfig column) {
        super(column);
        setActiveModifier(column.getComparator());
    }

    @Override
    public void copyFrom(HeaderWithModifiers<Comparator> headerWithModifiers) {
        setActiveModifier(headerWithModifiers.getActiveModifier());
        setSortingDirection(((HeaderSorting) headerWithModifiers).getSortingDirection());
    }

    public HeaderSorting(ColumnConfig column, SortingDirection sortingDirection) {
        this(column);
        setSortingDirection(sortingDirection);
    }

    @Override
    public SortingDirection getSortingDirection() {
        return sortingDirection;
    }

    @Override
    public void setSortingDirection(SortingDirection sortingDirection) {
        setActiveModifier(sortingDirection != null ? sortingDirection == SortingDirection.ASCENDING ?
                column.getComparator() : column.getComparator().reversed() : null);
        this.sortingDirection = sortingDirection;
    }

}
