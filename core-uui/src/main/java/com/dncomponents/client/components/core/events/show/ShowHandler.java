package com.dncomponents.client.components.core.events.show;

import com.dncomponents.client.dom.handlers.BaseEventListener;
import elemental2.dom.CustomEvent;
import elemental2.dom.Event;
import jsinterop.base.Js;

public interface ShowHandler<T> extends BaseEventListener {
    void onShow(ShowEvent<T> event);

    String TYPE = "show";

    @Override
    default void handleEvent(Event evt) {
        onShow(Js.cast(((CustomEvent) evt).detail));
    }

    @Override
    default String getType() {
        return TYPE;
    }

}
