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
import com.dncomponents.client.components.table.header.filter.Comparator;
import com.dncomponents.client.components.table.header.filter.FilterUtil;

import java.util.function.Predicate;


public class HeaderFiltering extends HeaderWithModifiers<Predicate> {

    Object userEnteredValue;
    Comparator comparator;
    boolean or;


    public HeaderFiltering(ColumnConfig column) {
        super(column);
    }

    public HeaderFiltering(ColumnConfig column, Object userEnteredValue, Comparator comparator) {
        super(column);
        setUserEnteredValue(userEnteredValue, comparator);
    }

    @Override
    public void copyFrom(HeaderWithModifiers<Predicate> headerWithModifiers) {
        setActiveModifier(headerWithModifiers.getActiveModifier());
    }

    public void setUserEnteredValue(Object userEnteredValue, Comparator comparator) {
        this.userEnteredValue = userEnteredValue;
        this.comparator = comparator;
        if (userEnteredValue == null && !FilterUtil.isEmptyComparator(comparator))
            setActiveModifier(null);//this removes HeaderFiltering
        else if (comparator != null)
            setActiveModifier(o -> comparator.test(column.getFieldGetter().apply(o), userEnteredValue));
    }

    public Comparator getComparator() {
        return comparator;
    }

    public Object getUserEnteredValue() {
        return userEnteredValue;
    }

    public void setUserEnteredValue(Object userEnteredValue) {
        this.userEnteredValue = userEnteredValue;
    }

    public void setComparator(Comparator comparator) {
        this.comparator = comparator;
    }
    //TODO uncomment equals for multiple filters!


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HeaderFiltering)) return false;
        if (!super.equals(o)) return false;

        HeaderFiltering that = (HeaderFiltering) o;
        if (that.getActiveModifier() == null && this.getActiveModifier() == null)
            return true;
        if (that.getActiveModifier() == null || this.getActiveModifier() == null)
            return false;
        return that.getActiveModifier().equals(this.getActiveModifier());
    }

    //
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getActiveModifier() != null ? getActiveModifier().hashCode() : 0);
        return result;
    }

    public void setOr(boolean or) {
        this.or = or;
    }

    public boolean isOr() {
        return or;
    }
}
