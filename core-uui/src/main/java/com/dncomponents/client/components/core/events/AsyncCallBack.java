package com.dncomponents.client.components.core.events;

/**
 * @author nikolasavic
 */
public interface AsyncCallBack<T> {

    void onSuccess(T result);

    void onFailure(Throwable caught);
}
