package com.dncomponents.client.dom.handlers;

import elemental2.dom.Event;
import elemental2.dom.MouseEvent;
import jsinterop.base.Js;

/**
 * @author nikolasavic
 */
public interface MouseMoveHandler extends BaseEventListener {

    String TYPE = "mousemove";

    void onMouseMove(MouseEvent mouseEvent);

    @Override
    default void handleEvent(Event evt) {
        onMouseMove(Js.cast(evt));
    }

    @Override
    default String getType() {
        return TYPE;
    }
}