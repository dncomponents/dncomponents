package com.dncomponents.client.components.events;

import com.dncomponents.client.components.core.CellConfig;
import com.dncomponents.client.components.core.FieldGetter;

/**
 * Validates value that is obtained from {@link FieldGetter}.
 * <p>
 * It is part of {@link CellConfig} object.
 * <p>
 * <p>
 *
 * @author nikolasavic
 */
public interface CellValidator<T, M> {
    /**
     * @param t model object
     * @param m value that should be validated
     * @throws CellValidationException if there were a validation errors
     */
    void validate(M m, T t) throws CellValidationException;
}