package com.dncomponents.client.components.core.validation.validators;

import com.dncomponents.client.components.core.validation.Validator;

/**
 * @author nikolasavic
 */
public abstract class AbstractValidator<T> implements Validator<T> {
    protected String message;

    public AbstractValidator() {
    }

    public AbstractValidator(String message) {
        this.message = message;
    }
}
