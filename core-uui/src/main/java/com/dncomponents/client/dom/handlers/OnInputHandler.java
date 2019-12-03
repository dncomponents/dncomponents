package com.dncomponents.client.dom.handlers;

import elemental2.dom.Event;
import elemental2.dom.InputEvent;
import jsinterop.base.Js;

/**
 * @author nikolasavic
 */
public interface OnInputHandler extends BaseEventListener {

    String TYPE = "input";

    void onInputChange(InputEvent inputEvent);

    @Override
    default void handleEvent(Event evt) {
        onInputChange(Js.cast(evt));
    }

    @Override
    default String getType() {
        return TYPE;
    }
}