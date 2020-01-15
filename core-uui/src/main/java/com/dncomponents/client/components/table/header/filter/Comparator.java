package com.dncomponents.client.components.table.header.filter;


import com.dncomponents.client.components.core.FieldGetter;

import java.util.function.BiPredicate;

/**
 * Tests {@link FieldGetter#getField(Object)} with
 * {@link FilterValue#getUserEnteredValue()}
 * <p>
 * o2 is user entered value and it is tested only when it's not null!
 * this logic is done in {@link PredicateComparator#test(Object)}
 * <p>
 * First argument of test method is result of FieldGetter of entity object.
 * Second parameter is userEntered object
 *
 * @author nikolasavic
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


