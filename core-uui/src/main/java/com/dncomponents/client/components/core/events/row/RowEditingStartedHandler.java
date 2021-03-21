package com.dncomponents.client.components.core.events.row;

import com.dncomponents.client.dom.handlers.BaseEventListener;
import elemental2.dom.CustomEvent;
import elemental2.dom.Event;
import jsinterop.base.Js;

public interface RowEditingStartedHandler<T> extends BaseEventListener {

    void onRowEditingStarted(RowEditingStartedEvent<T> event);

    String TYPE = "rowEditingStarted";

    @Override
    default void handleEvent(Event evt) {
        onRowEditingStarted(Js.cast(((CustomEvent) evt).detail));
    }

    @Override
    default String getType() {
        return TYPE;
    }
}
