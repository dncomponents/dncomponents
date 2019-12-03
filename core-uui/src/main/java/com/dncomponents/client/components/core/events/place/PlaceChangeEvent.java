package com.dncomponents.client.components.core.events.place;

import com.dncomponents.client.components.core.events.BaseEvent;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.appview.Place;

public class PlaceChangeEvent<T extends Place> extends BaseEvent {

    private T item;

    public PlaceChangeEvent() {
        super(PlaceChangeHandler.TYPE);
    }

    public PlaceChangeEvent(T item) {
        this();
        this.item = item;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    public static <T extends Place> void fire(IsElement source, T item) {
        PlaceChangeEvent<T> event = new PlaceChangeEvent<>(item);
        source.asElement().dispatchEvent(event.asEvent());
    }

}
