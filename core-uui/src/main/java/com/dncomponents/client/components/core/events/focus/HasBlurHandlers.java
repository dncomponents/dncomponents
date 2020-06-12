package com.dncomponents.client.components.core.events.focus;

import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.dom.handlers.OnBlurHandler;
import com.dncomponents.client.components.core.events.HasHandlers;

public interface HasBlurHandlers extends HasHandlers {
    HandlerRegistration addBlurHandler(OnBlurHandler handler);
}
