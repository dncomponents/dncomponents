package com.dncomponents.client.components.core.events.beforeselection;

import com.dncomponents.client.components.core.events.BaseEvent;
import com.dncomponents.client.views.IsElement;

public class BeforeSelectionEvent<T> extends BaseEvent {

    private T item;

    public BeforeSelectionEvent() {
        super(BeforeSelectionHandler.TYPE);
    }

    public BeforeSelectionEvent(T item) {
        this();
        this.item = item;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    public static <T> void fire(IsElement source, T item) {
        BeforeSelectionEvent<T> event = new BeforeSelectionEvent<>(item);
        source.asElement().dispatchEvent(event.asEvent());
    }

}
