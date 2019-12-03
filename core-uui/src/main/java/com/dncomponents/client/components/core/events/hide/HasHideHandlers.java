package com.dncomponents.client.components.core.events.hide;

import com.dncomponents.client.components.core.events.HasHandlers;
import com.google.gwt.event.shared.HandlerRegistration;

public interface HasHideHandlers<T> extends HasHandlers {
    HandlerRegistration addHideHandler(HideHandler<T> handler);
}
