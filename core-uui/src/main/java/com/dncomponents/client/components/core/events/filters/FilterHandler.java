package com.dncomponents.client.components.core.events.filters;

import com.dncomponents.client.dom.handlers.BaseEventListener;
import elemental2.dom.CustomEvent;
import elemental2.dom.Event;
import jsinterop.base.Js;


public interface FilterHandler<T> extends BaseEventListener {

    void onFilter(FilterEvent<T> event);

    String TYPE = "filter";

    @Override
    default void handleEvent(Event evt) {
        onFilter(Js.cast(((CustomEvent) evt).detail));
    }

    @Override
    default String getType() {
        return TYPE;
    }

}