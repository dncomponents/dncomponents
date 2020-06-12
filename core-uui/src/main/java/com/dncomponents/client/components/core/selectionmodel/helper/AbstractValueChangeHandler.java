package com.dncomponents.client.components.core.selectionmodel.helper;

import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.value.HasValue;
import com.dncomponents.client.components.core.events.value.ValueChangeHandler;

public abstract class AbstractValueChangeHandler<T> extends AbstractHandler implements HasValue<T> {

    @Override
    public void setValue(T value) {
        setValue(value, false);
    }

    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<T> handler) {
        return handler.addTo(ensureHandlers());
    }
}
