package com.dncomponents.client.components.core.events.validator;

import com.dncomponents.client.dom.handlers.BaseEventListener;
import elemental2.dom.CustomEvent;
import elemental2.dom.Event;
import jsinterop.base.Js;

public interface ValidationHandler<T> extends BaseEventListener {
    void onValidation(ValidationEvent<T> event);

    String TYPE = "validation";

    @Override
    default void handleEvent(Event evt) {
        onValidation(Js.cast(((CustomEvent) evt).detail));
    }

    @Override
    default String getType() {
        return TYPE;
    }

}
