package com.dncomponents.client.components.core.validation;

public interface Validator<T> {
    void validate(final T value) throws ValidationException;
}
