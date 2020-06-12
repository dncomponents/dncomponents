package com.dncomponents.client.components.core.events.rowdata;

import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.HasHandlers;

public interface HasRowDataChangedHandlers<T> extends HasHandlers {
    HandlerRegistration addRowDataChangedHandler(RowDataChangedHandler handler);
}
