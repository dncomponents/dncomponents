package com.dncomponents.client.components.core.events.validator;

import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.HasHandlers;

public interface HasValidationHandlers<T> extends HasHandlers {
    HandlerRegistration addValidationHandler(ValidationHandler<T> handler);
}
