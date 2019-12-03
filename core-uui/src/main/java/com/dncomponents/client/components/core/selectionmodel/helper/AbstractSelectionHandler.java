package com.dncomponents.client.components.core.selectionmodel.helper;

import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;

public class AbstractSelectionHandler<T> extends AbstractHandler implements HasSelectionHandlers<T> {
    @Override
    public HandlerRegistration addSelectionHandler(SelectionHandler<T> handler) {
        return ensureHandlers().addHandler(SelectionEvent.getType(), handler);
    }
}
