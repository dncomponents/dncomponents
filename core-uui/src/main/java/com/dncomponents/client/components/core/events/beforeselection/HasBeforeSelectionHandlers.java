package com.dncomponents.client.components.core.events.beforeselection;

import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.HasHandlers;

public interface HasBeforeSelectionHandlers<T> extends HasHandlers {
    HandlerRegistration addBeforeSelectionHandler(BeforeSelectionHandler<T> handler);
}
