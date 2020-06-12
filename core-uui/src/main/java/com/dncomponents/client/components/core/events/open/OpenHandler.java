package com.dncomponents.client.components.core.events.open;

import com.dncomponents.client.dom.handlers.BaseEventListener;
import elemental2.dom.CustomEvent;
import elemental2.dom.Event;
import jsinterop.base.Js;

public interface OpenHandler<T> extends BaseEventListener {
    void onOpen(OpenEvent<T> event);

    String TYPE = "open";

    @Override
    default void handleEvent(Event evt) {
        onOpen(Js.cast(((CustomEvent) evt).detail));
    }

    @Override
    default String getType() {
        return TYPE;
    }

}