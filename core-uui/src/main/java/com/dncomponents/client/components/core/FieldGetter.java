package com.dncomponents.client.components.core;

/**
 * @author nikolasavic
 */
@Deprecated
public interface FieldGetter<T, M> {
    M getField(T t);
}