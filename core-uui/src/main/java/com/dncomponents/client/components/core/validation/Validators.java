package com.dncomponents.client.components.core.validation;

import elemental2.dom.DomGlobal;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nikolasavic
 */
public class Validators<T> implements Validator<T> {

    List<Validator<T>> validators = new ArrayList<>();
    List<Validator<T>> validatorsToBreak = new ArrayList<>();

    @Override
    public void validate(T value) throws ValidationException {
        List<String> messages = new ArrayList<String>();
        for (Validator<T> validator : this.validators) {
            try {
                validator.validate(value);
            } catch (ValidationException ex) {
                messages.add(ex.getMessage());
                if (validatorsToBreak.contains(validator))
                    break;
            }
        }
        if (!messages.isEmpty()) {
            throw new ValidationException(messages);
        }
    }

    public Validators<T> add(Validator<T> validator) {
        this.validators.add(validator);
        return this;
    }

    public Validators<T> add(Validator<T> validator, boolean break1) {
        this.validators.add(validator);
        if (break1)
            this.validatorsToBreak.add(validator);
        return this;
    }

    public List<Validator<T>> getValidators() {
        return validators;
    }
}

class Test {
    void test() {
        Validator validator = new Validators().add(new ModelValidator<String, Integer>() {
            @Override
            public void validate(Integer value) throws ValidationException {

            }
        });
    }
}
