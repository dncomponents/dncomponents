package com.dncomponents.client.components.core.events.row;

import com.dncomponents.client.components.RowTable;
import com.dncomponents.client.components.core.events.BaseEvent;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.Element;

public class RowEditingStartedEvent<T> extends BaseEvent {

    private RowTable<T> row;

    public RowEditingStartedEvent() {
        super(RowEditingStartedHandler.TYPE);
    }

    public RowEditingStartedEvent(RowTable<T> row) {
        this();
        this.row = row;
    }

    public RowTable<T> getRow() {
        return row;
    }

    public void setRow(RowTable<T> row) {
        this.row = row;
    }

    public static <T> void fire(IsElement source, RowTable<T> row) {
        fire(source.asElement(), row);
    }

    public static <T> void fire(Element source, RowTable<T> row) {
        RowEditingStartedEvent<T> event = new RowEditingStartedEvent<>(row);
        source.dispatchEvent(event.asEvent());
    }

}
