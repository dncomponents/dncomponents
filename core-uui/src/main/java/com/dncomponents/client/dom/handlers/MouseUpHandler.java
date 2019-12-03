package com.dncomponents.client.dom.handlers;

import elemental2.dom.Event;
import elemental2.dom.MouseEvent;
import jsinterop.base.Js;

/**
 * @author nikolasavic
 */
public interface MouseUpHandler extends BaseEventListener {

    String TYPE = "mouseup";

    void onMouseUp(MouseEvent mouseEvent);

    @Override
    default void handleEvent(Event evt) {
        onMouseUp(Js.cast(evt));
    }

    @Override
    default String getType() {
        return TYPE;
    }
}