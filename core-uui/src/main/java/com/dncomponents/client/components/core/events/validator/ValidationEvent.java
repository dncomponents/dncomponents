package com.dncomponents.client.components.core.events.validator;

import com.dncomponents.client.components.core.events.BaseEvent;
import com.dncomponents.client.views.IsElement;

import java.util.List;


public class ValidationEvent<T> extends BaseEvent {

    private T value;
    private List<String> errors;

    public ValidationEvent() {
        super(ValidationHandler.TYPE);
    }

    public ValidationEvent(T value, List<String> errors) {
        this();
        this.value = value;
        this.errors = errors;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public List<String> getErrors() {
        return errors;
    }

    public static <T> void fire(IsElement source, T value, List<String> errors) {
        ValidationEvent<T> event = new ValidationEvent<>(value, errors);
        source.asElement().dispatchEvent(event.asEvent());
    }

    public String getFirstError() {
        if (errors != null && !errors.isEmpty())
            return errors.get(0);
        return null;
    }
}
