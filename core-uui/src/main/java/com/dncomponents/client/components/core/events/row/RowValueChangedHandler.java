package com.dncomponents.client.components.core.events.row;

import com.dncomponents.client.dom.handlers.BaseEventListener;
import elemental2.dom.CustomEvent;
import elemental2.dom.Event;
import jsinterop.base.Js;

public interface RowValueChangedHandler<T> extends BaseEventListener {

    void onRowValueChanged(RowValueChangedEvent<T> event);

    String TYPE = "rowValueChanged";

    @Override
    default void handleEvent(Event evt) {
        onRowValueChanged(Js.cast(((CustomEvent) evt).detail));
    }

    @Override
    default String getType() {
        return TYPE;
    }

}
