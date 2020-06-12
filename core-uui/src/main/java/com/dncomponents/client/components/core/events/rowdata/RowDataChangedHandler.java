package com.dncomponents.client.components.core.events.rowdata;

import com.dncomponents.client.dom.handlers.BaseEventListener;
import elemental2.dom.CustomEvent;
import elemental2.dom.Event;
import jsinterop.base.Js;

public interface RowDataChangedHandler extends BaseEventListener {
    void onRowDataChange(RowDataChangedEvent event);

    String TYPE = "rowdatachanged";

    @Override
    default void handleEvent(Event evt) {
        onRowDataChange(Js.cast(((CustomEvent) evt).detail));
    }

    @Override
    default String getType() {
        return TYPE;
    }

}