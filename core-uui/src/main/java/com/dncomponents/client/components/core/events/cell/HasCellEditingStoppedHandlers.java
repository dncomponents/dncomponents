package com.dncomponents.client.components.core.events.cell;

import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.HasHandlers;

public interface HasCellEditingStoppedHandlers<T> extends HasHandlers {
    HandlerRegistration addCellEditingStoppedEvent(CellEditingStoppedEvent<T> handler);
}
