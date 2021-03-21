package com.dncomponents.client.components.core.validation.validators;

import com.dncomponents.client.components.core.validation.ValidationException;
import com.dncomponents.client.components.core.validation.Validator;
import elemental2.dom.DomGlobal;

/**
 * @author nikolasavic
 */
public class EmailValidator implements Validator<String> {
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    public void validate(String value) throws ValidationException {
        boolean matches = false;
        try {
            matches = value.matches(EMAIL_PATTERN);
        } catch (Exception ex) {
            DomGlobal.console.log(ex.getMessage());
            matches = false;
        }
        if (!matches)
            throw new ValidationException("Email is not valid");
    }

}
