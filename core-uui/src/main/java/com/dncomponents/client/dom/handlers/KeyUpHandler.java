package com.dncomponents.client.dom.handlers;

import elemental2.dom.Event;
import elemental2.dom.KeyboardEvent;
import jsinterop.base.Js;

/**
 * @author nikolasavic
 */
public interface KeyUpHandler extends BaseEventListener {

    String TYPE = "keyup";

    void onKeyUp(KeyboardEvent event);

    @Override
    default void handleEvent(Event evt) {
        onKeyUp(Js.cast(evt));
    }

    @Override
    default String getType() {
        return TYPE;
    }
}
