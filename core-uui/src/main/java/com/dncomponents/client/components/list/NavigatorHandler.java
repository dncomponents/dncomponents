package com.dncomponents.client.components.list;

import elemental2.dom.KeyboardEvent;

/**
 * @author nikolasavic
 */
public interface NavigatorHandler {
    void onLeftArrowMove(KeyboardEvent keyboardEvent);

    void onRightArrowMove(KeyboardEvent keyboardEvent);

    void onUpArrowMove(KeyboardEvent keyboardEvent);

    void onDownArrowMove(KeyboardEvent keyboardEvent);
}