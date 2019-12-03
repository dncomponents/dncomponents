package com.dncomponents.client.components.core.events.selection;

import com.dncomponents.client.components.core.events.BaseEvent;
import com.dncomponents.client.views.IsElement;


public class SelectionEvent<T> extends BaseEvent {

    private T item;

    public SelectionEvent() {
        super(SelectionHandler.TYPE);
    }

    public SelectionEvent(T item) {
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
        SelectionEvent<T> event = new SelectionEvent<>(item);
        source.asElement().dispatchEvent(event.asEvent());
    }

}
