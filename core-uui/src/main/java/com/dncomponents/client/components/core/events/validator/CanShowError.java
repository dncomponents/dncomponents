package com.dncomponents.client.components.core.events.validator;

/**
 * @author nikolasavic
 */
public interface CanShowError {
    void showErrorMessage(String error);

    void setErrorStyle(boolean b);
}
