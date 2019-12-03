package com.dncomponents.client.dom.handlers;

import elemental2.dom.Event;
import elemental2.dom.FocusEvent;
import jsinterop.base.Js;

/**
 * @author nikolasavic
 */
public interface OnBlurHandler extends BaseEventListener {

    String TYPE = "blur";

    void onBlur(FocusEvent focusEvent);

    @Override
    default void handleEvent(Event evt) {
        onBlur(Js.cast(evt));
    }

    @Override
    default String getType() {
        return TYPE;
    }
}