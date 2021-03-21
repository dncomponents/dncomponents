package com.dncomponents.client.components.core.events.form;

import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.HasHandlers;

public interface HasModelChangedHandlers<T> extends HasHandlers {
    HandlerRegistration addModelChangedHandler(ModelChangedHandler<T> handler);
}
