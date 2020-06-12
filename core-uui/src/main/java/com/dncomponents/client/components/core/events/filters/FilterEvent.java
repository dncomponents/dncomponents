package com.dncomponents.client.components.core.events.filters;

import com.dncomponents.client.components.core.events.BaseEvent;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.Element;

import java.util.function.Predicate;

public class FilterEvent<T> extends BaseEvent {

    Predicate<T> filter;

    public FilterEvent() {
        super(FilterHandler.TYPE);
    }


    public FilterEvent(Predicate<T> filter) {
        this();
        this.filter = filter;
    }

    public Predicate<T> getFilter() {
        return filter;
    }

    public void setFilter(Predicate<T> filter) {
        this.filter = filter;
    }

    public static <T> void fire(Element source, Predicate<T> filter) {
        FilterEvent<T> event = new FilterEvent<>(filter);
        source.dispatchEvent(event.asEvent());
    }

    public static <T> void fire(IsElement source, Predicate<T> filter) {
        fire(source.asElement(), filter);
    }

}