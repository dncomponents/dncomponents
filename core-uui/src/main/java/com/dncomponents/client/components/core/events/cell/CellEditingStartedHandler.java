package com.dncomponents.client.components.core.events.cell;

import com.dncomponents.client.dom.handlers.BaseEventListener;
import elemental2.dom.CustomEvent;
import elemental2.dom.Event;
import jsinterop.base.Js;

public interface CellEditingStartedHandler<T> extends BaseEventListener {

    void onCellEditingStarted(CellEditingStartedEvent<T> event);

    String TYPE = "cellEditingStarted";

    @Override
    default void handleEvent(Event evt) {
        onCellEditingStarted(Js.cast(((CustomEvent) evt).detail));
    }

    @Override
    default String getType() {
        return TYPE;
    }

}
