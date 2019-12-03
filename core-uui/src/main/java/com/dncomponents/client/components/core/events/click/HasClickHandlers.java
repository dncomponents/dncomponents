package com.dncomponents.client.components.core.events.click;

import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.components.core.events.HasHandlers;
import com.google.gwt.event.shared.HandlerRegistration;

public interface HasClickHandlers extends HasHandlers {
    HandlerRegistration addClickHandler(ClickHandler handler);
}
