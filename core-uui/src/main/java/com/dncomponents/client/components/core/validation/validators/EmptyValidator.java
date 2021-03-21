package com.dncomponents.client.components.core.validation.validators;

import com.dncomponents.client.components.core.validation.ValidationException;
import com.dncomponents.client.components.core.validation.Validator;

import java.util.Collection;

/**
 * @author nikolasavic
 */
public class EmptyValidator<T> implements Validator<T> {
    @Override
    public void validate(T value) throws ValidationException {
        if (value == null) {
            throw new ValidationException("Can't be empty!");
        }
        if (value instanceof Collection) {
            if (((Collection) value).isEmpty()) {
                throw new ValidationException("Can't be empty!");
            }
        }
        if (value instanceof String) {
            if (value.toString().isEmpty()) {
                throw new ValidationException("Can't be empty!");
            }
        }
    }
}
