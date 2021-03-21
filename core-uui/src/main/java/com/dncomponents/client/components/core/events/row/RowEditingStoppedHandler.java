package com.dncomponents.client.components.core.events.row;

import com.dncomponents.client.dom.handlers.BaseEventListener;
import elemental2.dom.CustomEvent;
import elemental2.dom.Event;
import jsinterop.base.Js;

public interface RowEditingStoppedHandler<T> extends BaseEventListener {

    void onRowEditingStopped(RowEditingStoppedEvent<T> event);

    String TYPE = "rowEditingStopped";

    @Override
    default void handleEvent(Event evt) {
        onRowEditingStopped(Js.cast(((CustomEvent) evt).detail));
    }

    @Override
    default String getType() {
        return TYPE;
    }

}
