package com.dncomponents.client.components.core.events.row;

import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.HasHandlers;

public interface HasRowRemovedHandlers<T> extends HasHandlers {
    HandlerRegistration addRowRemovedHandler(RowRemovedHandler<T> handler);
}
