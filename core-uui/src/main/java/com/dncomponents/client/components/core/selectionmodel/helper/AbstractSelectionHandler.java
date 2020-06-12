package com.dncomponents.client.components.core.selectionmodel.helper;


import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.selection.HasSelectionHandlers;
import com.dncomponents.client.components.core.events.selection.SelectionHandler;

public class AbstractSelectionHandler<T> extends AbstractHandler implements HasSelectionHandlers<T> {

    @Override
    public HandlerRegistration addSelectionHandler(SelectionHandler<T> handler) {
        return handler.addTo(ensureHandlers());
    }
}
