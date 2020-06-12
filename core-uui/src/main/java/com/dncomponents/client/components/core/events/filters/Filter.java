package com.dncomponents.client.components.core.events.filters;

import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.dom.DomUtil;
import elemental2.dom.CustomEvent;
import elemental2.dom.HTMLElement;

import java.util.function.Predicate;

/**
 * Created by nikolasavic
 */
public abstract class Filter<T> implements Predicate<T>, HasFilterHandlers<T> {

    private HTMLElement bus;

    public void fireEvent(CustomEvent event) {
        ensureHandlers().dispatchEvent(event);
    }

    public void fireFilterChange() {
        fireEvent((new FilterEvent(this)).asEvent());
    }

    @Override
    public HandlerRegistration addFilterHandler(FilterHandler<T> handler) {
        return handler.addTo(ensureHandlers());
    }

    protected HTMLElement ensureHandlers() {
        if (bus == null)
            bus = DomUtil.createDiv();
        return bus;
    }
}
