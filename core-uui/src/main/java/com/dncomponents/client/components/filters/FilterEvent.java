package com.dncomponents.client.components.filters;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

import java.util.function.Predicate;

/**
 * Created by nikolasavic
 */
public class FilterEvent extends GwtEvent<FilterEvent.FilterHandler> {


    /**
     * Handler type.
     */
    private static Type<FilterHandler> TYPE;

    Predicate filter;

    public FilterEvent(Predicate filter) {
        this.filter = filter;
    }

    public FilterEvent() {
    }

    /**
     * Gets the type associated with this event.
     *
     * @return returns the handler type
     */
    public static Type<FilterHandler> getType() {
        if (TYPE == null) {
            TYPE = new Type<>();
        }
        return TYPE;
    }

    @Override
    public Type<FilterHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(FilterHandler handler) {
        handler.onFilterEvent(this);
    }

    public Predicate getFilter() {
        return filter;
    }

    public void setFilter(Predicate filter) {
        this.filter = filter;
    }

    public interface FilterHandler extends EventHandler {
        void onFilterEvent(FilterEvent event);
    }

    public interface HasFilterHandler {
        HandlerRegistration addFilterHandler(FilterHandler handler);
    }
}
