package com.dncomponents.client.components.core.events.cell;

import com.dncomponents.client.dom.handlers.BaseEventListener;
import elemental2.dom.CustomEvent;
import elemental2.dom.Event;
import jsinterop.base.Js;

public interface CellValueChangedHandler<T> extends BaseEventListener {
    void onCellValueChanged(CellValueChangedEvent<T> event);

    String TYPE = "cellValueChanged";

    @Override
    default void handleEvent(Event evt) {
        onCellValueChanged(Js.cast(((CustomEvent) evt).detail));
    }

    @Override
    default String getType() {
        return TYPE;
    }

}
