package com.dncomponents.client.components.core.events.click;

import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.components.core.events.HasHandlers;

public interface HasClickHandlers extends HasHandlers {
    HandlerRegistration addClickHandler(ClickHandler handler);
}
