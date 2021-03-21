package com.dncomponents.client.components.core.events.row;

import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.HasHandlers;

public interface HasRowValueChangedHandlers<T> extends HasHandlers {

  HandlerRegistration addRowValueChangedHandler(RowValueChangedHandler<T> handler);
}
