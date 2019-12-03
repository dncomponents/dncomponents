package com.dncomponents.client.components.textbox;

public interface Validator<T> {
    String validate(final T value);
}
