package com.dncomponents.client.components.core.events.row;

import com.dncomponents.client.dom.handlers.BaseEventListener;
import elemental2.dom.CustomEvent;
import elemental2.dom.Event;
import jsinterop.base.Js;

public interface RowRemovedHandler<T> extends BaseEventListener {

    void onRowRemoved(RowRemovedEvent<T> event);

    String TYPE = "rowRemoved";

    @Override
    default void handleEvent(Event evt) {
        onRowRemoved(Js.cast(((CustomEvent) evt).detail));
    }

    @Override
    default String getType() {
        return TYPE;
    }
}
