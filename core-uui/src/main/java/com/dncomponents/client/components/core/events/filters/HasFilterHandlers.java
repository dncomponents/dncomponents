package com.dncomponents.client.components.core.events.filters;

import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.HasHandlers;

public interface HasFilterHandlers<T> extends HasHandlers {
    HandlerRegistration addFilterHandler(FilterHandler<T> handler);
}