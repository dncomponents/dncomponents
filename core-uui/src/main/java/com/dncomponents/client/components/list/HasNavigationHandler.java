package com.dncomponents.client.components.list;

import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.dom.handlers.KeyDownHandler;

/**
 * @author nikolasavic
 */
public interface HasNavigationHandler {
    HandlerRegistration addKeyDownHandler(KeyDownHandler keyDownHandler);
}
