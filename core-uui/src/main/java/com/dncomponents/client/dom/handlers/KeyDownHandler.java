package com.dncomponents.client.dom.handlers;

import elemental2.dom.Event;
import elemental2.dom.KeyboardEvent;
import jsinterop.base.Js;

/**
 * @author nikolasavic
 */
public interface KeyDownHandler extends BaseEventListener {

    String TYPE = "keydown";

    void onKeyDown(KeyboardEvent event);

    @Override
    default void handleEvent(Event evt) {
        onKeyDown(Js.cast(evt));
    }

    @Override
    default String getType() {
        return TYPE;
    }
}
