package com.dncomponents.client.components.core.events.cell;

import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.HasHandlers;

public interface HasCellEditingStartedHandlers<T> extends HasHandlers {

  HandlerRegistration addCellEditingStartedHandler(CellEditingStartedHandler<T> handler);
}
