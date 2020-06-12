package com.dncomponents.client.components.core.events.value;

import com.dncomponents.client.components.core.events.BaseEvent;
import com.dncomponents.client.views.IsElement;

import java.util.Objects;

public class ValueChangeEvent<T> extends BaseEvent {

    private T value;

    public ValueChangeEvent() {
        super(ValueChangeHandler.TYPE);
    }

    public ValueChangeEvent(T value) {
        this();
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public static <T> void fire(IsElement source, T item) {
        ValueChangeEvent<T> event = new ValueChangeEvent<>(item);
        source.asElement().dispatchEvent(event.asEvent());
    }

    public static <T> void fire(HasValueChangeHandlers<T> source, T value) {
        ValueChangeEvent<T> event = new ValueChangeEvent<T>(value);
        source.fireEvent(event.asEvent());
    }

    public static <T> void fireIfNotEqual(HasValueChangeHandlers<T> source, T oldValue, T newValue) {
        if (!Objects.equals(oldValue, newValue))
            fire(source, newValue);
    }

}
