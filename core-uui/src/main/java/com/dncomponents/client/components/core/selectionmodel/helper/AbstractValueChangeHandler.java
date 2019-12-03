package com.dncomponents.client.components.core.selectionmodel.helper;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;

public abstract class AbstractValueChangeHandler<T> extends AbstractHandler implements HasValue<T> {

    @Override
    public void setValue(T value) {
        setValue(value, false);
    }

    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<T> handler) {
        return ensureHandlers().addHandler(ValueChangeEvent.getType(), handler);
    }
}
