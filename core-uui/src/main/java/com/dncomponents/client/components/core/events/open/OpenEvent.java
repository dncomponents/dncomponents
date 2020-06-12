package com.dncomponents.client.components.core.events.open;

import com.dncomponents.client.components.core.events.BaseEvent;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.Element;

public class OpenEvent<T> extends BaseEvent {

    private T owner;

    public OpenEvent() {
        super(OpenHandler.TYPE);
    }

    public OpenEvent(T owner) {
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
        fire(source.asElement(), item);
    }

    public static <T> void fire(Element source, T item) {
        OpenEvent<T> event = new OpenEvent<>(item);
        source.dispatchEvent(event.asEvent());
    }

}
