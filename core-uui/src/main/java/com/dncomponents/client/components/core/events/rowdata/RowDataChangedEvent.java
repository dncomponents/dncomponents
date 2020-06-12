package com.dncomponents.client.components.core.events.rowdata;

import com.dncomponents.client.components.core.events.BaseEvent;
import com.dncomponents.client.components.core.events.open.OpenHandler;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.Element;

public class RowDataChangedEvent extends BaseEvent {

    private int count;

    public RowDataChangedEvent() {
        super(RowDataChangedHandler.TYPE);
    }

    public RowDataChangedEvent(int size) {
        this();
        this.count = size;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public static <T> void fire(IsElement source, int count) {
        fire(source.asElement(), count);
    }

    public static <T> void fire(Element source, int count) {
        RowDataChangedEvent event = new RowDataChangedEvent(count);
        source.dispatchEvent(event.asEvent());
    }

}
