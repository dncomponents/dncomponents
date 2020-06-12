package com.dncomponents.client.components.core.events.value;

import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.HasHandlers;

public interface HasValueChangeHandlers<T> extends HasHandlers {
    HandlerRegistration addValueChangeHandler(ValueChangeHandler<T> handler);
}
