package com.dncomponents.client.components.core.events.show;

import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.HasHandlers;

public interface HasShowHandlers<T> extends HasHandlers {
    HandlerRegistration addShowHandler(ShowHandler<T> handler);
}
