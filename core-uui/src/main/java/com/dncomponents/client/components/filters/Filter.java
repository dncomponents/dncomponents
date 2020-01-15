package com.dncomponents.client.components.filters;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;

import java.util.function.Predicate;

/**
 * Created by nikolasavic
 */
public abstract class Filter<T> implements FilterEvent.HasFilterHandler, Predicate<T> {


    private HandlerManager handlerManager;

    protected HandlerManager ensureHandlers() {
        if (handlerManager == null) {
            handlerManager = new HandlerManager(this);
        }
        return handlerManager;
    }

    public void fireEvent(GwtEvent<?> event) {
        if (handlerManager != null) {
            handlerManager.fireEvent(event);
        }
    }


    public void fireFilterChange() {
        fireEvent(new FilterEvent(this));
    }

    @Override
    public HandlerRegistration addFilterHandler(FilterEvent.FilterHandler handler) {
        return ensureHandlers().addHandler(FilterEvent.getType(), handler);
    }

}
