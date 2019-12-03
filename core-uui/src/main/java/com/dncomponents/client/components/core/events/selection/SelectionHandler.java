package com.dncomponents.client.components.core.events.selection;

import com.dncomponents.client.dom.handlers.BaseEventListener;
import elemental2.dom.CustomEvent;
import elemental2.dom.Event;
import jsinterop.base.Js;

public interface SelectionHandler<T> extends BaseEventListener {
    void onSelection(SelectionEvent<T> event);

    String TYPE = "selection";

    @Override
    default void handleEvent(Event evt) {
        onSelection(Js.cast(((CustomEvent) evt).detail));
    }

    @Override
    default String getType() {
        return TYPE;
    }

}
