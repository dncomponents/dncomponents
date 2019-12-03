package com.dncomponents.client.components.core.events.selection;

import com.dncomponents.client.components.core.events.HasHandlers;
import com.google.gwt.event.shared.HandlerRegistration;

public interface HasSelectionHandlers<T> extends HasHandlers {
    HandlerRegistration addSelectionHandler(SelectionHandler<T> handler);
}
