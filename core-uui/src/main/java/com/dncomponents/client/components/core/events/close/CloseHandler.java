package com.dncomponents.client.components.core.events.close;

import com.dncomponents.client.dom.handlers.BaseEventListener;
import elemental2.dom.CustomEvent;
import elemental2.dom.Event;
import jsinterop.base.Js;

public interface CloseHandler<T> extends BaseEventListener {
    void onClose(CloseEvent<T> event);

    String TYPE = "close";

    @Override
    default void handleEvent(Event evt) {
        onClose(Js.cast(((CustomEvent) evt).detail));
    }

    @Override
    default String getType() {
        return TYPE;
    }

}