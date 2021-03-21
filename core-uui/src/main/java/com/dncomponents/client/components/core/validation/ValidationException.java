package com.dncomponents.client.components.core.validation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikolasavic
 */
public class ValidationException extends Exception {

    List<String> validationMessages = new ArrayList<String>();

    public ValidationException() {
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(List<String> validationMessages) {
        this(!validationMessages.isEmpty() ? validationMessages.get(0) : "");
        this.validationMessages = validationMessages;
    }

    public List<String> getValidationMessages() {
        return validationMessages;
    }
}
