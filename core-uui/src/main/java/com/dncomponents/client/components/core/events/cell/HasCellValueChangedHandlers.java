package com.dncomponents.client.components.core.events.cell;

import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.HasHandlers;

public interface HasCellValueChangedHandlers<T> extends HasHandlers {

  HandlerRegistration addCellValueChangedHandler(CellValueChangedHandler<T> handler);
}
