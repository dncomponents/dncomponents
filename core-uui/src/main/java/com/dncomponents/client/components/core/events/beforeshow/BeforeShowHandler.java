package com.dncomponents.client.components.core.events.beforeshow;

import com.dncomponents.client.dom.handlers.BaseEventListener;
import elemental2.dom.CustomEvent;
import elemental2.dom.Event;
import jsinterop.base.Js;

public interface BeforeShowHandler<T> extends BaseEventListener {
    void onBeforeShow(BeforeShowEvent<T> event);

    String TYPE = "beforeShow";

    @Override
    default void handleEvent(Event evt) {
        onBeforeShow(Js.cast(((CustomEvent) evt).detail));
    }

    @Override
    default String getType() {
        return TYPE;
    }

}
