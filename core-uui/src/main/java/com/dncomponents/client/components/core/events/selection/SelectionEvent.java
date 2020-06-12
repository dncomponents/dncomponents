package com.dncomponents.client.components.core.events.selection;

import com.dncomponents.client.components.core.events.BaseEvent;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.Element;

public class SelectionEvent<T> extends BaseEvent {

    private T item;

    public SelectionEvent() {
        super(SelectionHandler.TYPE);
    }

    public SelectionEvent(T item) {
        this();
        this.item = item;
    }

    public T getSelectedItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    public static <T> void fire(IsElement source, T item) {
        fire(source.asElement(),item);
    }

    public static <T> void fire(HasSelectionHandlers<T> source, T item) {
        SelectionEvent<T> event = new SelectionEvent<>(item);
        source.fireEvent(event.asEvent());
    }
    public static <T> void fire(Element source, T item) {
        SelectionEvent<T> event = new SelectionEvent<>(item);
        source.dispatchEvent(event.asEvent());
    }

}