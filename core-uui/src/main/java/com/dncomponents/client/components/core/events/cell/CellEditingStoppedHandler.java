package com.dncomponents.client.components.core.events.cell;

import com.dncomponents.client.dom.handlers.BaseEventListener;
import elemental2.dom.CustomEvent;
import elemental2.dom.Event;
import jsinterop.base.Js;

public interface CellEditingStoppedHandler<T> extends BaseEventListener {
    void onCellEditingStopped(CellEditingStoppedEvent<T> event);

    String TYPE = "cellEditingStopped";

    @Override
    default void handleEvent(Event evt) {
        onCellEditingStopped(Js.cast(((CustomEvent) evt).detail));
    }

    @Override
    default String getType() {
        return TYPE;
    }

}
