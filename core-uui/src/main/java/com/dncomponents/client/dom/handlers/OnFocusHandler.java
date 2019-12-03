package com.dncomponents.client.dom.handlers;

import elemental2.dom.Event;
import elemental2.dom.FocusEvent;
import jsinterop.base.Js;

/**
 * @author nikolasavic
 */
public interface OnFocusHandler extends BaseEventListener {

    String TYPE = "focus";

    void onFocus(FocusEvent focusEvent);

    @Override
    default void handleEvent(Event evt) {
        onFocus(Js.cast(evt));
    }

    @Override
    default String getType() {
        return TYPE;
    }
}