package com.dncomponents.client.components.core.events.focus;

import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.HasHandlers;
import com.dncomponents.client.dom.handlers.OnFocusHandler;

public interface HasFocusHandlers extends HasHandlers {
    HandlerRegistration addFocusHandler(OnFocusHandler handler);
}
