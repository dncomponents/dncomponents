package com.dncomponents.client.dom.handlers;

import elemental2.dom.Event;
import jsinterop.base.Js;

/**
 * @author nikolasavic
 */
public interface OnChangeHandler extends BaseEventListener {

    String TYPE = "change";

    void onChange(Event inputEvent);


    @Override
    default void handleEvent(Event evt) {
        onChange(Js.cast(evt));
    }

    @Override
    default String getType() {
        return TYPE;
    }
}