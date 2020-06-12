package com.dncomponents.client.components.core.events.cell;

import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.HasHandlers;

public interface HasCellEditHandlers<T> extends HasHandlers {

  HandlerRegistration addCellEditHandler(CellEditHandler<T> handler);
}
