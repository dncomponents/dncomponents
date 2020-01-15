package com.dncomponents.client.components.list;

import com.dncomponents.client.dom.handlers.KeyDownHandler;
import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author nikolasavic
 */
public interface HasNavigationHandler {
    HandlerRegistration addKeyDownHandler(KeyDownHandler keyDownHandler);
}
