package com.dncomponents.client.components.table.header;

import java.util.function.Predicate;
/**
 * @author nikolasavic
 */
class PredicateHc<T> implements Predicate<T> {
    Predicate<T> predicate;

    public PredicateHc(Predicate<T> predicate) {
        this.predicate = predicate;
    }

    @Override
    public boolean test(T t) {
        return predicate.test(t);
    }
}
