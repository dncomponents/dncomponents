package com.dncomponents.client.components.core.events.selection;

import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.HasHandlers;

public interface HasSelectionHandlers<T> extends HasHandlers {
    HandlerRegistration addSelectionHandler(SelectionHandler<T> handler);
}
