package com.dncomponents.client.components.core.events.row;

import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.HasHandlers;

public interface HasRowEditingStartedHandlers<T> extends HasHandlers {

  HandlerRegistration addRowEditingStartedHandler(RowEditingStartedHandler<T> handler);
}
