package com.dncomponents.client.components.core.events.close;

import com.dncomponents.client.components.core.events.BaseEvent;
import com.dncomponents.client.views.IsElement;

public class CloseEvent<T> extends BaseEvent {

    private T owner;

    public CloseEvent() {
        super(CloseHandler.TYPE);
    }

    public CloseEvent(T owner) {
        this();
        this.owner = owner;
    }

    public T getOwner() {
        return owner;
    }

    public void setOwner(T owner) {
        this.owner = owner;
    }

    public static <T> void fire(IsElement source, T item) {
        CloseEvent<T> event = new CloseEvent<>(item);
        source.asElement().dispatchEvent(event.asEvent());
    }

}
