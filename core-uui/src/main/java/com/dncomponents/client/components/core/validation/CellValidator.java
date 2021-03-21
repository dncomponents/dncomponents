package com.dncomponents.client.components.core.validation;

import com.dncomponents.client.components.core.CellConfig;
import com.dncomponents.client.components.core.FieldGetter;

/**
 * Validates value that is obtained from {@link FieldGetter}.
 * <p>
 * It is part of {@link CellConfig} object.
 *
 * @author nikolasavic
 */
public interface CellValidator<T, M> {
    /**
     * @param m value that should be validated
     * @param t model object
     * @throws ValidationException if there were a validation errors
     */
    void validate(M m, T t) throws ValidationException;
}
