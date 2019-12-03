package com.dncomponents.client.components.core.events.beforeselection;

import com.dncomponents.client.dom.handlers.BaseEventListener;
import elemental2.dom.CustomEvent;
import elemental2.dom.Event;
import jsinterop.base.Js;

public interface BeforeSelectionHandler<T> extends BaseEventListener {
    void onBeforeSelection(BeforeSelectionEvent<T> event);

    String TYPE = "beforeSelection";

    @Override
    default void handleEvent(Event evt) {
        onBeforeSelection(Js.cast(((CustomEvent) evt).detail));
    }

    @Override
    default String getType() {
        return TYPE;
    }

}
