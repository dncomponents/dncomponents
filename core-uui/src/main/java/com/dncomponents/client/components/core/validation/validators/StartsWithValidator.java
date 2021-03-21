package com.dncomponents.client.components.core.validation.validators;

import com.dncomponents.client.components.core.validation.ValidationException;
import com.dncomponents.client.components.core.validation.Validator;

/**
 * @author nikolasavic
 */
public class StartsWithValidator implements Validator<String> {
    private String str;
    private String message;

    public StartsWithValidator(String str) {
        this.str = str;
    }

    public StartsWithValidator(String str, String message) {
        this.str = str;
        this.message = message;
    }

    @Override
    public void validate(String value) throws ValidationException {
        if (value == null || !value.startsWith(this.str))
            throw new ValidationException(message != null ? message : "The value should starts with " + this.str);
    }
}
