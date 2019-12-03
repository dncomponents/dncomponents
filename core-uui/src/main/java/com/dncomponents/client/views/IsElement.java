package com.dncomponents.client.views;

import elemental2.dom.HTMLElement;

/**
 * @author nikolasavic
 */
public interface IsElement<E extends HTMLElement> extends IsNode {
    E asElement();
}