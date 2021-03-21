package com.dncomponents.client.components.core;

import elemental2.dom.HTMLElement;

/**
 * @author nikolasavic
 */
public interface ValueRenderer<M> {
    void setValue(M value, HTMLElement valuePanel);
}
