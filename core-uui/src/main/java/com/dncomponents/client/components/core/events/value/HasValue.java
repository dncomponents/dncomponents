package com.dncomponents.client.components.core.events.value;

/**
 * @author nikolasavic
 */
public interface HasValue<T> extends TakesValue<T>, HasValueChangeHandlers<T> {

    T getValue();

    void setValue(T value);

    void setValue(T value, boolean fireEvents);
}
