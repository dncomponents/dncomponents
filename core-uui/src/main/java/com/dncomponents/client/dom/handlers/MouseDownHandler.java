package com.dncomponents.client.dom.handlers;

import elemental2.dom.Event;
import elemental2.dom.MouseEvent;
import jsinterop.base.Js;

/**
 * @author nikolasavic
 */
public interface MouseDownHandler extends BaseEventListener {

    String TYPE = "mousedown";

    void onMouseDown(MouseEvent mouseEvent);

    @Override
    default void handleEvent(Event evt) {
        onMouseDown(Js.cast(evt));
    }

    @Override
    default String getType() {
        return TYPE;
    }
}