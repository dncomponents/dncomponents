package com.dncomponents.client.components.core.selection;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler class for {@link SelectionChangedEvent} events.
 */
public interface SelectionChangedHandler<M> extends EventHandler {

    /**
     * Called after a widget's selections are changed.
     */
    void onSelectionChanged(SelectionChangedEvent<M> event);
}
