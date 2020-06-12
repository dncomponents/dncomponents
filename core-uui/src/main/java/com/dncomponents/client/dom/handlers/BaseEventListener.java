package com.dncomponents.client.dom.handlers;

import com.dncomponents.client.components.core.events.HandlerRegistration;
import elemental2.dom.EventListener;
import elemental2.dom.EventTarget;

/**
 * @author nikolasavic
 */
public interface BaseEventListener extends EventListener {
    String getType();

    default void removeFrom(EventTarget element) {
        element.removeEventListener(getType(), this);
    }

    default HandlerRegistration addTo(EventTarget element) {
        element.addEventListener(getType(), this);
        return () -> removeFrom(element);
    }

}
