package com.dncomponents.client.components.core.validation;

/**
 * @author nikolasavic
 */
public interface HasModel<P> {
    void setValueToModel();

    void setValueFromModel();

    P getModel();

    void setModel(P model);
}
