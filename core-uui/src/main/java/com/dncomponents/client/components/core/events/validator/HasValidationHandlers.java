package com.dncomponents.client.components.core.events.validator;

import com.dncomponents.client.components.core.events.HasHandlers;
import com.google.gwt.event.shared.HandlerRegistration;

public interface HasValidationHandlers<T> extends HasHandlers {
    HandlerRegistration addValidationHandler(ValidationHandler<T> handler);
}
