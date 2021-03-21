package com.dncomponents.client.components.core.validation;

import com.dncomponents.client.components.core.events.validator.HasValidationHandlers;

public interface HasValidation<T> extends HasValidationHandlers<T> {
    boolean isValidEntry();

    void setValidator(Validator<T> validator);

    void validate(T value) throws ValidationException;
}
