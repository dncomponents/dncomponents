package com.dncomponents.client.components.core.events.show;

import com.dncomponents.client.components.core.events.HasHandlers;
import com.google.gwt.event.shared.HandlerRegistration;

public interface HasShowHandlers<T> extends HasHandlers {
    HandlerRegistration addShowHandler(ShowHandler<T> handler);
}
