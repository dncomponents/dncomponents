package com.dncomponents.client.components.core.selection;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * A widget that implements this interface is a public source of
 * {@link SelectionChangedEvent} events.
 *
 * @param <M> the model type
 */
public interface HasSelectionChangedHandlers<M> {

    /**
     * Adds a {@link SelectionChangedHandler} handler for
     * {@link SelectionChangedEvent} events.
     *
     * @param handler the handler
     * @return the registration for the event
     */
    HandlerRegistration addSelectionChangedHandler(SelectionChangedHandler<M> handler);
}