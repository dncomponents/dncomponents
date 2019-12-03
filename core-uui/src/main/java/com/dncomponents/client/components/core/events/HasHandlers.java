package com.dncomponents.client.components.core.events;

import elemental2.dom.CustomEvent;

public interface HasHandlers {
    void fireEvent(CustomEvent event);
}
