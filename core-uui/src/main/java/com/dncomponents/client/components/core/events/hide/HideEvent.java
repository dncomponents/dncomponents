package com.dncomponents.client.components.core.events.hide;

import com.dncomponents.client.components.core.events.BaseEvent;
import com.dncomponents.client.views.IsElement;

public class HideEvent<T> extends BaseEvent {

    private T item;

    public HideEvent() {
        super(HideHandler.TYPE);
    }

    public HideEvent(T item) {
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
        HideEvent<T> event = new HideEvent<>(item);
        source.asElement().dispatchEvent(event.asEvent());
    }

}
