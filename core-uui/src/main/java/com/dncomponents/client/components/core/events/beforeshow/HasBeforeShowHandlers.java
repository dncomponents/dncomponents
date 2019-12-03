package com.dncomponents.client.components.core.events.beforeshow;

import com.dncomponents.client.components.core.events.HasHandlers;
import com.google.gwt.event.shared.HandlerRegistration;

public interface HasBeforeShowHandlers<T> extends HasHandlers {
    HandlerRegistration addBeforeShowHandler(BeforeShowHandler<T> handler);
}