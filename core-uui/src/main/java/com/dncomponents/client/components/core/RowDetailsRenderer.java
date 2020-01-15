package com.dncomponents.client.components.core;

import elemental2.dom.HTMLElement;

/**
 * @author nikolasavic
 */
public interface RowDetailsRenderer<T> {
    void render(T t, HTMLElement valuePanel);
}