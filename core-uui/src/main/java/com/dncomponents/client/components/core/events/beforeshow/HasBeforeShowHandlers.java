package com.dncomponents.client.components.core.events.beforeshow;

import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.HasHandlers;

public interface HasBeforeShowHandlers<T> extends HasHandlers {
    HandlerRegistration addBeforeShowHandler(BeforeShowHandler<T> handler);
}