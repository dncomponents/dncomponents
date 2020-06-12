package com.dncomponents.client.components.core.events.hide;

import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.HasHandlers;

public interface HasHideHandlers<T> extends HasHandlers {
    HandlerRegistration addHideHandler(HideHandler<T> handler);
}
