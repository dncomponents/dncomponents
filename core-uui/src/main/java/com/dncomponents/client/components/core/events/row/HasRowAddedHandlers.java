package com.dncomponents.client.components.core.events.row;

import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.HasHandlers;

public interface HasRowAddedHandlers<T> extends HasHandlers {
    HandlerRegistration addRowAddedHandler(RowAddedHandler<T> handler);
}
