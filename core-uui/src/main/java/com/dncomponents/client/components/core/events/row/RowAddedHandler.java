package com.dncomponents.client.components.core.events.row;

import com.dncomponents.client.dom.handlers.BaseEventListener;
import elemental2.dom.CustomEvent;
import elemental2.dom.Event;
import jsinterop.base.Js;

public interface RowAddedHandler<T> extends BaseEventListener {

    void onRowAdded(RowAddedEvent<T> event);

    String TYPE = "rowAdded";

    @Override
    default void handleEvent(Event evt) {
        onRowAdded(Js.cast(((CustomEvent) evt).detail));
    }

    @Override
    default String getType() {
        return TYPE;
    }
}
