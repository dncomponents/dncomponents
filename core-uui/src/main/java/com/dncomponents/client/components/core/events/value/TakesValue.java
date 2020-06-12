package com.dncomponents.client.components.core.events.value;

public interface TakesValue<V> {

    void setValue(V value);

    V getValue();
}
