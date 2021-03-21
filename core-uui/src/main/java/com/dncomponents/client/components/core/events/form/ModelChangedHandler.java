package com.dncomponents.client.components.core.events.form;

import com.dncomponents.client.dom.handlers.BaseEventListener;
import elemental2.dom.CustomEvent;
import elemental2.dom.Event;
import jsinterop.base.Js;


public interface ModelChangedHandler<T> extends BaseEventListener {

    void onModelChanged(ModelChangedEvent<T> event);

    String TYPE = "modelChanged";

    @Override
     default void handleEvent(Event evt) {
        onModelChanged(Js.cast(((CustomEvent) evt).detail));
    }

    @Override
    default String getType() {
        return TYPE;
    }

}
