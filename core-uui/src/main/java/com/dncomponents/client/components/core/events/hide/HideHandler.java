package com.dncomponents.client.components.core.events.hide;

import com.dncomponents.client.dom.handlers.BaseEventListener;
import elemental2.dom.CustomEvent;
import elemental2.dom.Event;
import jsinterop.base.Js;

public interface HideHandler<T> extends BaseEventListener {
    void onHide(HideEvent<T> event);

    String TYPE = "hide";

    @Override
    default void handleEvent(Event evt) {
        onHide(Js.cast(((CustomEvent) evt).detail));
    }

    @Override
    default String getType() {
        return TYPE;
    }

}
