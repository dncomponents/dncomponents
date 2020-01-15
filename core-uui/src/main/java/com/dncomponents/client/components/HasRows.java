package com.dncomponents.client.components;

import com.dncomponents.client.components.events.RowCountChangedEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.view.client.RowCountChangeEvent;

/**
 * Created by nikolasavic
 */
public interface HasRows<T> extends HasRowsData<T>, RowCountChangedEvent.HasRowCountHandler {


    int getRowSize();

    /**
     * Add a {@link RowCountChangeEvent.Handler}.
     *
     * @param handler the handler
     * @return a {@link HandlerRegistration} to remove the handler
     */
    HandlerRegistration addRowCountChangedHandler(RowCountChangedEvent.Handler handler);
}