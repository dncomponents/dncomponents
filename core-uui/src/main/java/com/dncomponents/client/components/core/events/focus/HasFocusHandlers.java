package com.dncomponents.client.components.core.events.focus;

import com.dncomponents.client.components.core.events.HasHandlers;
import com.dncomponents.client.dom.handlers.OnFocusHandler;
import com.google.gwt.event.shared.HandlerRegistration;

public interface HasFocusHandlers extends HasHandlers {
    HandlerRegistration addFocusHandler(OnFocusHandler handler);
}
