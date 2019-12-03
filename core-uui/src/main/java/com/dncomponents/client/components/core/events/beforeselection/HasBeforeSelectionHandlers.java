package com.dncomponents.client.components.core.events.beforeselection;

import com.dncomponents.client.components.core.events.HasHandlers;
import com.google.gwt.event.shared.HandlerRegistration;

public interface HasBeforeSelectionHandlers<T> extends HasHandlers {
    HandlerRegistration addBeforeSelectionHandler(BeforeSelectionHandler<T> handler);
}
