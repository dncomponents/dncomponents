package com.dncomponents.client.components.core.validation.validators;

import com.dncomponents.client.components.core.validation.ValidationException;
import com.dncomponents.client.components.core.validation.Validator;

/**
 * @author nikolasavic
 */
public class LessThanValidator<T extends Number & Comparable> implements Validator<T> {
    private T numb;
    private String message;

    public LessThanValidator(T numb) {
        this.numb = numb;
    }

    public LessThanValidator(T numb, String message) {
        this.numb = numb;
        this.message = message;
    }

    @Override
    public void validate(T value) throws ValidationException {
        if (value.compareTo(numb) != -1 || value.compareTo(numb) == 0)
            throw new ValidationException(message != null ? message : "The value has to be less than " + numb);
    }

    public static void main(String[] args) {
        LessThanValidator<Integer> lb = new LessThanValidator<>(22, "less than " + 22);
        try {
            lb.validate(2);
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }
}
