package com.dncomponents.client.components.core.events.show;

import com.dncomponents.client.components.core.events.BaseEvent;
import com.dncomponents.client.views.IsElement;

public class ShowEvent<T> extends BaseEvent {

    private T item;

    public ShowEvent() {
        super(ShowHandler.TYPE);
    }

    public ShowEvent(T item) {
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
        ShowEvent<T> event = new ShowEvent<>(item);
        source.asElement().dispatchEvent(event.asEvent());
    }

}
