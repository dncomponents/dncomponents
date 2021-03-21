package com.dncomponents.client.components.core.validation;

/**
 * @author nikolasavic
 */
public abstract class ModelValidator<T,M> implements Validator<M> {
    protected  T model;

    public void setModel(T model) {
        this.model = model;
    }
}
