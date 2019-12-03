package com.dncomponents.client.components.core.events.beforeshow;

import com.dncomponents.client.components.core.events.BaseEvent;
import com.dncomponents.client.views.IsElement;

public class BeforeShowEvent<T> extends BaseEvent {

    private T item;

    public BeforeShowEvent() {
        super(BeforeShowHandler.TYPE);
    }

    public BeforeShowEvent(T item) {
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
        BeforeShowEvent<T> event = new BeforeShowEvent<>(item);
        source.asElement().dispatchEvent(event.asEvent());
    }

}
