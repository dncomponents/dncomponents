package com.dncomponents.client.components.core.validation;

/**
 * @author nikolasavic
 */
public interface HasValidationModel<T, M> extends BaseHasValidation<M> {
    void setValidator(CellValidator<T, M> validator);

    CellValidator<T, M> getCellValidator();
}
