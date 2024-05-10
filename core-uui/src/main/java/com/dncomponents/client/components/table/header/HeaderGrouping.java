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


public class HeaderGrouping extends HeaderWithModifiers<ColumnConfig> implements HasDirection {

    private SortingDirection sortingDirection;

    public HeaderGrouping(ColumnConfig column) {
        this(column, SortingDirection.ASCENDING);
    }

    @Override
    public void copyFrom(HeaderWithModifiers<ColumnConfig> headerWithModifiers) {
        setActiveModifier(headerWithModifiers.getActiveModifier());
        setSortingDirection(((HeaderGrouping) headerWithModifiers).getSortingDirection());
    }

    public HeaderGrouping(ColumnConfig column, SortingDirection sortingDirection) {
        super(column);
        setSortingDirection(sortingDirection);
    }

    public HeaderGrouping(ColumnConfig column, Boolean sortingDirection) {
        super(column);
        setSortingDirection((sortingDirection == null) ? null : sortingDirection ?
                SortingDirection.ASCENDING : SortingDirection.DESCENDING);
    }

    @Override
    public SortingDirection getSortingDirection() {
        return sortingDirection;
    }

    @Override
    public void setSortingDirection(SortingDirection sortingDirection) {
        setActiveModifier(sortingDirection != null ? column : null);
        this.sortingDirection = sortingDirection;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        if (!super.equals(o)) return false;
//        HeaderGrouping that = (HeaderGrouping) o;
//        return sortingDirection == that.sortingDirection;
//    }
//
//    @Override
//    public int hashCode() {
//        int result = super.hashCode();
//        result = 31 * result + sortingDirection.hashCode();
//        return result;
//    }
}
