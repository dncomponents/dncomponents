package com.dncomponents.client.components.core.events.row;

import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.HasHandlers;

public interface HasRowEditingStoppedHandlers<T> extends HasHandlers {
    HandlerRegistration addRowEditingStoppedEvent(RowEditingStoppedHandler<T> handler);
}
