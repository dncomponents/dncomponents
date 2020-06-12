package com.dncomponents.client.components.core.events.cell;

import com.dncomponents.client.dom.handlers.BaseEventListener;
import elemental2.dom.CustomEvent;
import elemental2.dom.Event;
import jsinterop.base.Js;

public interface CellEditHandler<T> extends BaseEventListener {
    void onEdit(CellEditEvent<T> event);

    String TYPE = "celledit";

    @Override
    default void handleEvent(Event evt) {
        onEdit(Js.cast(((CustomEvent) evt).detail));
    }

    @Override
    default String getType() {
        return TYPE;
    }

}